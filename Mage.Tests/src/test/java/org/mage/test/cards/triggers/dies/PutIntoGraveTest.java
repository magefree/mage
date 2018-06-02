
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class PutIntoGraveTest extends CardTestPlayerBase {

    /**
     * When I go to Nature's Claim my Chromatic Star, I dont end up drawing a
     * card.
     */
    @Test
    public void testDiesTriggeredAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        // Destroy target artifact or enchantment. Its controller gains 4 life.
        addCard(Zone.HAND, playerA, "Nature's Claim", 1);

        // {1}, {T}, Sacrifice Chromatic Star: Add one mana of any color.
        // When Chromatic Star is put into a graveyard from the battlefield, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Chromatic Star", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nature's Claim", "Chromatic Star");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 24);

        assertGraveyardCount(playerA, "Nature's Claim", 1);
        assertGraveyardCount(playerA, "Chromatic Star", 1);

        assertHandCount(playerA, 1);
    }

}
