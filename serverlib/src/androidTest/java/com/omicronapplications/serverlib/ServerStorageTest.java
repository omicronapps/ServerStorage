package com.omicronapplications.serverlib;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ServerStorageTest {
    private ServerStorage storage;
    private FTPServer server1, server2, server3;

    @Before
    public void setup() {
        storage = new ServerStorage(InstrumentationRegistry.getInstrumentation().getTargetContext(), "ftp", 1);
        storage.deleteStorage();

        server1 = new FTPServer("testftp.com", 22, "anonymous", "", "/");
        server2 = new FTPServer("server.com", 22, "username", "password", "/");
        server3 = new FTPServer("testftp.com", 22, null, null, null);
    }

    @Test
    public void testAddDelete() {
        assertEquals("getCount", storage.getCount(), 0);

        assertEquals("addServer", storage.addServer(server1), true);
        assertEquals("getCount", storage.getCount(), 1);
        assertEquals("addServer", storage.addServer(server2), true);
        assertEquals("getCount", storage.getCount(), 2);
        assertEquals("addServer", storage.addServer(server3), true);
        assertEquals("getCount", storage.getCount(), 3);

        assertEquals("deleteServer", storage.deleteServer(server1), 1);
        assertEquals("getCount", storage.getCount(), 2);
        assertEquals("deleteServer", storage.deleteServer(server3), 1);
        assertEquals("getCount", storage.getCount(), 1);
        assertEquals("deleteServer", storage.deleteServer(server2), 1);
        assertEquals("getCount", storage.getCount(), 0);
    }

    @Test
    public void testEdit() {
        assertEquals("addServer", storage.addServer(server3), true);
        assertEquals("addServer", storage.addServer(server2), true);
        assertEquals("addServer", storage.addServer(server1), true);

        List<FTPServer> list = storage.getServers();
        assertNotNull("getServers", list);
        FTPServer server = list.get(0);
        server.setHost("other.com");
        assertEquals("editServer", storage.editServer(server), 1);

        list = storage.getServers();
        assertNotNull("getServers", list);
        server = list.get(0);
        assertEquals("getHost", server.getHost(), "other.com");
    }
}
