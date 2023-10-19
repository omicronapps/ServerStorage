package com.omicronapplications.serverlib;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class ServerStorage extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "servers";
    private static final String KEY_HOST = "host";
    private static final String KEY_PORT = "port";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PATH = "path";
    private int mCurrentIndex;

    public ServerStorage(Context context, String name, int version) {
        super(context, name, null, version);
        mCurrentIndex = -1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                getDatabaseName() + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_HOST + " TEXT, " +
                KEY_PORT + " INTEGER, " +
                KEY_USERNAME + " TEXT, " +
                KEY_PASSWORD + " TEXT, " +
                KEY_PATH + " TEXT" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addServer(FTPServer server) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HOST, server.getHost());
        values.put(KEY_PORT, server.getPort());
        values.put(KEY_USERNAME, server.getUsername());
        values.put(KEY_PASSWORD, server.getPassword());
        values.put(KEY_PATH, server.getPath());

        long row = db.insert(TABLE_NAME, null, values);
        server.setRow(row);
        db.close();

        return (row != -1);
    }

    public int deleteServer(FTPServer server) {
        SQLiteDatabase db = getWritableDatabase();

        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(server.getRow());
        int rowCount = 0;
        try {
            rowCount = db.delete(TABLE_NAME, getDatabaseName() + " =? ", whereArgs);
        } catch (android.database.SQLException e) {
            rowCount = -1;
        }

        db.close();

        return rowCount;
    }

    public int deleteStorage() {
        SQLiteDatabase db = getWritableDatabase();

        int rowCount = 0;
        try {
            rowCount = db.delete(TABLE_NAME, null, null);
        } catch (android.database.SQLException e) {
            rowCount = -1;
        }
        db.close();

        return rowCount;
    }

    public int editServer(FTPServer server) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HOST, server.getHost());
        values.put(KEY_PORT, server.getPort());
        values.put(KEY_USERNAME, server.getUsername());
        values.put(KEY_PASSWORD, server.getPassword());
        values.put(KEY_PATH, server.getPath());

        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(server.getRow());
        int rowCount = db.update(TABLE_NAME, values, getDatabaseName() + " =? ", whereArgs);
        db.close();

        return rowCount;
    }

    public List<FTPServer> getServers() {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<FTPServer> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                FTPServer server = new FTPServer();
                server.setRow(Long.parseLong(cursor.getString(0)));
                server.setHost(cursor.getString(1));
                server.setPort(Integer.parseInt(cursor.getString(2)));
                server.setUsername(cursor.getString(3));
                server.setPassword(cursor.getString(4));
                server.setPath(cursor.getString(5));
                list.add(server);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    public FTPServer getServer(int index) {
        FTPServer server = null;
        List<FTPServer> servers = getServers();
        if (index < servers.size()) {
            server = servers.get(index);
        }
        return server;
    }

    public void setCurrentServer(int index) {
        mCurrentIndex = index;
    }

    public FTPServer getCurrentServer() {
        FTPServer server = null;
        if (mCurrentIndex >= 0) {
            server = getServer(mCurrentIndex);
        }
        return server;
    }

    public long getCount() {
        SQLiteDatabase db = getReadableDatabase();

        long count = DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        db.close();

        return count;
    }
}
