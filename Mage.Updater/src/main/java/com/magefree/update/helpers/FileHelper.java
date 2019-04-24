package com.magefree.update.helpers;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.io.Closeable;
/**
 * Helper for file operations.
 *
 * @author noxx
 */
public final class FileHelper {

    private FileHelper() {
    }

    /**
     * Filters out dirs.
     */
    private static final FileFilter anyFileFilter = f -> f.isFile();

    /**
     * Filters out jars.
     */
    private static final FilenameFilter jarFileFilter = (dir, name) -> name.endsWith(".jar");

    /**
     * Gets .jar files from specified folder.
     *
     * @param dir Folder to scan for rile
     * @return
     */
    public static List<File> findJarsInDir(String dir) {
        ArrayList<File> result = new ArrayList<>();
        File directory = new File(dir);
        if (directory.exists() && directory.isDirectory()) {
            for (File jar : directory.listFiles(jarFileFilter)) {
                result.add(jar);
            }
        }
        return result;
    }

    /**
     * Gets non-dir files from specified folder.
     *
     * @param dir Folder to scan for rile
     * @return
     */
    public static List<File> findAllFilesInDir(String dir) {
        ArrayList<File> result = new ArrayList<>();
        File directory = new File(dir);
        if (directory.exists() && directory.isDirectory()) {
            for (File jar : directory.listFiles(anyFileFilter)) {
                result.add(jar);
            }
        }
        return result;
    }

    /**
     * Removes all files from the list.
     *
     * @param files
     */
    public static void removeFiles(List<String> files) {
        for (String filename : files) {
            File f = new File(filename);
            if (f.exists()) {
                f.delete();
                System.out.println("File has been deleted: " + filename);
            } else {
                System.out.println("ERROR. Couldn't find file to delete: " + filename);
            }
        }
    }

    /**
     * Downloads specified file.
     *
     * @param filename
     * @param urlConnection
     */
    public static void downloadFile(String filename, HttpURLConnection urlConnection) {
        System.out.println("Downloading " + filename);
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = urlConnection.getInputStream();
            File f = new File(filename);
            if (!f.exists() && f.getParentFile() != null) {
                f.getParentFile().mkdirs();
                System.out.println("Directories have been created: " + f.getParentFile().getPath());
            }

            out = new FileOutputStream(filename);
            byte[] buf = new byte[4 * 1024];
            int bytesRead;

            while ((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
            }

            System.out.println("File has been updated: " + filename);
        } catch (IOException e) {
            System.out.println("i/o exception - " + e.getMessage());
        } finally {
            closeQuietly(in);
            closeQuietly(out);
        }
    }

    public static void closeQuietly(Closeable s) {
        if(s != null) {
            try {
                s.close();
            } catch (Exception e) {
                System.out.println("i/o exception - " + e.getMessage());
            }
        }
    }
}
