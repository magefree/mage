package org.mage.test.cards.single.tdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TaigamMasterOpportunistTest extends CardTestPlayerBase {

    private static final String TAIGAM = "Taigam, Master Opportunist";
    private static final String ORNITHOPTER = "Ornithopter";
    private static final String TWINMAW = "Twinmaw Stormbrood";
    private static final String BITE = "Charring Bite";
    private static final String TURTLE = "Aegis Turtle";
    private static final String AKOUM = "Akoum Warrior";

    @Test
    public void testCardWithSpellOption() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, TAIGAM);
        addCard(Zone.BATTLEFIELD, playerA, "Yawgmoth's Bargain"); // Can shuffle to the top of the library, prevent drawing it
        addCard(Zone.HAND, playerA, ORNITHOPTER);
        addCard(Zone.HAND, playerA, TWINMAW);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 6);
        addCard(Zone.BATTLEFIELD, playerB, TURTLE, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ORNITHOPTER, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, BITE, TURTLE);
        checkCardCounters("time counters", 1, PhaseStep.BEGIN_COMBAT, playerA, TWINMAW, CounterType.TIME, 4);
        setChoice(playerA, true);
        setChoice(playerA, "Cast " + BITE);
        addTarget(playerA, TURTLE);

        setStopAt(9, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLibraryCount(playerA, TWINMAW, 1);
        assertGraveyardCount(playerB, TURTLE, 2);
    }

    @Test
    public void testMDFC() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, TAIGAM);
        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 6);
        addCard(Zone.HAND, playerA, ORNITHOPTER);
        addCard(Zone.HAND, playerA, AKOUM);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ORNITHOPTER, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, AKOUM);
        setChoice(playerA, true);
        setChoice(playerA, "Play Akoum Teeth");

        setStopAt(9, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Akoum Teeth", 1);
        assertTapped("Akoum Teeth", true);
    }

    @Test
    public void testMDFC2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Delay");
        addCard(Zone.HAND, playerA, AKOUM);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, AKOUM);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Delay", AKOUM);
        setChoice(playerA, true);
        setChoice(playerA, "Play Akoum Teeth");

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Akoum Teeth", 1);
        assertTapped("Akoum Teeth", true);
    }

    @Test
    public void test() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Plateau", 6);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerB, TURTLE);
        addCard(Zone.HAND, playerB, "Delay");
        addCard(Zone.HAND, playerA, TWINMAW);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, TWINMAW);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Delay", TWINMAW);
        setChoice(playerA, true);
        setChoice(playerA, "Cast " + BITE);
        addTarget(playerA, TURTLE);

        setStopAt(7, PhaseStep.PRECOMBAT_MAIN);
        execute();

    }

}
