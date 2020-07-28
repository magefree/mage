package org.mage.test.cards.mana;

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

public class NonTappingManaAbilitiesTest extends CardTestPlayerBase {

    @Test
    public void druidsRepositoryTest() {
        setStrictChooseMode(true);
        
        addCard(Zone.HAND, playerA, "Alaborn Grenadier", 1); //Creature {W}{W}
        
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 2);
        // Whenever a creature you control attacks, put a charge counter on Druids' Repository.
        // Remove a charge counter from Druids' Repository: Add one mana of any color.        
        addCard(Zone.BATTLEFIELD, playerA, "Druids' Repository", 1); // Enchantment {1}{G}{G}

        attack(1, playerA, "Silvercoat Lion");
        attack(1, playerA, "Silvercoat Lion");
        setChoice(playerA, "Whenever a creature you control");
        
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();
        
        assertTappedCount("Silvercoat Lion", true, 2);        
        assertCounterCount(playerA,"Druids' Repository",  CounterType.CHARGE, 2);

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}{Any}", manaOptions);        
                
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Alaborn Grenadier");
        setChoice(playerA, "White");
        setChoice(playerA, "White");
        
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA,"Druids' Repository",  CounterType.CHARGE, 0);
        assertPermanentCount(playerA, "Alaborn Grenadier", 1);
    }

   @Test
    public void TestWorkhorse() {
        setStrictChooseMode(true);
        
        // Workhorse enters the battlefield with four +1/+1 counters on it.
        // Remove a +1/+1 counter from Workhorse: Add {C}.
        addCard(Zone.BATTLEFIELD, playerA, "Workhorse", 1);
        
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{C}{C}{C}{C}", manaOptions);
    } 

    @Test
    public void TestMorselhoarder() {
        setStrictChooseMode(true);
        // Morselhoarder enters the battlefield with two -1/-1 counters on it.
        // Remove a -1/-1 counter from Morselhoarder: Add one mana of any color.
        addCard(Zone.BATTLEFIELD, playerA, "Morselhoarder", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{B}{B}{Any}{Any}{Any}{Any}", manaOptions);
    }

    @Test
    public void TestFarrelitePriest() {
        setStrictChooseMode(true);
        // {1}: Add {W}. If this ability has been activated four or more times this turn, sacrifice Farrelite Priest at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Farrelite Priest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 5, manaOptions.size());
        assertManaOptions("{W}{W}{W}{W}", manaOptions);
        assertManaOptions("{W}{W}{W}{B}", manaOptions);
        assertManaOptions("{W}{W}{B}{B}", manaOptions);
        assertManaOptions("{W}{B}{B}{B}", manaOptions);
        assertManaOptions("{B}{B}{B}{B}", manaOptions);
    }
    @Test
    public void TestCrystallineCrawler() {
        setStrictChooseMode(true);
        // Converge - Crystalline Crawler enters the battlefield with a +1/+1 counter on it for each color of mana spent to cast it.
        // Remove a +1/+1 counter from Crystalline Crawler: Add one mana of any color.
        // {T}: Put a +1/+1 counter on Crystalline Crawler.
        addCard(Zone.BATTLEFIELD, playerA, "Crystalline Crawler", 1);
        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crystalline Crawler", CounterType.P1P1, 2);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();

        ManaOptions manaOptions = playerA.getAvailableManaTest(currentGame);
        Assert.assertEquals("mana variations don't fit", 1, manaOptions.size());
        assertManaOptions("{Any}{Any}", manaOptions);
    }
    
}