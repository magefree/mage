package mage.client.util;

import mage.client.remote.XmageURLConnection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author JayDi85
 */
@Disabled // TODO: too many fails due third party servers downtime, migrate to more stable resources or just run it manually
public class DownloaderTest {

    @Test
    public void test_DownloadText_ByHttp() {
        String s = XmageURLConnection.downloadText("http://example.com");
        Assertions.assertTrue(s.contains("<head>"), "must have text data");
    }

    @Test
    public void test_DownloadText_ByHttps() {
        String s = XmageURLConnection.downloadText("https://example.com");
        Assertions.assertTrue(s.contains("<head>"), "must have text data");
    }

    @Test
    public void test_DownloadText_ByRedirectProtocol() {
        // http to https restricted by design, see https://stackoverflow.com/a/1884427/1276632
        // it's not critical for a client (e.g. for images download), so no needs in custom implementation
        // like xmage launcher does
        String s = XmageURLConnection.downloadText("http://github.com");
        Assertions.assertTrue(s.isEmpty(), "must have fail on https redirect (301 result)");
    }

    @Test
    public void test_DownloadText_ByRedirectUri() {
        String s = XmageURLConnection.downloadText("https://github.com/magefree/mage/issues/new");
        Assertions.assertTrue(s.contains("Sign in to GitHub"), "must have text data (redirect to login page)");
    }

    @Test
    public void test_DownloadText_ScryfallUtf8() {
        String s = XmageURLConnection.downloadText("https://api.scryfall.com/cards/sld/379★/en");
        Assertions.assertTrue(s.contains("Zndrsplt, Eye of Wisdom"), "must have text data (utf8 url must work)");
    }

    @Test
    public void test_DownloadFile_ByHttp() throws IOException {
        // use any public image here
        InputStream stream = XmageURLConnection.downloadBinary("http://xmage.today/images/xmage-logo.png");
        Assertions.assertNotNull(stream);
        BufferedImage image = ImageIO.read(stream);
        Assertions.assertNotNull(stream);
        Assertions.assertTrue(image.getWidth() > 0, "must have image data");
    }

    @Test
    public void test_DownloadFile_ByHttps() throws IOException {
        // use any public image here
        InputStream stream = XmageURLConnection.downloadBinary("https://xmage.today/images/xmage-logo.png");
        Assertions.assertNotNull(stream);
        BufferedImage image = ImageIO.read(stream);
        Assertions.assertNotNull(stream);
        Assertions.assertTrue(image.getWidth() > 0, "must have image data");
    }

    @Test
    @Disabled // TODO: enable after gatherer server maintenance done
    public void test_DownloadFromWindowsServers() throws IOException {
        // symbols download from gatherer website
        // error example: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
        InputStream stream = XmageURLConnection.downloadBinary("https://gatherer.wizards.com/Handlers/Image.ashx?type=symbol&set=BIG&size=small&rarity=C");
        Assertions.assertNotNull(stream);
        BufferedImage image = null;
        try {
            image = ImageIO.read(stream);
        } catch (IOException e) {
            Assertions.fail("Can't download image file due error: " + e);
        }
        Assertions.assertNotNull(stream);
        Assertions.assertNotNull(image);
        Assertions.assertTrue(image.getWidth() > 0, "must have image data");
    }
}
