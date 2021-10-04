package org.mage.test.cards.mana.conditional;

import mage.abilities.mana.ManaOptions;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;
import static org.mage.test.utils.ManaOptionsTestUtils.assertManaOptions;

/**
 *
 * @author LevelX2
 */

public class CrypticTrilobiteTest extends CardTestPlayerBase {

    @Test
    public void testAvailableManaCalculation(){
        setStrictChooseMode(true);
        
        // Cryptic Trilobite enters the battlefield with X +1/+1 counters on it.
        // Remove a +1/+1 counter from Cryptic Trilobite: Add {C}{C}. Spend this mana only to activate abilities.        
        // {1}, {T}: Put a +1/+1 counter on Cryptic Trilobite.
        addCard(Zone.HAND, playerA, "Cryptic Trilobite"); // Creature {X}{X}
        
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Trilobite");                
        setChoice(playerA, "X=5");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Cryptic Trilobite", 1);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{C}{C}{C}{C}{C}{C}[{CrypticTrilobiteManaCondition}]", manaOptions);        
    }
    
    @Test
    public void testUse(){
        setStrictChooseMode(true);
        
        // Cryptic Trilobite enters the battlefield with X +1/+1 counters on it.
        // Remove a +1/+1 counter from Cryptic Trilobite: Add {C}{C}. Spend this mana only to activate abilities.        
        // {1}, {T}: Put a +1/+1 counter on Cryptic Trilobite.
        addCard(Zone.HAND, playerA, "Cryptic Trilobite"); // Creature {X}{X}        
        // Flying
        // {2}: Deathknell Kami gets +1/+1 until end of turn. Sacrifice it at the beginning of the next end step.
        // Soulshift 1 (When this creature dies, you may return target Spirit card with converted mana cost 1 or less from your graveyard to your hand.)            
        addCard(Zone.BATTLEFIELD, playerA, "Deathknell Kami"); // Creature   (0/1)     
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Trilobite");
        setChoice(playerA, "X=5");
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}:");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}:");
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Cryptic Trilobite", 1);
        assertCounterCount(playerA, "Cryptic Trilobite", CounterType.P1P1, 3);
        
        assertPowerToughness(playerA, "Deathknell Kami", 2, 3);
        
        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}{C}{C}[{CrypticTrilobiteManaCondition}]", manaOptions);        
    }    
    
    @Test
    public void testCantUse(){
        setStrictChooseMode(true);
        
        // Cryptic Trilobite enters the battlefield with X +1/+1 counters on it.
        // Remove a +1/+1 counter from Cryptic Trilobite: Add {C}{C}. Spend this mana only to activate abilities.        
        // {1}, {T}: Put a +1/+1 counter on Cryptic Trilobite.
        addCard(Zone.HAND, playerA, "Cryptic Trilobite"); // Creature {X}{X}        
        
        // {4}{W}: Return another target creature you control to its owner's hand.
        addCard(Zone.HAND, playerA, "Aegis Automaton"); // Creature {2}  (0/2)     
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);
        
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cryptic Trilobite");
        setChoice(playerA, "X=5");
        
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        
        checkPlayableAbility("can't play", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Aegis Automaton", false);
        
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Cryptic Trilobite", 1);
        assertCounterCount(playerA, "Cryptic Trilobite", CounterType.P1P1, 5);                        
    }    
        
    
}