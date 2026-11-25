package org.mage.test.lki;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Short-living LKI must not outlive the action that produced it. A player who
 * holds priority and activates again must not let a permanent that left the
 * battlefield on the previous activation look back at the next event
 * (CR 603.10a: only "immediately prior to the event" counts).
 */
public class ShortLivingLKIHeldPriorityTest extends CardTestPlayerBase {

    // Sacrifice another creature or artifact: Surveil 1.
    private static final String ZEALOT = "Umbral Collar Zealot";
    // Whenever you sacrifice another creature or artifact, put a +1/+1 counter on this creature.
    private static final String EVANGEL = "Lightless Evangel";

    @Test
    public void test_SacrificedSourceDoesNotTriggerOnLaterActivation() {
        removeAllCardsFromLibrary(playerA); // nothing to surveil, so no choice dialogs
        addCard(Zone.BATTLEFIELD, playerA, ZEALOT);
        addCard(Zone.BATTLEFIELD, playerA, EVANGEL);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        // first activation sacrifices the Evangel itself (not "another", so no trigger)
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice another creature or artifact: Surveil 1");
        setChoice(playerA, EVANGEL);
        // second activation, priority still held: the Evangel is already in the graveyard
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice another creature or artifact: Surveil 1");
        setChoice(playerA, "Grizzly Bears");
        // third one, the Lion
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice another creature or artifact: Surveil 1");
        setChoice(playerA, "Silvercoat Lion");

        // only the three Zealot abilities may be on the stack, no Evangel trigger
        checkStackSize("no Evangel trigger after it left", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 3);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, EVANGEL, 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
    }

    @Test
    public void test_LiveSourceStillTriggersOnEachActivation() {
        removeAllCardsFromLibrary(playerA); // nothing to surveil, so no choice dialogs
        addCard(Zone.BATTLEFIELD, playerA, ZEALOT);
        addCard(Zone.BATTLEFIELD, playerA, EVANGEL);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice another creature or artifact: Surveil 1");
        setChoice(playerA, "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice another creature or artifact: Surveil 1");
        setChoice(playerA, "Silvercoat Lion");

        // two Zealot abilities plus one Evangel trigger per sacrifice
        checkStackSize("Evangel triggers per sacrifice", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 4);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
    }
}
