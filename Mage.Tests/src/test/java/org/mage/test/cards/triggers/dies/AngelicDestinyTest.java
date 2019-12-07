
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 
 * 
 * @author LevelX2
 */

public class AngelicDestinyTest extends CardTestPlayerBase {

    /**
     * I killed my opponent's Champion of the Parish, which was enchanted with Angelic Destiny. 
     * However the Angelic Destiny went to the graveyard instead of returning to their hand.
     * 
     */
    @Test
    public void testDisabledEffectOnChangeZone() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        // Enchantment - Aura    {2}{W}{W}
        // Enchant creature
        // Enchanted creature gets +4/+4, has flying and first strike, and is an Angel in addition to its other types.
        // When enchanted creature dies, return Angelic Destiny to its owner's hand.
        addCard(Zone.HAND, playerA, "Angelic Destiny", 1);
        // Champion of the Parish
        // Whenever another Human enters the battlefield under your control, put a +1/+1 counter on Champion of the Parish.
        addCard(Zone.BATTLEFIELD, playerA, "Champion of the Parish", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Terminate", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Angelic Destiny", "Champion of the Parish");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Terminate", "Champion of the Parish");

        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertGraveyardCount(playerA, "Champion of the Parish", 1);
        assertGraveyardCount(playerB, "Terminate", 1);
        
        assertGraveyardCount(playerA, "Angelic Destiny", 0);
        assertHandCount(playerA, "Angelic Destiny", 1);
        
    }

}