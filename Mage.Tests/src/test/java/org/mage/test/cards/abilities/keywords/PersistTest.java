/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mage.test.cards.abilities.keywords;

import junit.framework.Assert;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *     702.78. Persist
 *       702.78a Persist is a triggered ability. "Persist" means "When this permanent is put into a graveyard
 *       from the battlefield, if it had no -1/-1 counters on it, return it to the battlefield under its
 *       owner's control with a -1/-1 counter on it."
 *
 * @author LevelX2
 */

public class PersistTest extends CardTestPlayerBase {

    /**
     * Tests Safehold Elite don't returns from Persist if already a -1/-1 counter
     * was put on it from another source
     *
     */
    @Test
    public void testUndyingdoesntTriggerWithMinusCounter() {
        
        // Safehold Elite 2/2   {1}{G/W}
        // Creature - Elf Scout
        // 
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite");

        // Put a -1/-1 counter on target creature. When that creature dies this turn, its controller gets a poison counter.
        addCard(Zone.HAND, playerB, "Virulent Wound",1);
        addCard(Zone.HAND, playerB, "Lightning Bolt",1);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Virulent Wound", "Safehold Elite");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Safehold Elite", 0);
        assertGraveyardCount(playerA, "Safehold Elite", 1);

        // one poison counter from Virulent Wound
        Assert.assertEquals(1, playerA.getCounters().getCount(CounterType.POISON));
    }


    /**
     * If a card with persist is removed from a graveyard before the persist ability resolves, persist will do nothing.
     */
    @Test
    public void testWontTriggerIfPersistCardIsRemovedFromGraveyard() {

        // Safehold Elite 2/2   {1}{G/W}
        // Creature - Elf Scout
        //
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite");

        // Exile target card from a graveyard. You gain 3 life.
        addCard(Zone.HAND, playerB, "Lightning Bolt",1);
        addCard(Zone.HAND, playerB, "Shadowfeed",1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shadowfeed", "Safehold Elite","Persist <i>(When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)</i>");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 23);

        assertPermanentCount(playerA, "Safehold Elite", 0);
        assertGraveyardCount(playerA, "Safehold Elite", 0);

    }


    /**
     * see here for more information
     * http://www.slightlymagic.net/forum/viewtopic.php?f=116&t=14516
     * 
     * Tests Safehold Elite with persist returns to battlefield with -1/-1 counter
     * Murder Investigation puts 2 tokens onto battlefield because enchanted Safehold Elite
     * was 2/2
     *
     */
    @Test
    public void testUndyingTriggersInTime() {
        // Safehold Elite {1}{G/W}
        // Creature - Elf Scout
        // 2/2
        // Persist

        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Murder Investigation",2);

        addCard(Zone.HAND, playerB, "Lightning Bolt",2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murder Investigation", "Safehold Elite");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");
        // choose triggered ability order
        playerA.addChoice("When enchanted creature dies, put X 1/1 red and white Soldier creature token with haste onto the battlefield, where X is its power.");
        //castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite", "When enchanted creature dies, put X 1/1 red and white Soldier creature token with haste onto the battlefield, where X is its power");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Safehold Elite", 1);
        assertPowerToughness(playerA, "Safehold Elite", 1, 1);
        // because enchanted Safehold Elite's P/T was 2/2, Murder Investigation has to put 2 Soldier onto the battlefield
      //  assertPermanentCount(playerA, "Soldier", 2);

    }

}
