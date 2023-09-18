
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

        // cast spell, create copy token, exile spell card and encode it to that token of Roil Elemental
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stolen Identity", "Roil Elemental");
        setChoice(playerA, true); // Cipher activate
        addTarget(playerA, "Roil Elemental"); // Cipher target for encode
        checkPermanentCount("playerA must have Roil Elemental", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Roil Elemental", 1);
        checkPermanentCount("playerB must have Roil Elemental", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Roil Elemental", 1);
        checkExileCount("Stolen Identity must be in exile zone", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Stolen Identity", 1);

        // Roil Elemental must activated on new land
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");
        setChoice(playerA, true); // activate landfall to control opponent creature
        addTarget(playerA, "Silvercoat Lion"); // Triggered ability of copied Roil Elemental to gain control
        checkPermanentCount("must gain control of Lion", 3, PhaseStep.POSTCOMBAT_MAIN, playerA, "Silvercoat Lion", 1);
        checkPermanentCount("must lose control of Lion", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion", 0);

        // on attack must activated ability to free cast
        attack(5, playerA, "Roil Elemental");
        setChoice(playerA, true); // activate free cast of encoded card
        checkPermanentCount("playerA must have 2 Roil Elemental", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, "Roil Elemental", 2);
        checkPermanentCount("playerB must have Roil Elemental", 5, PhaseStep.POSTCOMBAT_MAIN, playerB, "Roil Elemental", 1);

        setStopAt(5, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 17); // -3 by Roil
    }
}