

package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class VarolzTheScarStripedTest extends CardTestPlayerBase {

    /** 
     * Varolz, the Scar-Striped
     * Legendary Creature — Troll Warrior 2/2, 1BG (3)
     * Each creature card in your graveyard has scavenge. The scavenge cost is 
     * equal to its mana cost. (Exile a creature card from your graveyard and 
     * pay its mana cost: Put a number of +1/+1 counters equal to that card's 
     * power on target creature. Scavenge only as a sorcery.)
     * Sacrifice another creature: Regenerate Varolz, the Scar-Striped.
     *
     */

    @Test
    public void testUseScavenge() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Varolz, the Scar-Striped");
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Roughrider");
        addCard(Zone.GRAVEYARD, playerA, "Goblin Roughrider");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Scavenge", "Goblin Roughrider");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertGraveyardCount(playerA, "Goblin Roughrider", 0);
        assertExileCount("Goblin Roughrider", 1);
        assertCounterCount("Goblin Roughrider", CounterType.P1P1, 3);

    }

}
