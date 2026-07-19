package org.mage.test.utils;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.util.CardUtil;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CardUtilTest extends CardTestPlayerBase {
    // Whenever you cast or copy an instant or sorcery spell, reveal the top card of your library.
    // If it’s a nonland card, you may cast it by paying {1} rather than paying its mana cost.
    // If it’s a land card, put it onto the battlefield.
    private static final String jadzi = "Jadzi, Oracle of Arcavios";
    // Whenever you discard a nonland card, you may cast it from your graveyard.
    private static final String oskar = "Oskar, Rubbish Reclaimer";
    // MDFC where the back side is a land "Akoum Teeth"
    private static final String akoumWarrior = "Akoum Warrior"; // {5}{R}
    // Discard your hand, then draw a card for each card you’ve discarded this turn.
    private static final String changeOfFortune = "Change of Fortune"; // {3}{R}
    // MDFC where both sides should be playable
    private static final String birgi = "Birgi, God of Storytelling"; // {2}{R}, frontside of Harnfel
    private static final String harnfel = "Harnfel, Horn of Bounty"; // {4}{R}, backside of Birgi
    private static final String tamiyo = "Tamiyo, Inquisitive Student"; // {U}, TDFC
    /**
     * Test that it will for trigger for discarding a MDFC but will only let you cast the nonland side.
     */
    @Test
    public void cantPlayLandSideOfMDFC() {
        addCard(Zone.HAND, playerA, changeOfFortune);
        addCard(Zone.HAND, playerA, akoumWarrior);

        addCard(Zone.BATTLEFIELD, playerA, oskar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 10);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, changeOfFortune);
        setChoice(playerA, "Yes");

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();
        assertPermanentCount(playerA, akoumWarrior, 1);
    }

    /**
     * Test that when both sides of a MDFC card match, we can choose either side.
     */
    @Test
    public void testFrontSideOfMDFC() {
        addCard(Zone.HAND, playerA, changeOfFortune);
        addCard(Zone.HAND, playerA, birgi, 2);

        addCard(Zone.BATTLEFIELD, playerA, oskar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 12);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, changeOfFortune);
        setChoice(playerA, "Whenever you discard");
        setChoice(playerA, "Yes");
        setChoice(playerA, "Cast " + birgi);
        setChoice(playerA, "Yes");
        setChoice(playerA, "Cast " + harnfel);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();
        assertPermanentCount(playerA, birgi, 1);
        assertPermanentCount(playerA, harnfel, 1);
    }

    /**
     * Test that with Jadzi, you are able to play the nonland side of a MDFC, and that the alternative cost works properly.
     */
    @Test
    public void testJadziPlayingLandAndCast() {
        addCard(Zone.BATTLEFIELD, playerA, jadzi);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1 + 1 + 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.LIBRARY, playerA, "Cragcrown Pathway");
        addCard(Zone.LIBRARY, playerA, akoumWarrior);

        skipInitShuffling();
        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        setChoice(playerA, "Yes");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();
        assertPermanentCount(playerA, akoumWarrior, 1);
        assertPermanentCount(playerA, "Cragcrown Pathway", 1);
    }

    @Test
    public void test_Substring() {
        String str = "12345";
        String ending = "...";

        Assertions.assertEquals("", CardUtil.substring(str, 0));
        Assertions.assertEquals("1", CardUtil.substring(str, 1));
        Assertions.assertEquals("12", CardUtil.substring(str, 2));
        Assertions.assertEquals("123", CardUtil.substring(str, 3));
        Assertions.assertEquals("1234", CardUtil.substring(str, 4));
        Assertions.assertEquals("12345", CardUtil.substring(str, 5));
        Assertions.assertEquals("12345", CardUtil.substring(str, 6));
        Assertions.assertEquals("12345", CardUtil.substring(str, 7));
        Assertions.assertEquals("12345", CardUtil.substring(str, 8));
        Assertions.assertEquals("12345", CardUtil.substring(str, 9));

        Assertions.assertEquals("", CardUtil.substring(str, 0, ending));
        Assertions.assertEquals(".", CardUtil.substring(str, 1, ending));
        Assertions.assertEquals("..", CardUtil.substring(str, 2, ending));
        Assertions.assertEquals("...", CardUtil.substring(str, 3, ending));
        Assertions.assertEquals("1...", CardUtil.substring(str, 4, ending));
        Assertions.assertEquals("12345", CardUtil.substring(str, 5, ending));
        Assertions.assertEquals("12345", CardUtil.substring(str, 6, ending));
        Assertions.assertEquals("12345", CardUtil.substring(str, 7, ending));
        Assertions.assertEquals("12345", CardUtil.substring(str, 8, ending));
        Assertions.assertEquals("12345", CardUtil.substring(str, 9, ending));
    }

    /**
     * Test that it will for trigger for discarding a TDFC but will only let you cast the front side.
     */
    @Test
    public void cantPlayTDFCBackSide() {
        addCard(Zone.HAND, playerA, changeOfFortune);
        addCard(Zone.HAND, playerA, tamiyo);

        addCard(Zone.BATTLEFIELD, playerA, oskar);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        skipInitShuffling();
        setStrictChooseMode(true);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {R}", 4);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, changeOfFortune);
        setChoice(playerA, "Yes");
        // only option is to cast front side, so auto chosen

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerA, tamiyo, 1);
    }

    @Test
    public void parseCardNumberAsInt() {
        // digit numbers
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("123"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("123a"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("123xxx"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("a123"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("xxx123"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("a123a"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("xxx123xxx"));
        Assertions.assertEquals(123456, CardUtil.parseCardNumberAsInt("xxx123xxx456xxx"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("-123"));
        Assertions.assertEquals(123, CardUtil.parseCardNumberAsInt("xxx-123xxx"));

        // non-digit numbers and sorting
        IntStream.range(0, 10).forEach(i -> {
            Assertions.assertEquals(CardUtil.parseCardNumberAsInt("abc"), CardUtil.parseCardNumberAsInt("abc"), "non-digit must be stable fake number");
            Assertions.assertTrue(CardUtil.parseCardNumberAsInt("abc") > CardUtil.parseCardNumberAsInt("123"), "non-digit must be > of any digit number");
            Assertions.assertTrue(CardUtil.parseCardNumberAsInt("abc") > CardUtil.parseCardNumberAsInt("x456x"), "non-digit must be > of any digit number");
        });

        // restricted empty number
        Assertions.assertThrows(IllegalArgumentException.class, () -> CardUtil.parseCardNumberAsInt(""));

        // restricted token's zero number
        Assertions.assertThrows(IllegalArgumentException.class, () -> CardUtil.parseCardNumberAsInt("0"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CardUtil.parseCardNumberAsInt("000"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> CardUtil.parseCardNumberAsInt("x0x"));
    }
}
