package org.mage.test.cards.single.c17;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.GameException;
import org.junit.Before;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;
import org.mage.test.serverside.base.CardTestMultiPlayerBaseWithRangeAll;

import java.io.FileNotFoundException;

/**
 * (Also tests {@link mage.abilities.common.EnchantedPlayerAttackedTriggeredAbility} since these enchantments are the only ones
 * that use that effect.)
 *
 * Whenever enchanted player is attacked, create a Gold token.
 * Each opponent attacking that player does the same.
 *
 * @author Alex-Vasile
 */
public class CurseOfOpulenceTest extends CardTestMultiPlayerBase {
    private static final String curseOfOpulence = "Curse of Opulence";
    private static final String mountain = "Mountain";
    // 1/1
    private static final String baneHound = "Banehound";

    /**
     * Test that it triggers when a player is attacked by you, and produces a single Gold token.
     */
    @Test
    public void controllerAttacks() {
        addCard(Zone.HAND, playerA, curseOfOpulence);
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.BATTLEFIELD, playerA, baneHound);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseOfOpulence, playerD);
        attack(1, playerA, baneHound, playerD);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerD, 19);
        assertPermanentCount(playerA, "Gold Token", 1);
        assertPermanentCount(playerD, "Gold Token", 0);
        assertPermanentCount(playerC, "Gold Token", 0);
        assertPermanentCount(playerB, "Gold Token", 0);
    }

    /**
     * Test that when an opponent attacks the player both you and the attacker make a Gold token.
     */
    @Test
    public void opponentAttacks() {
        addCard(Zone.HAND, playerA, curseOfOpulence);
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.BATTLEFIELD, playerB, baneHound);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseOfOpulence, playerA);
        attack(4, playerB, baneHound, playerA);

        setStopAt(4, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Gold Token", 1);
        assertPermanentCount(playerD, "Gold Token", 0);
        assertPermanentCount(playerC, "Gold Token", 0);
        assertPermanentCount(playerB, "Gold Token", 1);
    }

    /**
     * Test range of influence working correctly. If the attacking player is outside of your range of influence
     * the event will still trigger and you will still make your gold, but the attacker will not.
     * Player order: A -> D -> C -> B
     * So casting Curse of Opulence of playerB and having playerC should mean that playerC does not make a gold
     * since they are outside of A's range of influence.
     */
    @Test
    public void rangeOfInfluence() {
        addCard(Zone.HAND, playerA, curseOfOpulence);
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.BATTLEFIELD, playerC, baneHound);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseOfOpulence, playerB);
        attack(3, playerC, baneHound, playerB);

        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 19);
        assertPermanentCount(playerA, "Gold Token", 1);
        assertPermanentCount(playerD, "Gold Token", 0);
        assertPermanentCount(playerC, "Gold Token", 0);
        assertPermanentCount(playerB, "Gold Token", 0);
    }

    /**
     * Test that it only triggers once per attack step, regardless of the number of attacking creatues.
     */
    @Test
    public void doesNotTriggerMoreThanOnce() {
        addCard(Zone.HAND, playerA, curseOfOpulence);
        addCard(Zone.BATTLEFIELD, playerA, mountain);
        addCard(Zone.BATTLEFIELD, playerA, baneHound, 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, curseOfOpulence, playerD);
        attack(1, playerA, baneHound, playerD);
        attack(1, playerA, baneHound, playerD);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerD, 18);
        assertPermanentCount(playerA, "Gold Token", 1);
        assertPermanentCount(playerD, "Gold Token", 0);
        assertPermanentCount(playerC, "Gold Token", 0);
        assertPermanentCount(playerB, "Gold Token", 0);
    }
}
