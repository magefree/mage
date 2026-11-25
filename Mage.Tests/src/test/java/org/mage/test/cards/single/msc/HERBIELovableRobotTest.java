package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HERBIELovableRobotTest extends CardTestPlayerBase {

    /**
     * H.E.R.B.I.E., Lovable Robot {2}
     * Legendary Artifact Creature — Robot Scout 1/1
     * Flying
     * At the beginning of combat on your turn, if you've cast a noncreature spell this turn, surveil 1.
     * {T}: Add {C}.
     * {1}, {T}: Add one mana of any color.
     */
    private static final String herbie = "H.E.R.B.I.E., Lovable Robot";

    @Test
    public void test_NoncreatureSpell_Surveil() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, herbie);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // surveil 1
        checkStackSize("on trigger", 1, PhaseStep.BEGIN_COMBAT, playerA, 1);
        addTarget(playerA, "Grizzly Bears"); // put into graveyard

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertLibraryCount(playerA, 0);
    }

    @Test
    public void test_CreatureSpell_NoTrigger() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, herbie);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        addCard(Zone.HAND, playerA, "Memnite"); // {0}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite");

        // a creature spell doesn't count, so no trigger and no surveil dialog
        checkStackSize("no trigger", 1, PhaseStep.BEGIN_COMBAT, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Memnite", 1);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_OpponentNoncreatureSpell_NoTrigger() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, herbie);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        addCard(Zone.HAND, playerB, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        // only your own spells count
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkStackSize("no trigger", 1, PhaseStep.BEGIN_COMBAT, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20 - 3);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
    }

    @Test
    public void test_OpponentTurn_NoTrigger() {
        skipInitShuffling();
        removeAllCardsFromLibrary(playerA);

        addCard(Zone.BATTLEFIELD, playerA, herbie);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears");
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        // your noncreature spell on the opponent's turn, but it's not your combat
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);
        checkStackSize("no trigger", 2, PhaseStep.BEGIN_COMBAT, playerB, 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertLibraryCount(playerA, "Grizzly Bears", 1);
    }
}
