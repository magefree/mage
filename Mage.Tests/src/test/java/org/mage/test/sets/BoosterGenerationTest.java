package org.mage.test.sets;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.ModalDoubleFacesCard;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardScanner;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.sets.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.MageTestBase;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author nigelzor, JayDi85
 */
public class BoosterGenerationTest extends MageTestBase {

    private static final List<String> basics = new ArrayList<>(Arrays.asList("Plains", "Island", "Swamp", "Mountain", "Forest"));

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

    @Before
    public void setUp() {
        CardScanner.scan();
    }

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
        assertTrue(str(booster), contains(booster, tapland, "FRF")
                || contains(booster, fetchland, "KTK")
                || contains(booster, basics, null)
        );
    }

    @Test
    public void testMysteryBooster1() {
        // https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
        List<Card> booster = MysteryBooster.getInstance().createBooster();
        Assert.assertNotNull(booster);
        Assert.assertEquals("Pack contains 15 cards", 15, booster.size());

        Assert.assertTrue("Slot 1 is white", booster.get(0).getColor(null).isWhite());
        Assert.assertTrue("Slot 2 is white", booster.get(1).getColor(null).isWhite());

        // Wretched Gryff is colorless, but stores in blue slots
        Assert.assertTrue("Slot 3 is blue", booster.get(2).getName().equals("Wretched Gryff") || booster.get(2).getColor(null).isBlue());
        Assert.assertTrue("Slot 4 is blue", booster.get(3).getName().equals("Wretched Gryff") || booster.get(3).getColor(null).isBlue());

        Assert.assertTrue("Slot 5 is black", booster.get(4).getColor(null).isBlack());
        Assert.assertTrue("Slot 6 is black", booster.get(5).getColor(null).isBlack());

        Assert.assertTrue("Slot 7 is red", booster.get(6).getColor(null).isRed());
        Assert.assertTrue("Slot 8 is red", booster.get(7).getColor(null).isRed());

        Assert.assertTrue("Slot 9 is green", booster.get(8).getColor(null).isGreen());
        Assert.assertTrue("Slot 10 is green", booster.get(9).getColor(null).isGreen());

        Assert.assertTrue("Slot 11 is multicolored", booster.get(10).getColor(null).isMulticolored());
        Assert.assertTrue("Slot 12 is colorless", booster.get(11).getColor(null).isColorless());

        Assert.assertEquals("Slot 15 is from FMB1 set", "FMB1", booster.get(14).getExpansionSetCode());
    }

    @Test
    public void testMysteryBooster1Create15CardBooster() {
        // https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
        List<Card> booster = MysteryBooster.getInstance().create15CardBooster();
        Assert.assertNotNull(booster);
        Assert.assertEquals("Pack contains 15 cards", 15, booster.size());

        Assert.assertTrue("Slot 1 is white (" + booster.get(0).getName() + ')', booster.get(0).getColorIdentity().isWhite());
        Assert.assertTrue("Slot 2 is white (" + booster.get(1).getName() + ')', booster.get(1).getColorIdentity().isWhite());

        Assert.assertTrue("Slot 3 is blue (" + booster.get(2).getName() + ')', booster.get(2).getColorIdentity().isBlue());
        Assert.assertTrue("Slot 4 is blue (" + booster.get(3).getName() + ')', booster.get(3).getColorIdentity().isBlue());

        Assert.assertTrue("Slot 5 is black (" + booster.get(4).getName() + ')', booster.get(4).getColorIdentity().isBlack());
        Assert.assertTrue("Slot 6 is black (" + booster.get(5).getName() + ')', booster.get(5).getColorIdentity().isBlack());

        Assert.assertTrue("Slot 7 is red (" + booster.get(6).getName() + ')', booster.get(6).getColorIdentity().isRed());
        Assert.assertTrue("Slot 8 is red (" + booster.get(7).getName() + ')', booster.get(7).getColorIdentity().isRed());

        Assert.assertTrue("Slot 9 is green (" + booster.get(8).getName() + ')', booster.get(8).getColorIdentity().isGreen());
        Assert.assertTrue("Slot 10 is green (" + booster.get(9).getName() + ')', booster.get(9).getColorIdentity().isGreen());

        Assert.assertTrue("Slot 11 is multicolored (" + booster.get(10).getName() + ')', booster.get(10).getColorIdentity().isMulticolored());
        Assert.assertTrue(
                "Slot 12 is colorless (" + booster.get(11).getName() + ')',
                booster.get(11).getColor(null).isColorless()
                        || booster.get(11).isLand(currentGame)
                        || booster.get(11).isArtifact(currentGame)
        );

        Assert.assertEquals("Slot 15 is from FMB1 set", "FMB1", booster.get(14).getExpansionSetCode());
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
            assertTrue(booster.stream().anyMatch(card -> card.getCardType(currentGame).contains(CardType.LAND)));
            allCards.addAll(booster);
        }
        // check that some dual lands were generated
        assertTrue(allCards.stream().anyMatch(card -> card.getCardType(currentGame).contains(CardType.LAND) && Objects.equals(card.getRarity(), Rarity.COMMON)));
    }

    @Test
    public void testWarOfTheSpark_EveryBoosterContainsPlaneswalker() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = WarOfTheSpark.getInstance().createBooster();
            // check that booster contains a planeswalker
            assertTrue(booster.stream().anyMatch(card -> card.isPlaneswalker(currentGame)));
        }
    }

    @Test
    public void testDominaria_EveryBoosterContainsLegendaryCreature() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = Dominaria.getInstance().createBooster();
            // check that booster contains legendary creature
            assertTrue(booster.stream().anyMatch(card -> card.isCreature(currentGame) && card.isLegendary()));
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
            assertTrue("battlebond's booster must contain 1 land", booster.stream().anyMatch(card -> card.isBasic() && card.isLand(currentGame)));
        }
    }

    @Test
    public void testZendikarRising_MDFC() {
        for (int i = 0; i < 20; i++) {
            List<Card> booster = ZendikarRising.getInstance().createBooster();

            assertEquals("Booster does not have 15 cards", 15, booster.size());
            assertTrue(
                    "Booster contains cards from another set",
                    booster.stream().map(Card::getExpansionSetCode).allMatch("ZNR"::equals)
            );
            assertEquals(
                    "Booster must contain exactly 1 basic land", 1,
                    booster.stream().filter(MageObject::isBasic).count()
            );
            assertEquals(
                    "Booster must contain exactly 1 rare or mythic", 1,
                    booster.stream().map(Card::getRarity).filter(rarity -> rarity == Rarity.RARE || rarity == Rarity.MYTHIC).count()
            );
            assertEquals(
                    "Booster must contain exactly 3 uncommons", 3,
                    booster.stream().map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count()
            );
            assertEquals(
                    "Booster must contain exactly 10 uncommons", 10,
                    booster.stream().map(Card::getRarity).filter(Rarity.COMMON::equals).count()
            );
            assertEquals(
                    "Booster must contain exactly 1 MDFC", 1,
                    booster.stream().filter(ModalDoubleFacesCard.class::isInstance).count()
            );
        }
    }

    @Test
    public void testKaldheim_SnowLandAndMDFC() {
        boolean foundVale = false;
        boolean foundMDFC = false;
        boolean foundNoMDFC = false;

        for (int i = 1; i <= 100; i++) {
            List<Card> booster = Kaldheim.getInstance().createBooster();

            assertEquals("Booster does not have 15 cards", 15, booster.size());
            assertTrue(
                    "Booster contains cards from another set",
                    booster.stream().map(Card::getExpansionSetCode).allMatch("KHM"::equals)
            );
            assertFalse(
                    "Booster cannot contain non-snow basic lands",
                    booster.stream().anyMatch(card -> card.isBasic() && !card.isSnow())
            );
            assertEquals(
                    "Booster must contain exactly 1 rare or mythic", 1,
                    booster.stream().map(Card::getRarity).filter(rarity -> rarity == Rarity.RARE || rarity == Rarity.MYTHIC).count()
            );
            assertEquals(
                    "Booster must contain exactly 3 uncommons", 3,
                    booster.stream().map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count()
            );

            List<Card> snowLands = booster.stream().filter(card -> card.isSnow() && card.isLand(currentGame)).collect(Collectors.toList());
            switch (snowLands.size()) {
                case 0:
                    fail("Booster must have snow lands");
                case 1:
                    Card snowLand = snowLands.get(0);
                    assertTrue(
                            "Only one snow land, must be basic or common",
                            snowLand.isBasic() || snowLand.getRarity() == Rarity.COMMON
                    );
                    assertNotEquals(
                            "Only one snow land, can't be Shimmerdrift Vale",
                            "Shimmerdrift Vale", snowLand.getName()
                    );
                    assertNotEquals(
                            "Only one snow land, can't be Faceless Haven",
                            "Faceless Haven", snowLand.getName()
                    );
                    break;
                case 2:
                    assertEquals(
                            "Booster can't have two snow lands unless one is Shimmerdrift Vale or Faceless Haven", 1,
                            snowLands.stream().filter(card -> card.getName().equals("Shimmerdrift Vale") || card.getName().equals("Faceless Haven")).count()
                    );
                    assertEquals(
                            "Booster can't have two snow lands unless one is not Shimmerdrift Vale or Faceless Haven", 1,
                            snowLands.stream().filter(card -> !card.getName().equals("Shimmerdrift Vale") && !card.getName().equals("Faceless Haven")).count()
                    );
                    break;
                case 3:
                    assertEquals("Booster can't have three snow lands unless one is Shimmerdrift Vale", 1,
                            snowLands.stream().filter(card -> card.getName().equals("Shimmerdrift Vale")).count()
                    );
                    assertEquals("Booster can't have three snow lands unless one is Faceless Haven", 1,
                            snowLands.stream().filter(card -> card.getName().equals("Faceless Haven")).count()
                    );
                    assertEquals("Booster can't have three snow lands unless one is not Shimmerdrift Vale or Faceless Haven", 1,
                            snowLands.stream().filter(card -> !card.getName().equals("Shimmerdrift Vale") && !card.getName().equals("Faceless Haven")).count()
                    );
                    break;
                default:
                    fail("Booster can't have more than three snow lands");
            }

            long mdfcCount = booster.stream().filter(card -> card instanceof ModalDoubleFacesCard).count();
            assertTrue("Booster can't have more than one MDFC", mdfcCount < 2);

            foundMDFC |= mdfcCount > 0;
            foundNoMDFC |= mdfcCount == 0;
            foundVale |= booster.stream().map(MageObject::getName).anyMatch("Shimmerdrift Vale"::equals);
            if (foundVale && foundMDFC && foundNoMDFC && i > 20) {
                break;
            }
        }
        assertTrue("No booster contained Shimmerdrift Vale", foundVale);
        assertTrue("No booster contained an MDFC", foundMDFC);
        assertTrue("Every booster contained an MDFC", foundNoMDFC);
    }

    @Test
    public void testTimeSpiralRemastered_BonusSheet() {
        for (int i = 1; i <= 5; i++) {
            List<Card> booster = TimeSpiralRemastered.getInstance().createBooster();

            assertFalse(
                    "Booster should have no basic lands:" + str(booster),
                    contains(booster, basics, null)
            );

            assertEquals(
                    "Booster should have 10 commons", 10,
                    booster.stream().map(Card::getRarity).filter(Rarity.COMMON::equals).count()
            );
            assertEquals(
                    "Booster should have 3 uncommons", 3,
                    booster.stream().map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count()
            );
            assertEquals(
                    "Booster should have 1 rare/mythic", 1,
                    booster.stream().map(Card::getRarity).filter(r -> r == Rarity.RARE || r == Rarity.MYTHIC).count()
            );
            assertEquals(
                    "Booster should have 1 bonus card", 1,
                    booster.stream().map(Card::getRarity).filter(Rarity.SPECIAL::equals).count()
            );
        }
    }

    @Test
    public void testStrixhavenSchoolOfMages_LessonsAndArchive() {
        boolean foundUncommonLesson = false;
        boolean foundNoUncommonLesson = false;

        for (int i = 1; i <= 100; i++) {
            List<Card> booster = StrixhavenSchoolOfMages.getInstance().createBooster();
            List<Card> nonLessons = booster
                    .stream()
                    .filter(c -> "STX".equals(c.getExpansionSetCode()))
                    .filter(c -> !c.hasSubtype(SubType.LESSON, null))
                    .collect(Collectors.toList());
            List<Card> lessons = booster
                    .stream()
                    .filter(c -> "STX".equals(c.getExpansionSetCode()))
                    .filter(c -> c.hasSubtype(SubType.LESSON, null))
                    .collect(Collectors.toList());

            assertEquals("Booster should have 15 cards", 15, booster.size());

            assertFalse(
                    "Booster should have no basic lands:" + str(booster),
                    contains(booster, basics, null)
            );

            assertEquals(
                    "Booster should have 9 non-Lesson commons", 9,
                    nonLessons.stream().map(Card::getRarity).filter(Rarity.COMMON::equals).count()
            );
            assertEquals(
                    "Booster should have 3 uncommons", 3,
                    booster.stream().filter(c -> "STX".equals(c.getExpansionSetCode())).map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count()
            );
            assertEquals(
                    "Booster should have 1 non-Lesson rare/mythic", 1,
                    nonLessons.stream().map(Card::getRarity).filter(r -> r == Rarity.RARE || r == Rarity.MYTHIC).count()
            );

            assertEquals(
                    "Booster should have 1 Mystical Archive card", 1,
                    booster.stream().map(Card::getExpansionSetCode).filter("STA"::equals).count()
            );

            assertTrue("Booster should have no more than 2 total Lessons", lessons.size() <= 2);
            assertEquals(
                    "Booster should have 1 non-uncommon Lesson", 1,
                    lessons.stream().filter(c -> c.getRarity() != Rarity.UNCOMMON).count()
            );
            long uncommonLessonCount = lessons.stream().filter(c -> c.getRarity() == Rarity.UNCOMMON).count();
            assertTrue("Booster should have no more than 1 uncommon Lesson", uncommonLessonCount <= 1);

            foundUncommonLesson |= uncommonLessonCount > 0;
            foundNoUncommonLesson |= uncommonLessonCount == 0;
            if (foundUncommonLesson && foundNoUncommonLesson && i > 20) {
                break;
            }
        }
        assertTrue("No booster contained an uncommon Lesson", foundUncommonLesson);
        assertTrue("Every booster contained an uncommon Lesson", foundNoUncommonLesson);
    }

    private static final List<String> mh2Reprints = Collections.unmodifiableList(Arrays.asList(
            "262", "263", "264", "265", "266", "267", "268", "269", "270", "271", "272", "273", "274", "275",
            "276", "277", "278", "279", "280", "281", "282", "283", "284", "285", "286", "287", "288", "289",
            "290", "291", "292", "293", "294", "295", "296", "297", "298", "299", "300", "301", "302", "303",
            "308", "314", "319", "320", "321", "322", "325", "326", "387", "416", "419", "423", "491"
    ));

    @Test
    public void testModernHorizons2ReprintSlot() {
        for (int i = 0; i < 20; i++) {
            List<Card> booster = ModernHorizons2.getInstance().createBooster();
            List<Card> notReprint = booster
                    .stream()
                    .filter(card -> !mh2Reprints.contains(card.getCardNumber()))
                    .collect(Collectors.toList());
            List<Card> reprint = booster
                    .stream()
                    .filter(card -> mh2Reprints.contains(card.getCardNumber()))
                    .collect(Collectors.toList());

            assertEquals(
                    "Booster must contain exactly one reprint (other than fetches)", 1, reprint.size()
            );
            assertEquals(
                    "Booster must contain exactly 14 other cards", 14, notReprint.size()
            );
            assertEquals(
                    "Booster must contain one non-reprint rare/mythic", 1,
                    notReprint
                            .stream()
                            .map(Card::getRarity)
                            .filter(rarity -> rarity == Rarity.RARE || rarity == Rarity.MYTHIC)
                            .count()
            );
            assertEquals(
                    "Booster must contain three non-reprint uncommons", 3,
                    notReprint
                            .stream()
                            .map(Card::getRarity)
                            .filter(Rarity.UNCOMMON::equals)
                            .count()
            );
            assertEquals(
                    "Booster must contain ten non-reprint uncommons", 10,
                    notReprint
                            .stream()
                            .map(Card::getRarity)
                            .filter(Rarity.COMMON::equals)
                            .count()
            );
        }
    }

    @Ignore // debug only: collect info about cards in boosters, see https://github.com/magefree/mage/issues/8081
    @Test
    public void test_CollectBoosterStats() {
        ExpansionSet setToAnalyse = Innistrad.getInstance();
        int openBoosters = 1000;

        Map<String, Integer> resRatio = new HashMap<>();
        int totalCards = 0;
        for (int i = 1; i <= openBoosters; i++) {
            List<Card> booster = setToAnalyse.createBooster();
            totalCards += booster.size();
            booster.forEach(card -> {
                String code = String.format("%s %s", card.getRarity().getCode(), card.getName());
                resRatio.putIfAbsent(code, 0);
                resRatio.computeIfPresent(code, (u, count) -> count + 1);
            });
        }
        final Integer totalCardsFinal = totalCards;
        List<String> info = resRatio.entrySet().stream()
                .sorted(new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                        return Integer.compare(o2.getValue(), o1.getValue());
                    }
                })
                .map(e -> String.format("%s: %d",
                        e.getKey(),
                        e.getValue()
                        //(double) e.getValue() / totalCardsFinal * 100.0
                ))
                .collect(Collectors.toList());
        System.out.println(setToAnalyse.getName() + " - boosters opened: " + openBoosters + ". Found cards: " + totalCardsFinal + "\n"
                + info.stream().collect(Collectors.joining("\n")));
    }
}
