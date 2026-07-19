package org.mage.test.sets;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardScanner;
import mage.constants.Rarity;
import mage.constants.SubType;
import mage.game.draft.ReshuffledSet;
import mage.sets.*;
import mage.util.CardUtil;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.MageTestPlayerBase;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author nigelzor, JayDi85
 */
public class BoosterGenerationTest extends MageTestPlayerBase {

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

    @BeforeEach
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
                        Assertions.assertEquals(Partner, card.getName());
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
        assertTrue(contains(booster, tapland, "FRF")
                || contains(booster, fetchland, "KTK")
                || contains(booster, basics, null), str(booster)
        );
    }

    @Test
    public void testMysteryBooster1() {
        // https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
        List<Card> booster = MysteryBooster.getInstance().createBooster();
        Assertions.assertNotNull(booster);
        Assertions.assertEquals(15, booster.size(), "Pack contains 15 cards");

        Assertions.assertTrue(booster.get(0).getColorIdentity().isWhite(), "Slot 1 is white (" + booster.get(0).getName() + ')');
        Assertions.assertTrue(booster.get(1).getColorIdentity().isWhite(), "Slot 2 is white (" + booster.get(1).getName() + ')');

        Assertions.assertTrue(booster.get(2).getColorIdentity().isBlue(), "Slot 3 is blue (" + booster.get(2).getName() + ')');
        Assertions.assertTrue(booster.get(3).getColorIdentity().isBlue(), "Slot 4 is blue (" + booster.get(3).getName() + ')');

        Assertions.assertTrue(booster.get(4).getColorIdentity().isBlack(), "Slot 5 is black (" + booster.get(4).getName() + ')');
        Assertions.assertTrue(booster.get(5).getColorIdentity().isBlack(), "Slot 6 is black (" + booster.get(5).getName() + ')');

        Assertions.assertTrue(booster.get(6).getColorIdentity().isRed(), "Slot 7 is red (" + booster.get(6).getName() + ')');
        Assertions.assertTrue(booster.get(7).getColorIdentity().isRed(), "Slot 8 is red (" + booster.get(7).getName() + ')');

        Assertions.assertTrue(booster.get(8).getColorIdentity().isGreen(), "Slot 9 is green (" + booster.get(8).getName() + ')');
        Assertions.assertTrue(booster.get(9).getColorIdentity().isGreen(), "Slot 10 is green (" + booster.get(9).getName() + ')');

        Assertions.assertTrue(booster.get(10).getColorIdentity().isMulticolored(), "Slot 11 is multicolored (" + booster.get(10).getName() + ')');
        Assertions.assertTrue(booster.get(11).getColor().isColorless(), "Slot 12 is colorless (" + booster.get(11).getName() + ')');
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
        assertTrue(contains(booster, snowCoveredLand, "ME2"), str(booster));
        assertFalse(contains(booster, basics, null), str(booster));
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
            assertTrue(contains(booster, urzaLand, "ME4"), str(booster));
            assertFalse(contains(booster, basics, null), str(booster));
        }
    }

    @Test
    public void testCoreSet2019_DualLandsAreGenerated() {
        List<Card> allCards = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            List<Card> booster = CoreSet2019.getInstance().createBooster();
            // check that booster contains a land card
            assertTrue(booster.stream().anyMatch(MageObject::isLand));
            allCards.addAll(booster);
        }
        // check that some dual lands were generated
        assertTrue(allCards.stream().anyMatch(card -> card.isLand() && Objects.equals(card.getRarity(), Rarity.COMMON)));
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
    public void testModernHorizons_BoosterMustHaveOneSnowLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = ModernHorizons.getInstance().createBooster();
            assertTrue(booster.stream().anyMatch(card -> card.isBasic() && card.getName().startsWith("Snow-Covered ")), "Modern Horizon's booster must contain 1 snow covered land");
        }
    }

    @Test
    public void testMastersEditionII_BoosterMustHaveOneSnowLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = MastersEditionII.getInstance().createBooster();
            assertTrue(booster.stream().anyMatch(card -> card.isBasic() && card.getName().startsWith("Snow-Covered ")), "Master Editions II's booster must contain 1 snow covered land");
        }
    }

    @Test
    public void testBattlebond_BoosterMustHaveOneLand() {
        for (int i = 0; i < 10; i++) {
            List<Card> booster = Battlebond.getInstance().createBooster();
            assertTrue(booster.stream().anyMatch(card -> card.isBasic() && card.isLand()), "battlebond's booster must contain 1 land");
        }
    }

    @Test
    public void testFallenEmpires_BoosterMustUseVariousArtsButUnique() {
        // Related issue: https://github.com/magefree/mage/issues/7333
        // Actual for default boosters without collation
        Set<String> cardNumberPostfixes = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            List<Card> booster = FallenEmpires.getInstance().createBooster();

            // must have single version of the card for booster generation
            Map<String, Long> stats = FallenEmpires.getInstance().getCardsByRarity(Rarity.COMMON)
                    .stream()
                    .collect(Collectors.groupingBy(CardInfo::getName, Collectors.counting()));
            String multipleCopies = stats.entrySet()
                    .stream()
                    .filter(data -> data.getValue() > 1)
                    .map(Map.Entry::getKey)
                    .collect(Collectors.joining(", "));
            assertTrue(multipleCopies.isEmpty(), "booster generation must use cards with various arts as one card: " + multipleCopies);

            // must have all reprints
            booster.forEach(card -> {
                // 123c -> c
                String postfix = card.getCardNumber().replace(String.valueOf(CardUtil.parseCardNumberAsInt(card.getCardNumber())), "");
                if (!postfix.isEmpty()) {
                    cardNumberPostfixes.add(postfix);
                }
            });
        }
        assertTrue(cardNumberPostfixes.contains("a")
                        && cardNumberPostfixes.contains("b")
                        && cardNumberPostfixes.contains("c")
                        && cardNumberPostfixes.contains("d"),
                "booster must use cards with various arts"
        );
    }

    @Test
    public void testZendikarRising_MDFC() {
        for (int i = 0; i < 20; i++) {
            List<Card> booster = ZendikarRising.getInstance().createBooster();

            assertEquals(15, booster.size(), "Booster does not have 15 cards");
            assertTrue(
                    booster.stream().map(Card::getExpansionSetCode).allMatch("ZNR"::equals),
                    "Booster contains cards from another set"
            );
            assertEquals(
                    1, booster.stream().filter(MageObject::isBasic).count(),
                    "Booster must contain exactly 1 basic land"
            );
            assertEquals(
                    1, booster.stream().map(Card::getRarity).filter(rarity -> rarity == Rarity.RARE || rarity == Rarity.MYTHIC).count(),
                    "Booster must contain exactly 1 rare or mythic"
            );
            assertEquals(
                    3, booster.stream().map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count(),
                    "Booster must contain exactly 3 uncommons"
            );
            assertEquals(
                    10, booster.stream().map(Card::getRarity).filter(Rarity.COMMON::equals).count(),
                    "Booster must contain exactly 10 uncommons"
            );
            assertEquals(
                    1, booster.stream().filter(ModalDoubleFacedCard.class::isInstance).count(),
                    "Booster must contain exactly 1 MDFC"
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

            assertEquals(15, booster.size(), "Booster does not have 15 cards");
            assertTrue(
                    booster.stream().map(Card::getExpansionSetCode).allMatch("KHM"::equals),
                    "Booster contains cards from another set"
            );
            assertFalse(
                    booster.stream().anyMatch(card -> card.isBasic() && !card.isSnow()),
                    "Booster cannot contain non-snow basic lands"
            );
            assertEquals(
                    1, booster.stream().map(Card::getRarity).filter(rarity -> rarity == Rarity.RARE || rarity == Rarity.MYTHIC).count(),
                    "Booster must contain exactly 1 rare or mythic"
            );
            assertEquals(
                    3, booster.stream().map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count(),
                    "Booster must contain exactly 3 uncommons"
            );

            List<Card> snowLands = booster.stream().filter(card -> card.isSnow() && card.isLand()).collect(Collectors.toList());
            switch (snowLands.size()) {
                case 0:
                    fail("Booster must have snow lands");
                case 1:
                    Card snowLand = snowLands.get(0);
                    assertTrue(
                            snowLand.isBasic() || snowLand.getRarity() == Rarity.COMMON,
                            "Only one snow land, must be basic or common"
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
                            1, snowLands.stream().filter(card -> card.getName().equals("Shimmerdrift Vale") || card.getName().equals("Faceless Haven")).count(),
                            "Booster can't have two snow lands unless one is Shimmerdrift Vale or Faceless Haven"
                    );
                    assertEquals(
                            1, snowLands.stream().filter(card -> !card.getName().equals("Shimmerdrift Vale") && !card.getName().equals("Faceless Haven")).count(),
                            "Booster can't have two snow lands unless one is not Shimmerdrift Vale or Faceless Haven"
                    );
                    break;
                case 3:
                    assertEquals(1, snowLands.stream().filter(card -> card.getName().equals("Shimmerdrift Vale")).count(),
                            "Booster can't have three snow lands unless one is Shimmerdrift Vale"
                    );
                    assertEquals(1, snowLands.stream().filter(card -> card.getName().equals("Faceless Haven")).count(),
                            "Booster can't have three snow lands unless one is Faceless Haven"
                    );
                    assertEquals(1, snowLands.stream().filter(card -> !card.getName().equals("Shimmerdrift Vale") && !card.getName().equals("Faceless Haven")).count(),
                            "Booster can't have three snow lands unless one is not Shimmerdrift Vale or Faceless Haven"
                    );
                    break;
                default:
                    fail("Booster can't have more than three snow lands");
            }

            long mdfcCount = booster.stream().filter(ModalDoubleFacedCard.class::isInstance).count();
            assertTrue(mdfcCount < 2, "Booster can't have more than one MDFC");

            foundMDFC |= mdfcCount > 0;
            foundNoMDFC |= mdfcCount == 0;
            foundVale |= booster.stream().map(MageObject::getName).anyMatch("Shimmerdrift Vale"::equals);
            if (foundVale && foundMDFC && foundNoMDFC && i > 20) {
                break;
            }
        }
        assertTrue(foundVale, "No booster contained Shimmerdrift Vale");
        assertTrue(foundMDFC, "No booster contained an MDFC");
        assertTrue(foundNoMDFC, "Every booster contained an MDFC");
    }

    @Test
    public void testTimeSpiralRemastered_BonusSheet() {
        for (int i = 1; i <= 5; i++) {
            List<Card> booster = TimeSpiralRemastered.getInstance().createBooster();

            assertFalse(
                    contains(booster, basics, null),
                    "Booster should have no basic lands:" + str(booster)
            );

            assertEquals(
                    10, booster.stream().map(Card::getRarity).filter(Rarity.COMMON::equals).count(),
                    "Booster should have 10 commons"
            );
            assertEquals(
                    3, booster.stream().map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count(),
                    "Booster should have 3 uncommons"
            );
            assertEquals(
                    1, booster.stream().map(Card::getRarity).filter(r -> r == Rarity.RARE || r == Rarity.MYTHIC).count(),
                    "Booster should have 1 rare/mythic"
            );
            assertEquals(
                    1, booster.stream().map(Card::getRarity).filter(Rarity.SPECIAL::equals).count(),
                    "Booster should have 1 bonus card"
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

            assertEquals(15, booster.size(), "Booster should have 15 cards");

            assertFalse(
                    contains(booster, basics, null),
                    "Booster should have no basic lands:" + str(booster)
            );

            assertEquals(
                    9, nonLessons.stream().map(Card::getRarity).filter(Rarity.COMMON::equals).count(),
                    "Booster should have 9 non-Lesson commons"
            );
            assertEquals(
                    3, booster.stream().filter(c -> "STX".equals(c.getExpansionSetCode())).map(Card::getRarity).filter(Rarity.UNCOMMON::equals).count(),
                    "Booster should have 3 uncommons"
            );
            assertEquals(
                    1, nonLessons.stream().map(Card::getRarity).filter(r -> r == Rarity.RARE || r == Rarity.MYTHIC).count(),
                    "Booster should have 1 non-Lesson rare/mythic"
            );

            assertEquals(
                    1, booster.stream().map(Card::getExpansionSetCode).filter("STA"::equals).count(),
                    "Booster should have 1 Mystical Archive card"
            );

            assertTrue(lessons.size() <= 2, "Booster should have no more than 2 total Lessons");
            assertEquals(
                    1, lessons.stream().filter(c -> c.getRarity() != Rarity.UNCOMMON).count(),
                    "Booster should have 1 non-uncommon Lesson"
            );
            long uncommonLessonCount = lessons.stream().filter(c -> c.getRarity() == Rarity.UNCOMMON).count();
            assertTrue(uncommonLessonCount <= 1, "Booster should have no more than 1 uncommon Lesson");

            foundUncommonLesson |= uncommonLessonCount > 0;
            foundNoUncommonLesson |= uncommonLessonCount == 0;
            if (foundUncommonLesson && foundNoUncommonLesson && i > 20) {
                break;
            }
        }
        assertTrue(foundUncommonLesson, "No booster contained an uncommon Lesson");
        assertTrue(foundNoUncommonLesson, "Every booster contained an uncommon Lesson");
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
                    1, reprint.size(), "Booster must contain exactly one reprint (other than fetches)"
            );
            assertEquals(
                    14, notReprint.size(), "Booster must contain exactly 14 other cards"
            );
            assertEquals(
                    1, notReprint
                            .stream()
                            .map(Card::getRarity)
                            .filter(rarity -> rarity == Rarity.RARE || rarity == Rarity.MYTHIC)
                            .count(),
                    "Booster must contain one non-reprint rare/mythic"
            );
            assertEquals(
                    3, notReprint
                            .stream()
                            .map(Card::getRarity)
                            .filter(Rarity.UNCOMMON::equals)
                            .count(),
                    "Booster must contain three non-reprint uncommons"
            );
            assertEquals(
                    10, notReprint
                            .stream()
                            .map(Card::getRarity)
                            .filter(Rarity.COMMON::equals)
                            .count(),
                    "Booster must contain ten non-reprint uncommons"
            );
        }
    }

    // String output formatter for the below debug test
    private static String getManaCostOrColorIdentity(Card card) {
        String result = card.getManaCost().getText();
        if (result.isEmpty()) {
            result = "[" + card.getColorIdentity().toString().replace("{", "").replace("}", "") + "]";
        }
        return result;
    }

    @Disabled // debug only: collect info about cards in boosters, see https://github.com/magefree/mage/issues/8081
    @Test
    public void test_CollectBoosterStats() {
        ExpansionSet setToAnalyse = NewPhyrexia.getInstance();
        // Takes about a minute for 100,000 boosters
        int openBoosters = 100000;

        Map<String, Integer> resRatio = new HashMap<>();
        int totalCards = 0;
        for (int i = 1; i <= openBoosters; i++) {
            List<Card> booster = setToAnalyse.createBooster();
            totalCards += booster.size();
            booster.forEach(card -> {
                String code = String.format("%s %s %3s  %-32s %18s",
                        card.getExpansionSetCode(),
                        card.getRarity().toString().charAt(0),
                        card.getCardNumber(),
                        card.getName(),
                        getManaCostOrColorIdentity(card));
                resRatio.putIfAbsent(code, 0);
                resRatio.computeIfPresent(code, (u, count) -> count + 1);
            });
        }
        System.out.println(setToAnalyse.getName() + " - boosters opened: " + openBoosters + ". Found cards: " + totalCards + "\n");
        for (char rarity : Arrays.asList('C', 'U', 'R', 'M', 'S', 'L', 'B')) {
            List<Integer> rarityCounts = resRatio.entrySet().stream()
                    .filter(e -> e.getKey().charAt(4) == rarity)
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            if (!rarityCounts.isEmpty()) {
                System.out.println(rarity + String.format(": %3s unique, min %5s, max %5s, total %7s",
                        rarityCounts.size(), Collections.min(rarityCounts), Collections.max(rarityCounts),
                        rarityCounts.stream().mapToInt(x -> x).sum()));
            }
        }
        List<String> info = resRatio.entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .map(e -> String.format("%s: %5d",
                        e.getKey(),
                        e.getValue()
                ))
                .collect(Collectors.toList());
        System.out.println("\n" + String.join("\n", info));
    }

    @Disabled // debug only
    @Test
    public void test_ReshuffledBoosterStats() {
        List<ExpansionSet> sets = new ArrayList<>();
        sets.add(ScarsOfMirrodin.getInstance());
        sets.add(MirrodinBesieged.getInstance());
        sets.add(NewPhyrexia.getInstance());
        ReshuffledSet setToAnalyse = new ReshuffledSet(sets, 10, 3, 1);
        int openBoosters = 10000;

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
        List<String> info = resRatio.entrySet().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getValue(), o1.getValue()))
                .map(e -> String.format("%s: %d",
                        e.getKey(),
                        e.getValue()
                ))
                .collect(Collectors.toList());
        System.out.println("Boosters opened: " + openBoosters + ". Found cards: " + totalCards + "\n"
                + String.join("\n", info));
    }

}
