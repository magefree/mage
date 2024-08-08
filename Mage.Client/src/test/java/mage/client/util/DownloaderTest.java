package mage.client.util;

import mage.client.remote.XmageURLConnection;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author JayDi85
 */
public class DownloaderTest {

    @Test
    public void test_DownloadText_ByHttp() {
        String s = XmageURLConnection.downloadText("http://google.com");
        Assert.assertTrue("must have text data", s.contains("<head>"));
    }

    @Test
    public void test_DownloadText_ByHttps() {
        String s = XmageURLConnection.downloadText("https://google.com");
        Assert.assertTrue("must have text data", s.contains("<head>"));
    }

    @Test
    public void test_DownloadFile_ByHttp() throws IOException {
        // use any public image here
        InputStream stream = XmageURLConnection.downloadBinary("http://www.google.com/tia/tia.png");
        Assert.assertNotNull(stream);
        BufferedImage image = ImageIO.read(stream);
        Assert.assertNotNull(stream);
        Assert.assertTrue("must have image data", image.getWidth() > 0);
    }

    @Test
    public void test_DownloadFile_ByHttps() throws IOException {
        // use any public image here
        InputStream stream = XmageURLConnection.downloadBinary("https://www.google.com/tia/tia.png");
        Assert.assertNotNull(stream);
        BufferedImage image = ImageIO.read(stream);
        Assert.assertNotNull(stream);
        Assert.assertTrue("must have image data", image.getWidth() > 0);
    }
}
