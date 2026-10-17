package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TheMasamuneTest extends CardTestPlayerBase {

    @Test
    public void test_OneOrMoreCreaturesDie_TriggersAdditionalTime() {
        // Equipped creature has "If a creature dying causes a triggered ability of this creature or an emblem you own
        // to trigger, that ability triggers an additional time."
        // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "The Masamune");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        //
        // Whenever one or more other creatures die, scry 1.
        addCard(Zone.BATTLEFIELD, playerA, "Great Fierce Bee");
        //
        // Each player sacrifices a creature.
        addCard(Zone.HAND, playerA, "Innocent Blood"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}", 2);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Great Fierce Bee");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // two creatures die at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Innocent Blood");
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerB, "Grizzly Bears");
        setChoice(playerA, "Whenever one or more other creatures die"); // two triggers order
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry 1: keep on top
        addTarget(playerA, TestPlayer.TARGET_SKIP); // scry 1: keep on top

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
    }
}
