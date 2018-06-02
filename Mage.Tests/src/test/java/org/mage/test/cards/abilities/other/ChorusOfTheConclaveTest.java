

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
public class ChorusOfTheConclaveTest extends CardTestPlayerBase {

    /** 
     * Chorus of the Conclave
     * 4GGWW
     * Legendary Creature -- Dryad
     * 3/8
     * Forestwalk
     * As an additional cost to cast creature spells, you may pay any amount of 
     * mana. If you do, that creature enters the battlefield with that many 
     * additional +1/+1 counters on it.
     *
     */

    @Test
    public void testPlayCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Chorus of the Conclave");
        addCard(Zone.HAND, playerA, "Goblin Roughrider");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Roughrider");
        setChoice(playerA, "Yes");
        setChoice(playerA, "X=1");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Goblin Roughrider", 1);
        assertCounterCount("Goblin Roughrider", CounterType.P1P1, 1);

    }

}
