package org.mage.test.utils;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileReader;
import net.java.truevfs.access.TFileWriter;
import net.java.truevfs.access.TVFS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author JayDi85
 */
public class ZipFilesReadWriteTest {

    @TempDir
    public Path tempFolder;

    @Test
    public void test_Read() {
        // exists
        TFile fileZip = new TFile(Paths.get("src", "test", "data", "images.zip").toString());
        Assertions.assertTrue(fileZip.exists());
        TFile fileZipDir = new TFile(Paths.get("src", "test", "data", "images.zip", "SET").toString());
        Assertions.assertTrue(fileZipDir.exists());
        TFile fileZipFile = new TFile(Paths.get("src", "test", "data", "images.zip", "SET", "image1.png").toString());
        Assertions.assertTrue(fileZipFile.exists());

        // not exists
        TFile fileNotZip = new TFile(Paths.get("src", "test", "data", "images-FAIL.zip").toString());
        Assertions.assertFalse(fileNotZip.exists());
        TFile fileNotZipDir = new TFile(Paths.get("src", "test", "data", "images.zip", "SET-FAIL").toString());
        Assertions.assertFalse(fileNotZipDir.exists());
        TFile fileNotZipFile = new TFile(Paths.get("src", "test", "data", "images.zip", "SET", "image1-FAIL.png").toString());
        Assertions.assertFalse(fileNotZipFile.exists());

        // reading
        Assertions.assertEquals(3, fileZipDir.list().length);
        Assertions.assertTrue(Arrays.asList(fileZipDir.list()).contains("image1.png"));
        Assertions.assertTrue(Arrays.asList(fileZipDir.list()).contains("image2.png"));
        Assertions.assertTrue(Arrays.asList(fileZipDir.list()).contains("image3.png"));
    }

    @Test
    public void test_write() {
        try {
            String zipPath = tempFolder.toFile().getAbsolutePath();
            TFile fileWriteZip = new TFile(Paths.get(zipPath, "temp-images.zip", "DIR", "test.txt").toString());
            Assertions.assertFalse(fileWriteZip.exists());

            Writer writer = new TFileWriter(fileWriteZip);
            try {
                writer.write("test text");
                writer.close();
                Assertions.assertTrue(fileWriteZip.exists());

                TFileReader reader = new TFileReader(fileWriteZip);
                BufferedReader br = new BufferedReader(reader);
                Assertions.assertEquals(br.readLine(), "test text");
                reader.close();
            } finally {
                TVFS.umount();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }
}
