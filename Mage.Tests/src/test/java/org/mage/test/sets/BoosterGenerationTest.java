package org.mage.test.sets;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardScanner;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.sets.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author nigelzor, JayDi85
 */
public class BoosterGenerationTest extends MageTestBase {

    @Before
    public void setUp() {
        CardScanner.scan();
    }

    private static final List<String> basics = new ArrayList<>(Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest"));

    private void checkOnePartnerBoost() {
        List<Card> booster = Battlebond.getInstance().createBooster();
        boolean foundPartner = false;
        String Partner = "";

        for (Card card : booster) {
            for (Ability ability : card.getAbilities()) {
                if (ability instanceof PartnerWithAbility) {
                    if (foundPartner) {
                        Assert.assertEquals(Partner, card.getName());
                    } else {
                        foundPartner = true;
                        Partner = ((PartnerWithAbility) ability).getPartnerName();
                    }
                }
            }
        }
    }

    @Test
    public void testBattlebondPartnerBoosters() {
        for (int i = 0; i < 10; i++) {
            checkOnePartnerBoost();
        }
    }

    @Test
    public void testFateReforged() {

        List<String> tapland = new ArrayList<>(Arrays.asList(
                "Bloodfell Caves", "Blossoming Sands", "Dismal Backwater", "Jungle Hollow", "Rugged Highlands",
                "Scoured Barrens", "Swiftwater Cliffs", "Thornwood Falls", "Tranquil Cove", "Wind-Scarred Crag"));
        List<String> fetchland = new ArrayList<>(Arrays.asList(
                "Bloodstained Mire", "Flooded Strand", "Polluted Delta", "Windswept Heath", "Wooded Foothills"));

        List<Card> booster = FateReforged.getInstance().createBooster();
        assertTrue(str(booster), contains(booster, tapland, "FRF") || contains(booster, fetchland, "KTK")
                || contains(booster, basics, null));
        // assertFalse(str(booster), contains(booster, basics, null));
    }

    @Test
    public void testMysteryBooster1() {
        // https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
        List<Card> booster = MysteryBooster.getInstance().createBooster();
        Assert.assertNotNull(booster);
        Assert.assertEquals("Pack contains 15 cards", 15, booster.size());

        Assert.assertTrue("Slot 1 is white", booster.get(0).getColor(null).isWhite());
        Assert.assertTrue("Slot 2 is white", booster.get(1).getColor(null).isWhite());

        Assert.assertTrue("Slot 3 is blue", booster.get(2).getColor(null).isBlue());
        Assert.assertTrue("Slot 4 is blue", booster.get(3).getColor(null).isBlue());

        Assert.assertTrue("Slot 5 is black", booster.get(4).getColor(null).isBlack());
        Assert.assertTrue("Slot 6 is black", booster.get(5).getColor(null).isBlack());

        Assert.assertTrue("Slot 7 is red", booster.get(6).getColor(null).isRed());
        Assert.assertTrue("Slot 8 is red", booster.get(7).getColor(null).isRed());

        Assert.assertTrue("Slot 9 is green", booster.get(8).getColor(null).isGreen());
        Assert.assertTrue("Slot 10 is green", booster.get(9).getColor(null).isGreen());

        Assert.assertTrue("Slot 11 is multicolored", booster.get(10).getColor(null).isMulticolored());
        Assert.assertTrue("Slot 12 is colorless", booster.get(11).getColor(null).isColorless());

        Assert.assertEquals("Slot 15 is from FMB1 set","FMB1", booster.get(14).getExpansionSetCode());
    }

    @Test
    public void testMysteryBooster1Create15CardBooster() {
        // https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
        List<Card> booster = MysteryBooster.getInstance().create15CardBooster();
        Assert.assertNotNull(booster);
        Assert.assertEquals("Pack contains 15 cards", 15, booster.size());

        Assert.assertTrue("Slot 1 is white", booster.get(0).getColor(null).isWhite());
        Assert.assertTrue("Slot 2 is white", booster.get(1).getColor(null).isWhite());

        Assert.assertTrue("Slot 3 is blue", booster.get(2).getColor(null).isBlue());
        Assert.assertTrue("Slot 4 is blue", booster.get(3).getColor(null).isBlue());

        Assert.assertTrue("Slot 5 is black", booster.get(4).getColor(null).isBlack());
        Assert.assertTrue("Slot 6 is black", booster.get(5).getColor(null).isBlack());

        Assert.assertTrue("Slot 7 is red", booster.get(6).getColor(null).isRed());
        Assert.assertTrue("Slot 8 is red", booster.get(7).getColor(null).isRed());

        Assert.assertTrue("Slot 9 is green", booster.get(8).getColor(null).isGreen());
        Assert.assertTrue("Slot 10 is green", booster.get(9).getColor(null).isGreen());

        Assert.assertTrue("Slot 11 is multicolored", booster.get(10).getColor(null).isMulticolored());
        Assert.assertTrue("Slot 12 is colorless", booster.get(11).getColor(null).isColorless());

        Assert.assertEquals("Slot 15 is from FMB1 set","FMB1", booster.get(14).getExpansionSetCode());
    }

    @Test
    public void testMastersEditionII() {
        List<String> snowCoveredLand = new ArrayList<>(Arrays.asList(
                "Snow-Covered Plains",
                "Snow-Covered Island",
                "Snow-Covered Swamp",
                "Snow-Covered Mountain",
                "Snow-Covered Forest"
        ));
        List<Card> booster = MastersEditionII.getInstance().createBooster();
        assertTrue(str(booster), contains(booster, snowCoveredLand, "ME2"));
        assertFalse(str(booster), contains(booster, basics, null));
    }

    @Test
    public void testMastersEditionIV_UrzaSpecialLandsList() {

        List<String> needUrzaList = new ArrayList<>(Arrays.asList(
                "Urza's Mine",
                "Urza's Power Plant",
                "Urza's Tower"
        ));

        List<CardInfo> setOrzaList = MastersEditionIV.getInstance().getSpecialLand();
        Assert.assertEquals("Urza special lands must have 4 variation for each of 3 card", 3 * 4, setOrzaList.size());

        List<String> foundedUrzaList = new ArrayList<>();
        for (CardInfo cardInfo : setOrzaList) {
            Assert.assertTrue("card " + cardInfo.getName() + " must be in urza's list", needUrzaList.contains(cardInfo.getName()));
            foundedUrzaList.add(cardInfo.getName());
        }

        for (String needName : needUrzaList) {
            Assert.assertTrue("can't find need card " + needName + " in special land list", foundedUrzaList.contains(needName));
        }
    }

    @Test
    public void testMastersEditionIV_UrzaSpecialLandInBoosters() {
        // ME4 replace all basic lands with special (1 per booster)
        // https://mtg.gamepedia.com/Masters_Edition_IV
        List<String> urzaLand = new ArrayList<>(Arrays.asList(
                "Urza's Mine",
                "Urza's Power Plant",
                "Urza's Tower"
        ));

        for (int i = 1; i <= 5; i++) {
            List<Card> booster = MastersEditionIV.getInstance().createBooster();
            assertTrue(str(booster), contains(booster, urzaLand, "ME4"));
            assertFalse(str(booster), contains(booster, basics, null));
        }
    }

    @Test
    public void testCoreSet2019_DualLandsAreGenerated() {
        List<Card> allCards = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<Card> booster = CoreSet2019.getInstance().createBooster();
            // check that booster contains a land card
            assertTrue(booster.stream().anyMatch(card -> card.getCardType().contains(CardType.LAND)));
            allCards.addAll(booster);
        }
        // check that some dual lands were generated
        assertTrue(allCards.stream().anyMatch(card -> card.getCardType().contains(CardType.LAND) && Objects.equals(card.getRarity(), Rarity.COMMON)));
    }

    @Test
    public void testWarOfTheSpark_EveryBoosterContainsPlaneswalker() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = WarOfTheSpark.getInstance().createBooster();
            // check that booster contains a planeswalker
            assertTrue(booster.stream().anyMatch(MageObject::isPlaneswalker));
        }
    }

    @Test
    public void testDominaria_EveryBoosterContainsLegendaryCreature() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = Dominaria.getInstance().createBooster();
            // check that booster contains legendary creature
            assertTrue(booster.stream().anyMatch(card -> card.isCreature() && card.isLegendary()));
        }
    }

    @Test
    public void testColdSnap_BoosterMustHaveOneSnowLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = Coldsnap.getInstance().createBooster();
            assertTrue("coldsnap's booster must contain 1 snow covered land", booster.stream().anyMatch(card -> card.isBasic() && card.getName().startsWith("Snow-Covered ")));
        }
    }

    @Test
    public void testModernHorizons_BoosterMustHaveOneSnowLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = ModernHorizons.getInstance().createBooster();
            assertTrue("Modern Horizon's booster must contain 1 snow covered land", booster.stream().anyMatch(card -> card.isBasic() && card.getName().startsWith("Snow-Covered ")));
        }
    }

    @Test
    public void testMastersEditionII_BoosterMustHaveOneSnowLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = MastersEditionII.getInstance().createBooster();
            assertTrue("Master Editions II's booster must contain 1 snow covered land", booster.stream().anyMatch(card -> card.isBasic() && card.getName().startsWith("Snow-Covered ")));
        }
    }

    @Test
    public void testBattlebond_BoosterMustHaveOneLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = Battlebond.getInstance().createBooster();
            assertTrue("battlebond's booster must contain 1 land", booster.stream().anyMatch(card -> card.isBasic() && card.isLand()));
        }
    }

    private static String str(List<Card> cards) {
        StringBuilder sb = new StringBuilder("[");
        Iterator<Card> iterator = cards.iterator();
        while (iterator.hasNext()) {
            Card next = iterator.next();
            sb.append(next.getName());
            if (iterator.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append("] (").append(cards.size()).append(')');
        return sb.toString();
    }

    private static boolean contains(List<Card> cards, List<String> names, String code) {
        return names.stream().anyMatch((name) -> (contains(cards, name, code)));
    }

    private static boolean contains(List<Card> cards, String name, String code) {
        return cards.stream().anyMatch((card)
                -> (card.getName().equals(name)
                && (code == null || card.getExpansionSetCode().equals(code)))
        );
    }

}
