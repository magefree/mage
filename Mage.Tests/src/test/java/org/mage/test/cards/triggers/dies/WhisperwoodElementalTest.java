package org.mage.test.cards.triggers.dies;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Whisperwood Elemental - Elemental {3}{G}{G} At the beginning of your end
 * step, manifest the top card of your library. Sacrifice Whisperwood Elemental:
 * Until end of turn, face-up, nontoken creatures you control gain "When this
 * creature dies, manifest the top card of your library."
 *
 * @author LevelX2
 */
public class WhisperwoodElementalTest extends CardTestPlayerBase {

    /**
     * Tests that the dies triggered ability of silvercoat lion (gained by
     * sacrificed Whisperwood Elemental) triggers as he dies from Lightning Bolt
     */
    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Whisperwood Elemental", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sacrifice {this}: Until end of turn, face-up, nontoken creatures you control gain \"When this creature dies, manifest the top card of your library.");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertGraveyardCount(playerA, "Whisperwood Elemental", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        // Manifested creature from dying Silvercoat Lion
        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
    }

}
