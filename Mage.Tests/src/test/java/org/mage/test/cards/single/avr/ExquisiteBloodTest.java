package org.mage.test.cards.single.avr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class ExquisiteBloodTest extends CardTestPlayerBase {

    @Test
    public void BasicCardTest() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        // Whenever an opponent loses life, you gain that much life.
        addCard(Zone.BATTLEFIELD, playerA, "Exquisite Blood", 1); // Enchantment {4}{B}

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerA, "Bump in the Night");
        addCard(Zone.BATTLEFIELD, playerA, "Raging Goblin", 2);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Shock");


        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bump in the Night", playerB);

        attack(1, playerA, "Raging Goblin");
        attack(1, playerA, "Raging Goblin");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Shock", playerA);

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 12);
        assertLife(playerA, 26);
    }

    /**
     *  Ajani, Inspiring leader does not trigger Exquisite Blood + Defiant Bloodlord #6464
     */
    @Test
    public void triggerCascadeTest() {
        // +2: You gain 2 life. Put two +1/+1 counters on up to one target creature.
        // −3: Exile target creature. Its controller gains 2 life.
        // −10: Creatures you control gain flying and double strike until end of turn.        
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Inspiring Leader", 1); // Planeswalker (5)        

        // Flying
        // Whenever you gain life, target opponent loses that much life.
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Bloodlord", 1); // Creature 4/5 {5}{B}{B}        
        
        // Whenever an opponent loses life, you gain that much life.
        addCard(Zone.BATTLEFIELD, playerA, "Exquisite Blood", 1); // Enchantment {4}{B}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "+2:", "Defiant Bloodlord");
        addTarget(playerA, playerB);  // Target opponent of Defiant Bloodlord triggered ability (looping until opponent is dead)
        addTarget(playerA, playerB);  
        addTarget(playerA, playerB);  
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Defiant Bloodlord", 6, 7);
        assertCounterCount("Ajani, Inspiring Leader", CounterType.LOYALTY, 7);

        assertLife(playerB, 0); // Player B is dead, game ends
        assertLife(playerA, 40);
        
        
    }

    /**
     *  Ajani, Inspiring leader does not trigger Exquisite Blood + Defiant Bloodlord #6464
     */
    @Test
    public void triggerCascadeAjaniSecondAbilityTest() {
        // +2: You gain 2 life. Put two +1/+1 counters on up to one target creature.
        // −3: Exile target creature. Its controller gains 2 life.
        // −10: Creatures you control gain flying and double strike until end of turn.        
        addCard(Zone.BATTLEFIELD, playerA, "Ajani, Inspiring Leader", 1); // Planeswalker (5)        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1); // Creature 2/2

        // Flying
        // Whenever you gain life, target opponent loses that much life.
        addCard(Zone.BATTLEFIELD, playerA, "Defiant Bloodlord", 1); // Creature 4/5 {5}{B}{B}        
        
        // Whenever an opponent loses life, you gain that much life.
        addCard(Zone.BATTLEFIELD, playerA, "Exquisite Blood", 1); // Enchantment {4}{B}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "-3:", "Silvercoat Lion");
        addTarget(playerA, playerB);  // Target opponent of Defiant Bloodlord triggered ability (looping until opponent is dead)
        addTarget(playerA, playerB);  
        addTarget(playerA, playerB);  
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        addTarget(playerA, playerB);
        
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Defiant Bloodlord", 4, 5);
        assertCounterCount("Ajani, Inspiring Leader", CounterType.LOYALTY, 2);

        assertLife(playerB, 0); // Player B is dead, game ends
        assertLife(playerA, 40);
        
        
    }
}
