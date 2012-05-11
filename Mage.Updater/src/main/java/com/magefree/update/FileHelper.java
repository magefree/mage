package com.magefree.update;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper for file operations.
 *
 * @author noxx
 */
public class FileHelper {

    private FileHelper() {
    }

    /**
     * Gets .jar files from specified folder.
     *
     * @param dir Folder to scan for rile
     * @return
     */
    public static List<File> findJarsInDir(String dir) {
        ArrayList<File> result = new ArrayList<File>();
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
        ArrayList<File> result = new ArrayList<File>();
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
     * Filters out dirs.
     */
    private static final FilenameFilter anyFileFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return dir.isFile();
        }
    };

    /**
     * Filters out jars.
     */
    private static final FilenameFilter jarFileFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".jar");
        }
    };
}
