package mage.client.util;

import mage.client.remote.XmageURLConnection;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author JayDi85
 */
@Ignore // TODO: too many fails due third party servers downtime, migrate to more stable resources or just run it manually
public class DownloaderTest {

    @Test
    public void test_DownloadText_ByHttp() {
        String s = XmageURLConnection.downloadText("http://example.com");
        Assert.assertTrue("must have text data", s.contains("<head>"));
    }

    @Test
    public void test_DownloadText_ByHttps() {
        String s = XmageURLConnection.downloadText("https://example.com");
        Assert.assertTrue("must have text data", s.contains("<head>"));
    }

    @Test
    public void test_DownloadText_ByRedirectProtocol() {
        // http to https restricted by design, see https://stackoverflow.com/a/1884427/1276632
        // it's not critical for a client (e.g. for images download), so no needs in custom implementation
        // like xmage launcher does
        String s = XmageURLConnection.downloadText("http://github.com");
        Assert.assertTrue("must have fail on https redirect (301 result)", s.isEmpty());
    }

    @Test
    public void test_DownloadText_ByRedirectUri() {
        String s = XmageURLConnection.downloadText("https://github.com/magefree/mage/issues/new");
        Assert.assertTrue("must have text data (redirect to login page)", s.contains("Sign in to GitHub"));
    }

    @Test
    public void test_DownloadText_ScryfallUtf8() {
        String s = XmageURLConnection.downloadText("https://api.scryfall.com/cards/sld/379★/en");
        Assert.assertTrue("must have text data (utf8 url must work)", s.contains("Zndrsplt, Eye of Wisdom"));
    }

    @Test
    public void test_DownloadFile_ByHttp() throws IOException {
        // use any public image here
        InputStream stream = XmageURLConnection.downloadBinary("http://xmage.today/images/xmage-logo.png");
        Assert.assertNotNull(stream);
        BufferedImage image = ImageIO.read(stream);
        Assert.assertNotNull(stream);
        Assert.assertTrue("must have image data", image.getWidth() > 0);
    }

    @Test
    public void test_DownloadFile_ByHttps() throws IOException {
        // use any public image here
        InputStream stream = XmageURLConnection.downloadBinary("https://xmage.today/images/xmage-logo.png");
        Assert.assertNotNull(stream);
        BufferedImage image = ImageIO.read(stream);
        Assert.assertNotNull(stream);
        Assert.assertTrue("must have image data", image.getWidth() > 0);
    }

    @Test
    @Ignore // TODO: enable after gatherer server maintenance done
    public void test_DownloadFromWindowsServers() throws IOException {
        // symbols download from gatherer website
        // error example: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
        InputStream stream = XmageURLConnection.downloadBinary("https://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=BIG&size=small&rarity=C");
        Assert.assertNotNull(stream);
        BufferedImage image = null;
        try {
            image = ImageIO.read(stream);
        } catch (IOException e) {
            Assert.fail("Can't download image file due error: " + e);
        }
        Assert.assertNotNull(stream);
        Assert.assertNotNull(image);
        Assert.assertTrue("must have image data", image.getWidth() > 0);
    }
}
