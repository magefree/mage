package org.mage.test.cards.single.m11;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * also tests triggered abilities that switch from one permanent to another
 * 
 * @author BetaSteward
 * 
 */
public class NecroticPlagueTest extends CardTestPlayerBase {

    @Test
    public void testCard1() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.HAND, playerA, "Necrotic Plague");
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Necrotic Plague", "Sejiri Merfolk");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertGraveyardCount(playerA, "Necrotic Plague", 1);
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Sejiri Merfolk", 1);
    }

    @Test
    public void testCard2() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        /**
         * Goblin Deathraiders
         * Creature — Goblin Warrior 3/1, BR
         * Trample
         */
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Deathraiders");

        /**
         * Necrotic Plague
         * Enchantment — Aura, 2BB
         * Enchant creature
         * Enchanted creature has "At the beginning of your upkeep, sacrifice this creature."
         * When enchanted creature dies, its controller chooses target creature one of their
         * opponents controls. Return Necrotic Plague from its owner's graveyard to the
         * battlefield attached to that creature.
        */
        addCard(Zone.HAND, playerA, "Necrotic Plague");
        /**
         * Sejiri Merfolk English
         * Creature — Merfolk Soldier 2/1, 1U
         * As long as you control a Plains, Sejiri Merfolk has first strike and lifelink.
         * (Damage dealt by a creature with lifelink also causes its controller to gain that much life.)
         */
        addCard(Zone.BATTLEFIELD, playerB, "Sejiri Merfolk");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Necrotic Plague", "Sejiri Merfolk");
        addTarget(playerB, "Goblin Deathraiders"); // target for new necro attach

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);
        
        assertPermanentCount(playerB, "Sejiri Merfolk", 0);
        assertPermanentCount(playerA, "Goblin Deathraiders", 0);
        assertGraveyardCount(playerA, 2);
        assertGraveyardCount(playerA, "Necrotic Plague", 1);
        assertGraveyardCount(playerA, "Goblin Deathraiders", 1);
        assertGraveyardCount(playerB, 1);
        assertGraveyardCount(playerB, "Sejiri Merfolk", 1);
    }

}
