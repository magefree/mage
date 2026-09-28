package org.mage.test.cards.single.blc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author brahle
 */
public class ZinniaValleysVoiceTest extends CardTestPlayerBase {

    private static final String zinnia = "Zinnia, Valley's Voice";

    @Test
    public void testCastCreatureWithOffspringPaid() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true); // Pay {2} for offspring
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertTokenCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertPowerToughness(playerA, "Grizzly Bears", 1, 1);

        // Zinnia gets +1/+0 for the 1/1 Grizzly Bears token (base power 1)
        // Original Grizzly Bears has base power 2, so it doesn't give a bonus
        assertPowerToughness(playerA, zinnia, 2, 3);
    }

    @Test
    public void testCastCreatureWithoutOffspringPaid() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, false); // Do not pay offspring
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTokenCount(playerA, "Grizzly Bears", 0);
        assertPowerToughness(playerA, zinnia, 1, 3);
    }

    @Test
    public void testBasePowerTracking() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // 1/1 (base power 1)
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // 2/2 (base power 2)

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        // 1 other creature with base power 1 (Llanowar Elves)
        assertPowerToughness(playerA, zinnia, 2, 3);
    }

    @Test
    public void testBasePowerWithCounters() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // 1/1, gets counter -> 2/2, base power still 1
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Battlegrowth"); // Put a +1/+1 counter on target creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Battlegrowth", "Llanowar Elves");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Llanowar Elves", 2, 2);
        // Counters don't modify base power, so base power is still 1 -> Zinnia gets +1/+0
        assertPowerToughness(playerA, zinnia, 2, 3);
    }

    @Test
    public void testZinniaDiesBeforeCreatureResolves() {
        // "Creature spells you cast gain offspring {2} as you cast them."
        // Once offspring is paid during casting, the triggered ability will still
        // trigger and create a token even if Zinnia is removed before resolution.
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.HAND, playerB, "Murder");

        setChoice(playerA, true); // Pay {2} for offspring
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Murder", zinnia, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, zinnia, 1);
        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertTokenCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2, 2);
        assertPowerToughness(playerA, "Grizzly Bears", 1, 1);
    }

    @Test
    public void testMultipleOffspringInstances() {
        // Iridescent Vinelasher has printed Offspring {2}.
        // Zinnia grants an additional Offspring {2}.
        // Paying both creates two 1/1 tokens (total 3 Vinelashers).
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5); // {B} + {2} + {2}
        addCard(Zone.HAND, playerA, "Iridescent Vinelasher");

        setChoice(playerA, true); // Pay first offspring instance
        setChoice(playerA, true); // Pay second offspring instance
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Iridescent Vinelasher");

        // Order the two ETB triggers
        setChoice(playerA, "When {this} enters, if its offspring cost was paid");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Iridescent Vinelasher", 3);
        assertTokenCount(playerA, "Iridescent Vinelasher", 2);
    }

    @Test
    public void testPayOnlyGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.HAND, playerA, "Iridescent Vinelasher");

        setChoice(playerA, false); // Don't pay printed offspring
        setChoice(playerA, true);  // Pay Zinnia's granted offspring
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Iridescent Vinelasher");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTokenCount(playerA, "Iridescent Vinelasher", 1);
    }

    @Test
    public void testOffspringUnderDressDown() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Dress Down");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Dress Down", null, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTokenCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testTwoZinniasTwoOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, "Mirror Box");
        addCard(Zone.BATTLEFIELD, playerA, zinnia, 2);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true); // Pay first offspring
        setChoice(playerA, true); // Pay second offspring
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        // If two ETB triggers trigger, order them
        setChoice(playerA, "When {this} enters, if its offspring cost was paid");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 3);
        assertTokenCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testSparkDoubleZinniaTwoOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Spark Double");

        setChoice(playerA, false); // Don't pay offspring for Spark Double itself
        setChoice(playerA, true); // Choose to use Spark Double copy effect
        setChoice(playerA, zinnia); // Choose Zinnia to copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true); // Pay first offspring on Grizzly Bears
        setChoice(playerA, true); // Pay second offspring on Grizzly Bears
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setChoice(playerA, "When {this} enters, if its offspring cost was paid");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 3);
        assertTokenCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testSparkDoubleZinniaPayOnlyOneOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Spark Double");

        setChoice(playerA, false); // Don't pay offspring for Spark Double itself
        setChoice(playerA, true); // Choose to use Spark Double copy effect
        setChoice(playerA, zinnia); // Choose Zinnia to copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true); // Pay first offspring on Grizzly Bears
        setChoice(playerA, false); // Don't pay second offspring on Grizzly Bears
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertTokenCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testSparkDoubleZinniaPayOnlySecondOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.HAND, playerA, "Spark Double");

        setChoice(playerA, false); // Don't pay offspring for Spark Double itself
        setChoice(playerA, true); // Choose to use Spark Double copy effect
        setChoice(playerA, zinnia); // Choose Zinnia to copy
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Spark Double");

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, false); // Don't pay first offspring on Grizzly Bears
        setChoice(playerA, true); // Pay second offspring on Grizzly Bears
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertTokenCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void testCopiedSpellDoesNotCopyGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 6);
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Double Major");

        setChoice(playerA, true); // Pay offspring for the original spell
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Double Major", "Grizzly Bears", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // CR 707.2: Zinnia's granted ability isn't copiable. Only the original makes offspring.
        assertPermanentCount(playerA, "Grizzly Bears", 3);
        assertTokenCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testTorporOrbPreventsGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Torpor Orb");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTokenCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testPanharmoniconDoublesGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Panharmonicon");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        // Allow automatic ordering of the two identical offspring triggers.
        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTokenCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testHushbringerPreventsGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Hushbringer");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTokenCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testEleshNornDoublesGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Elesh Norn, Mother of Machines");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTokenCount(playerA, "Grizzly Bears", 2);
    }

    @Test
    public void testOpponentEleshNornPreventsGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerB, "Elesh Norn, Mother of Machines");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertTokenCount(playerA, "Grizzly Bears", 0);
    }

    @Test
    public void testYarokDoublesGrantedOffspring() {
        addCard(Zone.BATTLEFIELD, playerA, zinnia);
        addCard(Zone.BATTLEFIELD, playerA, "Yarok, the Desecrated");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Grizzly Bears");

        setChoice(playerA, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");

        setStrictChooseMode(false);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTokenCount(playerA, "Grizzly Bears", 2);
    }
}
