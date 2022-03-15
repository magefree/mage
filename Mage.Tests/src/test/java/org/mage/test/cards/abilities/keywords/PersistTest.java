package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.Filter;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 702.78. Persist 702.78a Persist is a triggered ability. "Persist" means "When
 * this permanent is put into a graveyard from the battlefield, if it had no
 * -1/-1 counters on it, return it to the battlefield under its owner's control
 * with a -1/-1 counter on it."
 *
 * @author LevelX2
 */
public class PersistTest extends CardTestPlayerBase {

    /**
     * Tests Safehold Elite don't returns from Persist if already a -1/-1
     * counter was put on it from another source
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
        addCard(Zone.HAND, playerB, "Virulent Wound", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
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
     * If a card with persist is removed from a graveyard before the persist
     * ability resolves, persist will do nothing.
     */
    @Test
    public void testWontTriggerIfPersistCardIsRemovedFromGraveyard() {

        // Safehold Elite 2/2   {1}{G/W}
        // Creature - Elf Scout
        //
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Safehold Elite");

        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);
        // Exile target card from a graveyard. You gain 3 life.
        addCard(Zone.HAND, playerB, "Shadowfeed", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Safehold Elite");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Shadowfeed", "Safehold Elite", "persist");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 23);

        assertPermanentCount(playerA, "Safehold Elite", 0);
        assertGraveyardCount(playerA, "Safehold Elite", 0);

    }

    @Test
    public void testInteractionWithLifelink() {

        // Kitchen Finks 3/2   {1}{G/W}{G/W}
        // Creature - Ouphe
        // When Kitchen Finks enters the battlefield, you gain 2 life.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        //
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Kitchen Finks", 1);

        /**
         * Deathtouch, lifelink When Wurmcoil Engine dies, put a 3/3 colorless
         * Wurm artifact creature token with deathtouch and a 3/3 colorless Wurm
         * artifact creature token with lifelink onto the battlefield.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Wurmcoil Engine", 1);

        attack(2, playerB, "Wurmcoil Engine");
        block(2, playerA, "Kitchen Finks", "Wurmcoil Engine");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Wurmcoil Engine", 1);
        assertPermanentCount(playerA, "Kitchen Finks", 1);
        assertPowerToughness(playerA, "Kitchen Finks", 2, 1);

        assertLife(playerA, 22); // Kitchen Finks +2 life
        assertLife(playerB, 26); // Wurmcoil Engine +6 ife

    }

    @Test
    public void testInteractionWithToporOrb() {

        // Kitchen Finks 3/2   {1}{G/W}{G/W}
        // Creature - Ouphe
        // When Kitchen Finks enters the battlefield, you gain 2 life.
        // Persist (When this creature dies, if it had no -1/-1 counters on it, return it to the battlefield under its owner's control with a -1/-1 counter on it.)
        //
        // Persist
        addCard(Zone.BATTLEFIELD, playerA, "Kitchen Finks", 2);

        /**
         * Deathtouch, lifelink When Wurmcoil Engine dies, put a 3/3 colorless
         * Wurm artifact creature token with deathtouch and a 3/3 colorless Wurm
         * artifact creature token with lifelink onto the battlefield.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Wurmcoil Engine", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Torpor Orb", 1);

        attack(2, playerB, "Wurmcoil Engine");
        block(2, playerA, "Kitchen Finks:0", "Wurmcoil Engine");
        block(2, playerA, "Kitchen Finks:1", "Wurmcoil Engine");

        setChoice(playerB, "Creatures entering the battlefield don't cause abilities to trigger");
        setChoice(playerB, "Creatures entering the battlefield don't cause abilities to trigger");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20); // No life from Kitchen Finks ETB becaus of Torpor Orb
        assertLife(playerB, 26); // +6 from lifelink of Wurmcoil

        assertPermanentCount(playerB, "Wurmcoil Engine", 0);
        assertPermanentCount(playerB, "Phyrexian Wurm Token", 2);
        assertPermanentCount(playerA, "Kitchen Finks", 2);
        assertPowerToughness(playerA, "Kitchen Finks", 2, 1, Filter.ComparisonScope.All);

    }

    /**
     * Situation: Clever Impersonator is copying ". Opponent casts Supreme
     * Verdict. Persist on the clone of Glen Elendra Archmage triggers and goes
     * on the stack, and I am asked to put triggers on the stack. Problem: No
     * options pop up. I tried pressing many buttons, but the game was
     * deadlocked.
     */
    @Test
    public void testCopiedCreatureWithPersists() {

        // Flying
        // {U}, Sacrifice Glen Elendra Archmage: Counter target noncreature spell.
        // Persist
        addCard(Zone.BATTLEFIELD, playerB, "Glen Elendra Archmage", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        // Supreme Verdict can't be countered.
        // Destroy all creatures.
        addCard(Zone.HAND, playerB, "Supreme Verdict", 1); // {1}{W}{W}{U}

        // You may have Clever Impersonator enter the battlefield as a copy of any nonland permanent on the battlefield.
        addCard(Zone.HAND, playerA, "Clever Impersonator", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clever Impersonator");
        setChoice(playerA, "Glen Elendra Archmage");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Supreme Verdict");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerB, "Glen Elendra Archmage", 1);
        assertPowerToughness(playerB, "Glen Elendra Archmage", 1, 1);
        assertGraveyardCount(playerA, "Clever Impersonator", 1);
    }

    @Test
    public void testMeliraSylvokOutcast() {

        // You can't get poison counters.
        // Creatures you control can't have -1/-1 counters placed on them.
        // Creatures your opponents control lose infect.
        addCard(Zone.BATTLEFIELD, playerA, "Melira, Sylvok Outcast", 1); // 2/2
        // When Murderous Redcap enters the battlefield, it deals damage equal to its power to any target.
        // Persist
        addCard(Zone.HAND, playerA, "Murderous Redcap", 1); // 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Murderous Redcap");
        addTarget(playerA, "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Murderous Redcap");
        addTarget(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 2);
        assertPowerToughness(playerA, "Murderous Redcap", 2, 2); // Got no -1/-1 after returning because of Melira in play
    }
}
