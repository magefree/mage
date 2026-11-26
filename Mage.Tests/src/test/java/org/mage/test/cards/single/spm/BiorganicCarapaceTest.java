package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Biorganic Carapace (SPM): "Equipped creature gets +2/+2 and has 'Whenever
 * this creature deals combat damage to a player, draw a card for each
 * modified creature you control.'"
 *
 * CR 120.2a: combat damage is dealt by attacking and blocking creatures.
 * CR 510.1c: a blocked creature assigns its combat damage to the creatures
 * blocking it, so that damage is not dealt to a player.
 * CR 603.2: a triggered ability triggers only when its trigger event occurs;
 * "deals combat damage to a player" does not match damage dealt to a creature.
 */
public class BiorganicCarapaceTest extends CardTestPlayerBase {

    private void setUpEquippedBears() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Biorganic Carapace");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Grizzly Bears");
    }

    @Test
    public void testBlockedEquippedCreatureDoesNotDraw() {
        setStrictChooseMode(true);
        setUpEquippedBears();
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Grizzly Bears", "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 0);
    }

    @Test
    public void testUnblockedEquippedCreatureDraws() {
        setStrictChooseMode(true);
        setUpEquippedBears();

        attack(1, playerA, "Grizzly Bears");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, 1);
    }
}
