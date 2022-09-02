package org.mage.test.cards.facedown;

import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class ObscuringAetherTest extends CardTestPlayerBase {

    /**
     * Obscuring Aether cannot turn into a face down 2/2 like it should. When
     * activating the ability to turn it over it, it dies immediately.
     */
    // test that cards exiled using Ghastly Conscription return face down
    @Test
    public void testTurnFaceDown() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        // Face-down creature spells you cast cost {1} less to cast.
        // {1}{G}: Turn Obscuring Aether face down.
        addCard(Zone.HAND, playerA, "Obscuring Aether");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Obscuring Aether", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{G}: Turn");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertHandCount(playerA, "Obscuring Aether", 0);
        assertGraveyardCount(playerA, "Obscuring Aether", 0);

        assertPermanentCount(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 1);
        assertPowerToughness(playerA, EmptyNames.FACE_DOWN_CREATURE.toString(), 2, 2);
    }
}
