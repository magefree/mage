
package org.mage.test.cards.abilities.oneshot.destroy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class BaneOfProgessTest extends CardTestPlayerBase {

    @Test
    public void testDestroy() {
        // You may play land cards from your graveyard.
        addCard(Zone.BATTLEFIELD, playerA, "Crucible of Worlds");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Create a tokenonto the battlefield that's a copy of target artifact or creature.
        // Cipher (Then you may exile this spell card encoded on a creature you control. Whenever that creature deals combat damage to a player, its controller may cast a copy of the encoded card without paying its mana cost.)
        addCard(Zone.HAND, playerA, "Stolen Identity"); // {4}{U}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 6);
        // When Bane of Progress enters the battlefield, destroy all artifacts and enchantments. Put a +1/+1 counter on Bane of Progress for each permanent destroyed this way.
        addCard(Zone.HAND, playerB, "Bane of Progress");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stolen Identity", "Crucible of Worlds");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bane of Progress");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Stolen Identity", 1);
        assertPermanentCount(playerA, "Crucible of Worlds", 0);

        assertPermanentCount(playerB, "Bane of Progress", 1);
        assertPowerToughness(playerB, "Bane of Progress", 4, 4);
    }
}
