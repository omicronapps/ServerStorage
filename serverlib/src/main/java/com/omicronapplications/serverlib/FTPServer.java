package com.omicronapplications.serverlib;

public class FTPServer {
    private long mRow;
    private String mHost;
    private int mPort;
    private String mUsername;
    private String mPassword;
    private String mPath;

    public FTPServer() {
        mRow = 0;
        mHost = "";
        mPort = 22;
        mUsername = "anonymous";
        mPassword = "";
        mPath = "/";
    }

    public FTPServer(String host, int port, String username, String password, String path) {
        mHost = host;
        mPort = port;
        mUsername = username;
        mPassword = password;
        mPath = path;
    }

    public long getRow() {
        return mRow;
    }

    public void setRow(long row) {
        this.mRow = row;
    }

    public String getHost() {
        return mHost;
    }

    public void setHost(String host) {
        mHost = host;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int port) {
        mPort = port;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String path) {
        mPath = path;
    }
}
