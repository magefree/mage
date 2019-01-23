package org.mage.test.sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import mage.abilities.Ability;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardScanner;
import mage.constants.CardType;
import mage.constants.Rarity;

import mage.sets.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

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
        assertTrue(allCards.stream().anyMatch(card -> card.getCardType().contains(CardType.LAND) && card.getRarity().equals(Rarity.COMMON)));
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
    public void testMastersEditionII_BoosterMustHaveOneSnowLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = MastersEditionII.getInstance().createBooster();
            assertTrue("Master Editions II's booster must contain 1 snow covered land", booster.stream().anyMatch(card -> card.isBasic() && card.getName().startsWith("Snow-Covered ")));
        }
    }

    @Test
    public void testBattlebond_BoosterMustHaveOneLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = Coldsnap.getInstance().createBooster();
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
