

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class ChiefEngineerTest extends CardTestPlayerBase {

    /** 
     * Chief Engineer
     * Creature — Vedalken Artificer 1/3, 1U (2)
     * Artifact spells you cast have convoke. (Your creatures can help cast 
     * those spells. Each creature you tap while casting an artifact spell pays 
     * for {1} or one mana of that creature's color.)
     *
     */

    @Ignore // at this time player.getPlayable() does not take into account convoke payments
    @Test
    public void testGainsConvoke() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Chief Engineer");
        addCard(Zone.BATTLEFIELD, playerA, "Alpha Myr");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Roughrider");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertTapped("Alpha Myr", true);

    }
   
}
