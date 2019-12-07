
package org.mage.test.cards.triggers.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class ChandrasPhoenixTest extends CardTestPlayerBase {

    @Test
    public void testReturnByInstantSpell() {
        // Flying
        // Haste (This creature can attack and as soon as it comes under your control.)
        // Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return Chandra's Phoenix from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Chandra's Phoenix", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertLife(playerA, 20);
        assertLife(playerB, 17);

        assertHandCount(playerA, "Chandra's Phoenix", 1);

    }

    @Test
    public void testReturnByPlaneswalkerDamage() {
        // Flying
        // Haste (This creature can attack and as soon as it comes under your control.)
        // Whenever an opponent is dealt damage by a red instant or sorcery spell you control or by a red planeswalker you control, return Chandra's Phoenix from your graveyard to your hand.
        addCard(Zone.GRAVEYARD, playerA, "Chandra's Phoenix", 1);

        // +1: Chandra Nalaar deals 1 damage to target player.
        // -X: Chandra Nalaar deals X damage to target creature.
        // -8: Chandra Nalaar deals 10 damage to target player and each creature they control.
        addCard(Zone.BATTLEFIELD, playerA, "Chandra Nalaar", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+1", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 19);

        assertHandCount(playerA, "Chandra's Phoenix", 1);

    }

}
