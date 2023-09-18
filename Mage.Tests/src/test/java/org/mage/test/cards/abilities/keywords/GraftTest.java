

package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class GraftTest extends CardTestPlayerBase {

    /**
     * 702.57. Graft
     * 702.57a Graft represents both a static ability and a triggered ability. “Graft N” means “This
     * permanent enters the battlefield with N +1/+1 counters on it” and “Whenever another creature
     * enters the battlefield, if this permanent has a +1/+1 counter on it, you may move a +1/+1
     * counter from this permanent onto that creature.”
     * 702.57b If a creature has multiple instances of graft, each one works separately.
     * 
     */

    /** 
     * 	Sporeback Troll
     * 	Creature — Troll Mutant 0/0, 3G (4)
     * 	Graft 2 (This creature enters the battlefield with two +1/+1 counters on it. 
     *  Whenever another creature enters the battlefield, you may move a +1/+1 
     *  counter from this creature onto it.)
     * 	{1}{G}: Regenerate target creature with a +1/+1 counter on it.
     *
     */

    /**
     *  Cytoplast Root-Kin
     *  Creature — Elemental Mutant 0/0, 2GG (4)
     *  Graft 4 (This creature enters the battlefield with four +1/+1 counters on 
     *  it. Whenever another creature enters the battlefield, you may move a +1/+1 
     *  counter from this creature onto it.)
     *  When Cytoplast Root-Kin enters the battlefield, put a +1/+1 counter on 
     *  each other creature you control with a +1/+1 counter on it.
     *  {2}: Move a +1/+1 counter from target creature you control onto Cytoplast Root-Kin.
     * 
     */

    @Test
    public void testGraft() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);
        addCard(Zone.HAND, playerA, "Sporeback Troll");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Sporeback Troll");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Sporeback Troll", 1);
        assertPowerToughness(playerA, "Sporeback Troll", 2, 2);
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 2);

    }

    @Test
    public void testMoveCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.HAND, playerA, "Cytoplast Root-Kin");
        addCard(Zone.HAND, playerA, "Sporeback Troll");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cytoplast Root-Kin");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sporeback Troll");
        setChoice(playerA, true);
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sporeback Troll", 1);
        assertPermanentCount(playerA, "Cytoplast Root-Kin", 1);
        assertPowerToughness(playerA, "Sporeback Troll", 3, 3);
        assertPowerToughness(playerA, "Cytoplast Root-Kin", 3, 3);
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 3);
        assertCounterCount("Cytoplast Root-Kin", CounterType.P1P1, 3);
    }
    
    @Test
    public void testDontMoveCounters() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 8);
        addCard(Zone.HAND, playerA, "Cytoplast Root-Kin");
        addCard(Zone.HAND, playerA, "Sporeback Troll");
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cytoplast Root-Kin");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sporeback Troll");
        setChoice(playerA, false);
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Sporeback Troll", 1);
        assertPermanentCount(playerA, "Cytoplast Root-Kin", 1);
        assertPowerToughness(playerA, "Sporeback Troll", 2, 2);
        assertPowerToughness(playerA, "Cytoplast Root-Kin", 4, 4);
        assertCounterCount("Sporeback Troll", CounterType.P1P1, 2);
        assertCounterCount("Cytoplast Root-Kin", CounterType.P1P1, 4);
    }   
    
}
