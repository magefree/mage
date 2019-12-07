
package org.mage.test.cards.modal;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ModalTriggeredAbilityTest extends CardTestPlayerBase {

    @Test
    public void testBlizzardSpecterReturn() {
        // Flying
        // Whenever Blizzard Specter deals combat damage to a player, choose one
        // - That player returns a permanent they control to its owner's hand;
        // or that player discards a card.
        addCard(Zone.BATTLEFIELD, playerB, "Blizzard Specter");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Pillarfield Ox");

        attack(2, playerB, "Blizzard Specter");
        setModeChoice(playerB, "1");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Silvercoat Lion", 1);
        assertHandCount(playerA, "Pillarfield Ox", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }

    @Test
    public void testBlizzardSpecterDiscard() {
        // Flying
        // Whenever Blizzard Specter deals combat damage to a player, choose one
        // - That player returns a permanent they control to its owner's hand;
        // or that player discards a card.
        addCard(Zone.BATTLEFIELD, playerB, "Blizzard Specter");

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion");
        addCard(Zone.HAND, playerA, "Pillarfield Ox");

        attack(2, playerB, "Blizzard Specter");
        setModeChoice(playerB, "2");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertHandCount(playerA, "Silvercoat Lion", 0);
        assertHandCount(playerA, "Pillarfield Ox", 0);

        assertGraveyardCount(playerA, "Pillarfield Ox", 1);

        assertLife(playerA, 18);
        assertLife(playerB, 20);

    }

}
