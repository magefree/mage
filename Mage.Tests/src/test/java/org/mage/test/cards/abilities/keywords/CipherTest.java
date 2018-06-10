
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CipherTest extends CardTestPlayerBase {

    /**
     * Produced a copy of the opponents Roil Elemental with Stolen Identity and
     * used Cipher on that same token. The token's landfall ability then did
     * trigger normally up to the point where a target creature could be
     * selected. The selection was logged by XMage, but the effect simply did
     * not work. The original Roil Elemental controlled by the other player
     * worked as intended, though.
     *
     * Edit: Opponent was AI, if that helps.
     */
    @Test
    public void testStolenIdentity() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // Create a token that's a copy of target artifact or creature.
        // Cipher  (Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        addCard(Zone.HAND, playerA, "Stolen Identity"); // Sorcery {4}{U}{U}

        // Flying
        // Landfall - Whenever a land enters the battlefield under your control, you may gain control of target creature for as long as you control Roil Elemental.
        addCard(Zone.BATTLEFIELD, playerB, "Roil Elemental");
        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stolen Identity", "Roil Elemental");
        setChoice(playerA, "Yes");

        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        addTarget(playerA, "Silvercoat Lion"); // Triggered ability of copied Roil Elemental to gain control

        attack(3, playerA, "Roil Elemental"); // Creature 3/2
        addTarget(playerA, "Pillarfield Ox");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17);

        assertExileCount(playerA, "Stolen Identity", 1);

        assertPermanentCount(playerA, "Mountain", 1);

        assertPermanentCount(playerB, "Pillarfield Ox", 1);
        assertPermanentCount(playerA, "Pillarfield Ox", 1); // a copy from the cipered Stolen Identity caused by the Roil Elelemtal Attack

        assertPermanentCount(playerB, "Silvercoat Lion", 0);
        assertPermanentCount(playerA, "Silvercoat Lion", 1); // Gain control from triggered ability of the copied Roil Elemental ????? TARGET ???

        assertPermanentCount(playerB, "Roil Elemental", 1);
        assertPermanentCount(playerA, "Roil Elemental", 1);

    }
}
