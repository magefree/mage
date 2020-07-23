
package org.mage.test.cards.single.usd;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class UrzasIncubatorTest extends CardTestPlayerBase {

    /*
     * Reported bug: Urza's Incubator does not reduce the cost of Eldrazi creatures
    */
    @Test
    public void testEldraziCostReduction() {
  
        /*        
        Urza's Incubator (3) Artifact
            As Urza's Incubator enters the battlefield, choose a creature type.
            Creature spells of the chosen type cost 2 less to cast.
        */
        addCard(Zone.HAND, playerA, "Urza's Incubator", 1);
        addCard(Zone.HAND, playerA, "Eldrazi Displacer", 1); // {2}{W} eldrazi 3/3
        addCard(Zone.HAND, playerA, "Eldrazi Mimic", 2); // {2} eldrazi 2/1
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Incubator"); // taps 3 plains
        setChoice(playerA, "Eldrazi");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Eldrazi Displacer"); // taps last plains
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Eldrazi Mimic"); // both mimics should be free
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Eldrazi Mimic");
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Urza's Incubator", 1);
        assertPermanentCount(playerA, "Eldrazi Displacer", 1);
        assertPermanentCount(playerA, "Eldrazi Mimic", 2);
    }
    
    /*
     * Test to make sure incubator only reduces generic cost. Cards with <> requirement
     * still require specific colorless mana to cast.
    */
    @Test
    public void testEldraziCostReductionWastesRequirement() {
  
        /*        
        Urza's Incubator (3) Artifact
            As Urza's Incubator enters the battlefield, choose a creature type.
            Creature spells of the chosen type cost 2 less to cast.
        */
        addCard(Zone.HAND, playerA, "Urza's Incubator", 1);
        addCard(Zone.HAND, playerA, "Thought-Knot Seer", 1); // {3}{<>} eldrazi 4/4
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Urza's Incubator"); // taps 3 plains
        setChoice(playerA, "Eldrazi");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Thought-Knot Seer"); // 2 plains remaining, but <> required
        setStopAt(1, PhaseStep.END_TURN);
        execute();
        
        assertPermanentCount(playerA, "Urza's Incubator", 1);
        assertPermanentCount(playerA, "Thought-Knot Seer", 0); // should not be able to cast
    }
}
