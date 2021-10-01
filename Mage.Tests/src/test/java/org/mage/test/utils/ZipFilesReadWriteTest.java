package org.mage.test.utils;

import net.java.truevfs.access.TFile;
import net.java.truevfs.access.TFileReader;
import net.java.truevfs.access.TFileWriter;
import net.java.truevfs.access.TVFS;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @author JayDi85
 */
public class ZipFilesReadWriteTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void test_Read() {
        // exists
        TFile fileZip = new TFile(Paths.get("src", "test", "data", "images.zip").toString());
        Assert.assertTrue(fileZip.exists());
        TFile fileZipDir = new TFile(Paths.get("src", "test", "data", "images.zip", "SET").toString());
        Assert.assertTrue(fileZipDir.exists());
        TFile fileZipFile = new TFile(Paths.get("src", "test", "data", "images.zip", "SET", "image1.png").toString());
        Assert.assertTrue(fileZipFile.exists());

        // not exists
        TFile fileNotZip = new TFile(Paths.get("src", "test", "data", "images-FAIL.zip").toString());
        Assert.assertFalse(fileNotZip.exists());
        TFile fileNotZipDir = new TFile(Paths.get("src", "test", "data", "images.zip", "SET-FAIL").toString());
        Assert.assertFalse(fileNotZipDir.exists());
        TFile fileNotZipFile = new TFile(Paths.get("src", "test", "data", "images.zip", "SET", "image1-FAIL.png").toString());
        Assert.assertFalse(fileNotZipFile.exists());

        // reading
        Assert.assertEquals(3, fileZipDir.list().length);
        Assert.assertTrue(Arrays.asList(fileZipDir.list()).contains("image1.png"));
        Assert.assertTrue(Arrays.asList(fileZipDir.list()).contains("image2.png"));
        Assert.assertTrue(Arrays.asList(fileZipDir.list()).contains("image3.png"));
    }

    @Test
    public void test_write() {
        try {
            String zipPath = tempFolder.newFolder().getAbsolutePath();
            TFile fileWriteZip = new TFile(Paths.get(zipPath, "temp-images.zip", "DIR", "test.txt").toString());
            Assert.assertFalse(fileWriteZip.exists());

            Writer writer = new TFileWriter(fileWriteZip);
            try {
                writer.write("test text");
                writer.close();
                Assert.assertTrue(fileWriteZip.exists());

                TFileReader reader = new TFileReader(fileWriteZip);
                BufferedReader br = new BufferedReader(reader);
                Assert.assertEquals(br.readLine(), "test text");
                reader.close();
            } finally {
                TVFS.umount();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }
}
