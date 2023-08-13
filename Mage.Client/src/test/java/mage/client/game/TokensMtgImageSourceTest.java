package mage.client.game;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.CardImageUrls;
import org.mage.plugins.card.dl.sources.TokensMtgImageSource;
import org.mage.plugins.card.images.CardDownloadData;

/**
 * @author Quercitron
 */
@Ignore
public class TokensMtgImageSourceTest {

    @Test
    public void generateTokenUrlTest() throws Exception {
        CardImageSource imageSource = TokensMtgImageSource.instance;

        CardImageUrls url = imageSource.generateTokenUrl(new CardDownloadData("Thopter", "ORI", "0", false, 1));
        Assert.assertEquals("https://tokens.mtg.onl/tokens/ORI_010-Thopter.jpg", url.baseUrl);

        url = imageSource.generateTokenUrl(new CardDownloadData("Thopter", "ORI", "0", false, 2));
        Assert.assertEquals("https://tokens.mtg.onl/tokens/ORI_011-Thopter.jpg", url.baseUrl);

        url = imageSource.generateTokenUrl(new CardDownloadData("Ashaya, the Awoken World", "ORI", "0", false, 0));
        Assert.assertEquals("https://tokens.mtg.onl/tokens/ORI_007-Ashaya,-the-Awoken-World.jpg", url.baseUrl);

        url = imageSource.generateTokenUrl(new CardDownloadData("Emblem Gideon, Ally of Zendikar", "BFZ", "0", false, 0));
        Assert.assertEquals("https://tokens.mtg.onl/tokens/BFZ_012-Gideon-Emblem.jpg", url.baseUrl);
    }
}
