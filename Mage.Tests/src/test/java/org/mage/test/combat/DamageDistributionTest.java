package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author ayrat
 */
public class DamageDistributionTest extends CardTestPlayerBase {

    @Test
    public void testDoubleStrike() {
        addCard(Zone.BATTLEFIELD, playerA, "Warren Instigator");
        addCard(Zone.BATTLEFIELD, playerB, "Merfolk Looter");
        setLife(playerB, 4);

        attack(1, playerA, "Warren Instigator");
        block(1, playerB, "Merfolk Looter", "Warren Instigator");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        Permanent instigator = getPermanent("Warren Instigator", playerA.getId());
        Assert.assertNotEquals(null, instigator);
        Assert.assertEquals("Computer didn't attacked with Warren Instigator", true, instigator.isTapped());

        // should block and die
        assertPermanentCount(playerB, "Merfolk Looter", 0);

        // creature is blocked
        // blocker dies and second strike does nothing
        assertLife(playerB, 4);
    }

    @Test
    public void testDoubleStrikeUnblocked() {
        addCard(Zone.BATTLEFIELD, playerA, "Warren Instigator");
        setLife(playerB, 4);

        attack(1, playerA, "Warren Instigator");
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 2);
    }

    @Test
    public void testNotAttackingVersusDoubleStrike() {
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Looter");
        addCard(Zone.BATTLEFIELD, playerB, "Warren Instigator");
        setLife(playerB, 4);

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // should block and die
        assertPermanentCount(playerA, "Merfolk Looter", 1);
        assertPermanentCount(playerB, "Warren Instigator", 1);

        // creature is blocked
        // blocker dies and second strike does nothing
        assertLife(playerB, 4);
    }

    @Test
    public void testDoubleStrikeTrampleVersusIndestructible() {
        addCard(Zone.BATTLEFIELD, playerA, "Darksteel Gargoyle");
        addCard(Zone.BATTLEFIELD, playerB, "Drogskol Reaver");
        addCard(Zone.BATTLEFIELD, playerB, "Primal Rage");

        attack(2, playerB, "Drogskol Reaver");
        block(2, playerA, "Darksteel Gargoyle", "Drogskol Reaver");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // no creatures dies
        assertPermanentCount(playerA, "Darksteel Gargoyle", 1);
        assertPermanentCount(playerB, "Drogskol Reaver", 1);

        // creature is blocked
        // blocker does not die and second strike can trample through for 3
        assertLife(playerA, 17);
        assertLife(playerB, 26);
    }

    /**
     *
     */
    @Test
    public void testDoubleStrikeTrampleVersusIndestructibleGod() {
        /**
         * Heliod, God of the Sun Legendary Enchantment Creature — God 5/6, 3W
         * Indestructible As long as your devotion to white is less than five,
         * Heliod isn't a creature. (Each {W} in the mana costs of permanents
         * you control counts toward your devotion to white.) Other creatures
         * you control have vigilance. {2}{W}{W}: Create a 2/1 white Cleric
         * enchantment creature token.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Heliod, God of the Sun");
        addCard(Zone.BATTLEFIELD, playerA, "Terra Eternal"); // only for devotion
        addCard(Zone.BATTLEFIELD, playerA, "Terra Eternal"); // only for devotion

        /* Primeval Titan
         * Creature — Giant 6/6, 4GG
         * Trample
         * Whenever Primeval Titan enters the battlefield or attacks, you may search your library for up to two land cards, put them onto the battlefield tapped, then shuffle your library.
         */
        addCard(Zone.BATTLEFIELD, playerB, "Primeval Titan");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        // {W}{W}{2}
        // If Leyline of Sanctity is in your opening hand, you may begin the game with it on the battlefield.
        // You have hexproof.
        addCard(Zone.HAND, playerA, "Leyline of Sanctity", 1);
        // Enchantment - Aura {W}{2}
        // Enchant creature
        // Enchanted creature has double strike.
        addCard(Zone.HAND, playerB, "Battle Mastery");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Battle Mastery", "Primeval Titan");
        attack(2, playerB, "Primeval Titan");
        block(2, playerA, "Heliod, God of the Sun", "Primeval Titan");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // creature is blocked
        // blocker does not die and second strike can trample through for 6
        assertLife(playerA, 14);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Leyline of Sanctity", 1);
        assertPermanentCount(playerB, "Battle Mastery", 1);
        // no creatures dies
        assertPermanentCount(playerA, "Heliod, God of the Sun", 1);
        assertPowerToughness(playerA, "Heliod, God of the Sun", 5, 6);
        assertPermanentCount(playerB, "Primeval Titan", 1);
        assertPowerToughness(playerB, "Primeval Titan", 6, 6);

    }

    /**
     * Damage of one combat phase
     */
    @Test
    public void testCombatDamagePhyrexianUnlife() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        /**
         * Phyrexian Unlife {2}{W} Enchantment
         *
         * You don't lose the game for having 0 or less life. As long as you
         * have 0 or less life, all damage is dealt to you as though its source
         * had infect.
         */
        addCard(Zone.BATTLEFIELD, playerA, "Phyrexian Unlife");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "targetPlayer=PlayerA");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "targetPlayer=PlayerA");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "targetPlayer=PlayerA");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "targetPlayer=PlayerA");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "targetPlayer=PlayerA");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "targetPlayer=PlayerA");

        attack(2, playerB, "Silvercoat Lion");
        attack(2, playerB, "Silvercoat Lion");

        setStopAt(2, PhaseStep.END_TURN);
        execute();

        // No poison counter yet
        assertCounterCount(playerA, CounterType.POISON, 0);
        assertPermanentCount(playerB, "Silvercoat Lion", 2);

        // 4 damage from attack must be dealt as damage (not as poison counter)
        assertLife(playerA, -2);
        assertLife(playerB, 20);
    }

    @Test
    public void testTrampleDeathtouch() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Colossal Dreadmaw");
        addCard(Zone.BATTLEFIELD, playerB, "Colossapede");
        addCard(Zone.HAND, playerA, "Bladebrand");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bladebrand", "Colossal Dreadmaw");

        attack(1, playerA, "Colossal Dreadmaw");
        block(1, playerB, "Colossapede", "Colossal Dreadmaw");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 5);
    }
}
