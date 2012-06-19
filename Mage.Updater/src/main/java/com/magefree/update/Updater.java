package com.magefree.update;

import com.magefree.update.helpers.ChechsumHelper;
import com.magefree.update.helpers.FileHelper;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Mage Updater for updating Mage based on metadata from remote server.
 *
 * @author Loki, noxx
 */
public class Updater {

    /**
     * URL to get metadata and files from.
     */
    private final static String URL_PREFIX = "http://download.magefree.com/update/";

    /**
     * Main. Application Entry Point.
     *
     * @param args No args are used.
     *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        Updater m = new Updater();

        // check files on local machine
        HashMap<String, String> local = m.readLocalData();

        // request information for files on update server
        HashMap<String, String> remote = m.downloadAndParseUpdateData();

        // compare to find updated files
        List<String> downloadList = m.findUpdated(local, remote);
        downloadList.addAll(m.findNew(local, remote));

        // download and replace
        m.downloadAndUpdate(downloadList);

        // remove odd files
        List<String> removeList = m.findRemoved(local, remote);
        m.removeFiles(removeList);

        if (downloadList.isEmpty() && removeList.isEmpty()) {
            System.out.println("Already up-to-date.");
        }
    }

    /**
     * Gets lists of files on local machine.
     * For each such file an map's entry is created with path and checksum.
     *
     * @return
     * @throws Exception
     */
    public HashMap<String, String> readLocalData() throws Exception {
        HashMap<String, String> result = new HashMap<String, String>();
        for (File f : findFiles()) {
            result.put(f.getPath().replaceAll("\\\\", "/"), ChechsumHelper.getSHA1Checksum(f.getPath()));
        }
        return result;
    }

    /**
     * Get required files.
     *
     * @return
     * @throws Exception
     */
    public List<File> findFiles() throws Exception {
        ArrayList<File> result = new ArrayList<File>();
        result.addAll(FileHelper.findAllFilesInDir("mage-client/lib"));
        result.addAll(FileHelper.findAllFilesInDir("mage-client/plugins"));
        result.addAll(FileHelper.findAllFilesInDir("mage-server/lib"));
        result.addAll(FileHelper.findAllFilesInDir("mage-server/plugins"));
        return result;
    }

    /**
     * Downloads metadata from remote server getting checksums for files.
     * This information will be used to find out what files should be downloaded and replaced or removed locally.
     *
     * @return
     * @throws Exception
     */
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
                //System.out.println("jar " + lines[1] + ", checksum " + lines[0]);
            }
        }
        return result;
    }

    /**
     * Finds the list of files that have been updated and should be replaced.
     * The fact of being changed is determined based on checksum received from remote server.
     *
     * @param local List of local files with check sums to be compared with remote.
     * @param remote List of remove files with check sum to be compared with local.
     *
     * @return List of files to be replaced with newer versions.
     */
    public List<String> findUpdated(HashMap<String, String> local, HashMap<String, String> remote) {
        ArrayList<String> result = new ArrayList<String>();
        for (String remoteFile : remote.keySet()) {
            if (local.containsKey(remoteFile)) {
                if (!local.get(remoteFile).equals(remote.get(remoteFile))) {
//                    System.out.println("jar need to be updated - " + remoteFile + " local: " + local.get(remoteFile) + ", remoteL " + remote.get(remoteFile));
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
                //System.out.println("new jar found - " + remoteFile);
                result.add(remoteFile);
            }
        }
        return result;
    }

    /**
     * Finds files that should be removed.
     *
     * @param local List of local files with check sums to be compared with remote.
     * @param remote List of remove files with check sum to be compared with local.
     *
     * @return List of files to be removed.
     */
    public List<String> findRemoved(HashMap<String, String> local, HashMap<String, String> remote) {
        ArrayList<String> result = new ArrayList<String>();
        for (String localFile : local.keySet()) {
            if (!remote.containsKey(localFile)) {
                //System.out.println("deleted jar found - " + localFile);
                result.add(localFile);
            }
        }
        return result;
    }

    /**
     * Downloads files and updated them.
     *
     * @param downloadList
     * @throws IOException
     */
    public void downloadAndUpdate(List<String> downloadList) throws IOException {
        for (String filename : downloadList) {
            URL url = new URL(URL_PREFIX + filename);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                FileHelper.downloadFile(filename, urlConnection);
            } else {
                System.out.println(filename + " error status : " + urlConnection.getResponseMessage());
            }
        }
    }

    /**
     * Removes files from the list.
     *
     * @param files
     */
    public void removeFiles(List<String> files) {
        FileHelper.removeFiles(files);
    }
}
