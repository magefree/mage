
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class TargetedTriggeredTest extends CardTestPlayerBase {

    /**
     * Tests that the first spell that targets Kira, Great Glass-Spinner is
     * countered.
     *
     */
    @Test
    // this does not currently work in test (????), because the target event will be fired earlier during tests,
    // so the zone change counter for the fixed target of the counterspell will not work
    // UPDATE: seems to work fine now? 04/19/2017 escplan9
    public void testKiraGreatGlassSpinnerFirstSpellTurn() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerB, "Kira, Great Glass-Spinner", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Kira, Great Glass-Spinner");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);

        assertPermanentCount(playerB, "Kira, Great Glass-Spinner", 1);
    }

    /**
     * With Ashenmoor Liege on the battlefield, my opponent casts Claustrophobia
     * on it without losing 4hp.
     */
    @Test
    public void testAshenmoorLiege() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, "Claustrophobia"); // {1}{U}{U}

        // Other black creatures you control get +1/+1.
        // Other red creatures you control get +1/+1.
        // Whenever Ashenmoor Liege becomes the target of a spell or ability an opponent controls, that player loses 4 life.
        addCard(Zone.BATTLEFIELD, playerB, "Ashenmoor Liege", 1);  // 4/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Claustrophobia", "Ashenmoor Liege");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 16);

        assertPermanentCount(playerA, "Claustrophobia", 1);
        assertPowerToughness(playerB, "Ashenmoor Liege", 4, 1);
    }

    @Test
    public void testGlyphKeeperCountersFirstSpell() {
        
        /*
        Glyph Keeper {3}{U}{U}
        Creature - Sphinx
        Flying 5/3
        Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability." 
        */
        String gKeeper = "Glyph Keeper";
        String bolt = "Lightning Bolt"; // {R} instant deal 3 dmg
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, bolt);

        addCard(Zone.BATTLEFIELD, playerB, gKeeper);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gKeeper);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, bolt, 1);
        assertPermanentCount(playerB, gKeeper, 1);
    }
    
    @Test
    public void testGlyphKeeperCountersFirstSpellButNotSecondSpell() {
        
        /*
        Glyph Keeper {3}{U}{U}
        Creature - Sphinx
        Flying 5/3
        Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability." 
        */
        String gKeeper = "Glyph Keeper";
        String bolt = "Lightning Bolt"; // {R} instant deal 3 dmg
        
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, bolt, 2);

        addCard(Zone.BATTLEFIELD, playerB, gKeeper);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, bolt, gKeeper);
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, bolt, gKeeper);

        setStopAt(1, PhaseStep.DECLARE_ATTACKERS);
        execute();

        assertGraveyardCount(playerA, bolt, 2);
        assertPermanentCount(playerB, gKeeper, 0);
    }
    
    /*
    NOTE: test was failing due to card bug, resolved as of 04/20/2017. See issue #3180
    I had a Glyph Keeper on board (cloned with Vizier of many faces). -- note this test is a simplified version, next test will test on the Clone if needed
    First I played a Soulstinger and targeted the Glyph Keeper, the ability was countered. Then on the same main phase I played a Cartouche of Strength targeting the Glyph Keeper, that was also countered. 
    Only the first should have been countered.
    */
    @Test
    public void testGlyphKeeperCountersFirstAbilityButNotSecondOne() {
        
        /*
        Glyph Keeper {3}{U}{U}
        Creature - Sphinx
        Flying 5/3
        Whenever this creature becomes the target of a spell or ability for the first time in a turn, counter that spell or ability." 
        */
        String gKeeper = "Glyph Keeper";
        
        /*
        Soulstinger {3}{B}
        Creature - Scorpion Demon  4/5
        When Soulstinger enters the battlefield, put two -1/-1 counter on target creature you control.
        When Soulstinger dies, you may put a -1/-1 counter on target creature for each -1/-1 counter on Soulstinger. 
        */
        String sStinger = "Soulstinger";
        
        /*
        Cartouche of Strength {2}{G}
        Enchantment - Aura Cartouche
        Enchant creature you control
        When Cartouche of Strength enters the battlefield, you may have enchanted creature fight target creature an opponent controls.
        Enchanted creature gets +1/+1 and has trample. 
        */
        String cStrength = "Cartouche of Strength";
        String memnite = "Memnite"; // {0} 1/1

        addCard(Zone.BATTLEFIELD, playerA, gKeeper);
        addCard(Zone.HAND, playerA, sStinger);
        addCard(Zone.HAND, playerA, cStrength);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerB, memnite);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, sStinger);
        addTarget(playerA, gKeeper); // should be countered by Glyph Keeper clause as first ability targetting it
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, cStrength, gKeeper); // should not be countered anymore
        setChoice(playerA, true);
        addTarget(playerA, memnite); // Cartouche of Strength fight

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, gKeeper, 1);
        assertGraveyardCount(playerA, sStinger, 0); // countered
        assertGraveyardCount(playerA, cStrength, 0); // should not be countered
        assertPermanentCount(playerA, cStrength, 1);
        assertGraveyardCount(playerB, memnite, 1); // dies from fight
        assertPowerToughness(playerA, gKeeper, 6, 4, Filter.ComparisonScope.All); // Soul Stinger should never have given it two -1/-1 counters
        //And Cartouche of Strength gives +1/+1
        assertCounterCount(playerA, gKeeper, CounterType.M1M1, 0);
    }
}
