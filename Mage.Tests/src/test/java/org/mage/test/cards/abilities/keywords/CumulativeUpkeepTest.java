
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class CumulativeUpkeepTest extends CardTestPlayerBase {

    @Test
    public void basicTest() {
        setStrictChooseMode(true);
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        // Flying; fear
        // Cumulative upkeep {B}
        addCard(Zone.HAND, playerA, "Phobian Phantasm"); // Creature {1}{B}{B} 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Phobian Phantasm");

        // Phobian Phantasm - CumulativeUpkeepAbility: Cumulative upkeep {B}
        setChoice(playerA, true); // Pay {B}?
        attack(3, playerA, "Phobian Phantasm");
        checkPermanentCounters("Age counters", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Phobian Phantasm", CounterType.AGE, 1);
        
        setChoice(playerA, true); // Pay {B}{B}?
        attack(5, playerA, "Phobian Phantasm");
        checkPermanentCounters("Age counters", 5, PhaseStep.PRECOMBAT_MAIN, playerA, "Phobian Phantasm", CounterType.AGE, 2);
        
        setChoice(playerA, false); // Pay {B}{B}{B}?
        
        setStopAt(7, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Phobian Phantasm", 1);
        
        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }


    /**
     I changed control of a Illusions of Grandeur to an AI after cumulative upkeep had triggered but before it resolved.
     I chose not to pay the upkeep cost and then either the AI sacrificed it or I sacrificed it, neither of which should happen.
     I can't sacrifice it because it's not under my control. The AI can't sacrifice it because they are not instructed to do so.

     Here is the reminder text for cumulative upkeep:
     At the beginning of your upkeep, put an age counter on this permanent, then sacrifice it unless you pay its upkeep cost for each age counter on it.
     */
    @Test
    public void controlChangeTest() {
        setStrictChooseMode(true);
        
        // Whenever Kor Celebrant or another creature enters the battlefield under your control, you gain 1 life.
        addCard(Zone.HAND, playerB, "Kor Celebrant", 1); // Creature {2}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        // Cumulative upkeep {2}
        // When Illusions of Grandeur enters the battlefield, you gain 20 life.
        // When Illusions of Grandeur leaves the battlefield, you lose 20 life.
        addCard(Zone.HAND, playerA, "Illusions of Grandeur"); // Enchantment {3}{U}
        
        // At the beginning of your upkeep, you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser converted mana cost.        
        addCard(Zone.HAND, playerA, "Puca's Mischief"); // Enchantment {3}{U}

        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Illusions of Grandeur");
             
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Kor Celebrant");
        
        // Illusions of Grandeur - CumulativeUpkeepAbility: Cumulative upkeep {2}
        setChoice(playerA, true); // Pay {2}?
        
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Puca's Mischief");
        
        setChoice(playerA, "Cumulative upkeep"); // Triggered list (total 2) which trigger goes first on the stack
        addTarget(playerA, "Illusions of Grandeur"); // Own target permanent of Puca's Mischief
        addTarget(playerA, "Kor Celebrant"); // Opponent's target permanent of Puca's Mischief
        
        setChoice(playerA, true); // At the beginning of your upkeep, you may exchange control of target nonland permanent you control and target nonland permanent an opponent controls with an equal or lesser converted mana cost.
        setChoice(playerA, false); // Pay {2}{2}?
        
        checkPermanentCounters("Age counters", 5, PhaseStep.PRECOMBAT_MAIN, playerB, "Illusions of Grandeur", CounterType.AGE, 2);
        
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 40);
        assertLife(playerB, 21);  
        
        assertPermanentCount(playerA, "Kor Celebrant", 1);
        assertPermanentCount(playerB, "Illusions of Grandeur", 1);
        

    }

        
}