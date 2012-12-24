package org.mage.card.arcane;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Util {
    public static final boolean isMac = System.getProperty("os.name").toLowerCase().indexOf("mac") != -1;
    public static final boolean isWindows = System.getProperty("os.name").toLowerCase().indexOf("windows") == -1;

    public static Robot robot;
    static {
        try {
            new Robot();
        } catch (AWTException ex) {
            throw new RuntimeException("Error creating robot.", ex);
        }
    }

    public static final ThreadPoolExecutor threadPool;
    static private int threadCount;
    static {
        threadPool = new ThreadPoolExecutor(4, 4, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new ThreadFactory() {
            @Override
            public Thread newThread (Runnable runnable) {
                threadCount++;
                Thread thread = new Thread(runnable, "Util" + threadCount);
                thread.setDaemon(true);
                return thread;
            }
        });
        threadPool.prestartAllCoreThreads();
    }

    public static void broadcast (byte[] data, int port) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        broadcast(socket, data, port, NetworkInterface.getNetworkInterfaces());
        socket.close();
    }

    private static void broadcast (DatagramSocket socket, byte[] data, int port, Enumeration<NetworkInterface> ifaces)
        throws IOException {
        for (NetworkInterface iface : Collections.list(ifaces)) {
            for (InetAddress address : Collections.list(iface.getInetAddresses())) {
                if (!address.isSiteLocalAddress()) {
                    continue;
                }
                // Java 1.5 doesn't support getting the subnet mask, so try the two most common.
                byte[] ip = address.getAddress();
                ip[3] = -1; // 255.255.255.0
                socket.send(new DatagramPacket(data, data.length, InetAddress.getByAddress(ip), port));
                ip[2] = -1; // 255.255.0.0
                socket.send(new DatagramPacket(data, data.length, InetAddress.getByAddress(ip), port));
            }
        }
    }

    public static void sleep (int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    public static boolean classExists (String className) {
        try {
            Class.forName(className);
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

    public static void wait (Object lock) {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException ex) {
            }
        }
    }

    public static void invokeAndWait (Runnable runnable) {
        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (Exception ex) {
            throw new RuntimeException("Error invoking runnable in UI thread.", ex);
        }
    }
}
