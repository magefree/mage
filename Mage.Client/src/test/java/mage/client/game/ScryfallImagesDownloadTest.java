package mage.client.game;

import mage.sets.TheLordOfTheRingsTalesOfMiddleEarth;
import org.junit.Assert;
import org.junit.Test;
import org.mage.plugins.card.dl.sources.CardImageSource;
import org.mage.plugins.card.dl.sources.CardImageUrls;
import org.mage.plugins.card.dl.sources.ScryfallImageSource;
import org.mage.plugins.card.dl.sources.ScryfallImageSourceSmall;
import org.mage.plugins.card.images.CardDownloadData;

/**
 * @author JayDi85
 */
public class ScryfallImagesDownloadTest {

    @Test
    public void test_Cards_DownloadLinks() throws Exception {
        CardImageSource imageSource = ScryfallImageSource.getInstance();

        // normal card
        CardImageUrls urls = imageSource.generateCardUrl(new CardDownloadData("Grizzly Bears", "10E", "268", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/10e/268/en?format=image", urls.getBaseUrl());

        // various card
        urls = imageSource.generateCardUrl(new CardDownloadData("Grizzly Bears", "30A", "195", true, 1));
        Assert.assertEquals("https://api.scryfall.com/cards/30a/195/en?format=image", urls.getBaseUrl());
        urls = imageSource.generateCardUrl(new CardDownloadData("Grizzly Bears", "30A", "492", true, 2));
        Assert.assertEquals("https://api.scryfall.com/cards/30a/492/en?format=image", urls.getBaseUrl());

        // api link
        urls = imageSource.generateCardUrl(new CardDownloadData("Ajani, the Greathearted", "WAR", "184*", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/war/184★/en?format=image", urls.getBaseUrl());

        // direct api link
        urls = imageSource.generateCardUrl(new CardDownloadData("Command Tower", "REX", "26b", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/rex/26/en?format=image&face=back", urls.getBaseUrl());

        // the one ring
        Assert.assertTrue("LTR must use The One Ring with 001 number, not 0", TheLordOfTheRingsTalesOfMiddleEarth.getInstance().getSetCardInfo()
                .stream()
                .filter(c -> c.getName().equals("The One Ring"))
                .anyMatch(c -> c.getCardNumber().equals("001"))
        );
        urls = imageSource.generateCardUrl(new CardDownloadData("The One Ring", "LTR", "001", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/ltr/0/en?format=image", urls.getBaseUrl());


        // added same tests for small images
        CardImageSource imageSourceSmall = ScryfallImageSourceSmall.getInstance();

        // normal card
        urls = imageSourceSmall.generateCardUrl(new CardDownloadData("Grizzly Bears", "10E", "268", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/10e/268/en?format=image&version=small", urls.getBaseUrl());

        // various card
        urls = imageSourceSmall.generateCardUrl(new CardDownloadData("Grizzly Bears", "30A", "195", true, 1));
        Assert.assertEquals("https://api.scryfall.com/cards/30a/195/en?format=image&version=small", urls.getBaseUrl());
        urls = imageSourceSmall.generateCardUrl(new CardDownloadData("Grizzly Bears", "30A", "492", true, 2));
        Assert.assertEquals("https://api.scryfall.com/cards/30a/492/en?format=image&version=small", urls.getBaseUrl());

        // api link
        urls = imageSourceSmall.generateCardUrl(new CardDownloadData("Ajani, the Greathearted", "WAR", "184*", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/war/184★/en?format=image&version=small", urls.getBaseUrl());

        // direct api link
        urls = imageSourceSmall.generateCardUrl(new CardDownloadData("Command Tower", "REX", "26b", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/rex/26/en?format=image&version=small&face=back", urls.getBaseUrl());

        // the one ring
        Assert.assertTrue("LTR must use The One Ring with 001 number, not 0", TheLordOfTheRingsTalesOfMiddleEarth.getInstance().getSetCardInfo()
                .stream()
                .filter(c -> c.getName().equals("The One Ring"))
                .anyMatch(c -> c.getCardNumber().equals("001"))
        );
        urls = imageSourceSmall.generateCardUrl(new CardDownloadData("The One Ring", "LTR", "001", false, 0));
        Assert.assertEquals("https://api.scryfall.com/cards/ltr/0/en?format=image&version=small", urls.getBaseUrl());
    }
}
