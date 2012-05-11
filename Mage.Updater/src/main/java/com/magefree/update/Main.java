package com.magefree.update;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    private final static String URL_PREFIX = "http://download.magefree.com/update/";

    public static void main(String[] args) throws Exception {
        Main m = new Main();
        HashMap<String, String> local = m.readLocalData();
        HashMap<String, String> remote = m.downloadAndParseUpdateData();
        List<String> downloadList = m.findUpdated(local, remote);
        downloadList.addAll(m.findNew(local, remote));
        m.downloadAndUpdate(downloadList);
        m.removeFiles(m.findRemoved(local, remote));
    }

    public HashMap<String, String> readLocalData() throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        for (File f : findJars()) {
            result.put(f.getPath().replaceAll("\\\\", "/"), ChechsumHelper.getSHA1Checksum(f.getPath()));
        }
        return result;
    }

    public List<File> findJars() throws Exception {
        ArrayList<File> result = new ArrayList<File>();
        result.addAll(findJarsInDir("mage-client/lib"));
        result.addAll(findJarsInDir("mage-client/plugins"));
        result.addAll(findJarsInDir("mage-server/lib"));
        result.addAll(findJarsInDir("mage-server/plugins"));
        return result;
    }

    public List<File> findJarsInDir(String dir) {
        ArrayList<File> result = new ArrayList<File>();
        File directory = new File(dir);
        if (directory.exists() && directory.isDirectory()) {
            for (File jar : directory.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".jar");
                }
            })) {
                result.add(jar);
            }
        }
        return result;
    }

    public HashMap<String, String> downloadAndParseUpdateData() throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        URL url = new URL(URL_PREFIX + "update-data.txt");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();
        Scanner scanner = new Scanner(urlConnection.getInputStream());
        while (scanner.hasNextLine()) {
            String[] lines = scanner.nextLine().split("  ");
            if (lines.length == 2) {
                result.put(lines[1], lines[0]);
                System.out.println("jar " + lines[1] + ", checksum " + lines[0]);
            }
        }
        return result;
    }

    public List<String> findUpdated(HashMap<String, String> local, HashMap<String, String> remote) {
        ArrayList<String> result = new ArrayList<String>();
        for (String remoteFile : remote.keySet()) {
            if (local.containsKey(remoteFile)) {
                if (!local.get(remoteFile).equals(remote.get(remoteFile))) {
                    System.out.println("jar need to be updated - " + remoteFile);
                    result.add(remoteFile);
                }
            }
        }
        return result;
    }

    public List<String> findNew(HashMap<String, String> local, HashMap<String, String> remote) {
        ArrayList<String> result = new ArrayList<String>();
        for (String remoteFile : remote.keySet()) {
            if (!local.containsKey(remoteFile)) {
                System.out.println("new jar found - " + remoteFile);
                result.add(remoteFile);
            }
        }
        return result;
    }
    
    public List<String> findRemoved(HashMap<String, String> local, HashMap<String, String> remote) {
        ArrayList<String> result = new ArrayList<String>();
        for (String localFile : local.keySet()) {
            if (!remote.containsKey(localFile)) {
                System.out.println("deleted jar found - " + localFile);
                result.add(localFile);
            }
        }
        return result;
    }

    public void downloadAndUpdate(List<String> downloadList) throws IOException {
        for (String filename : downloadList) {
            URL url = new URL(URL_PREFIX + filename);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                System.out.println("downloading " + filename);
                try {
                    InputStream in = urlConnection.getInputStream();
                    File f = new File(filename);
                    if (!f.exists())
                        f.getParentFile().mkdirs();
                    FileOutputStream out = new FileOutputStream(filename);
                    byte[] buf = new byte[4 * 1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buf)) != -1) {
                        out.write(buf, 0, bytesRead);
                    }
                } catch (IOException e) {
                    System.out.println("i/o exception - " + e.getMessage());
                }

            } else {
                System.out.println(filename + " error status : " + urlConnection.getResponseMessage());
            }
        }
    }
    
    public void removeFiles(List<String> files) {
        for (String filename : files) {
            File f = new File(filename);
            if (f.exists()) {
                f.delete();
            } else {
                System.out.println("ERROR. File was found but currently not found");
            }
        }
    }
}
