package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 3G Creature - Human Warrior Whenever Briarbridge Patrol deals damage to one
 * or more creatures, investigate. (Put a colorless Clue artifact token onto the
 * battlefield with "2, Sacrifice this artifact: Draw a card.")
 *
 * At the beginning of each end step, if you sacrificed three or more clues this
 * turn, you may put a creature card from your hand onto the battlefield.
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class BriarbridgePatrolTest extends CardTestPlayerBase {

    /**
     * Deals damage to creature test.
     */
    @Test
    public void dealtDamageToCreatureInvestigate() {

        addCard(Zone.BATTLEFIELD, playerA, "Briarbridge Patrol", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Elite Vanguard", 1);

        attack(1, playerA, "Briarbridge Patrol");
        block(1, playerB, "Elite Vanguard", "Briarbridge Patrol");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1);
        assertPermanentCount(playerA, "Briarbridge Patrol", 1);
        assertPermanentCount(playerB, "Elite Vanguard", 0);
        assertGraveyardCount(playerB, "Elite Vanguard", 1);
    }

    /**
     * Reported bug: Briarbridge Patrol investigate doesn't trigger from
     * noncombat damage like Rabid Bite
     */
    @Test
    public void dealtNonCombatDamageToCreatureInvestigate() {
        // Whenever Briarbridge Patrol deals damage to one or more creatures, investigate (Create a colorless Clue artifact token onto the battlefield with "2, Sacrifice this artifact: Draw a card.").
        // At the beginning of each end step, if you sacrificed three or more Clues this turn, you may put a creature card from your hand onto the battlefield.
        addCard(Zone.BATTLEFIELD, playerA, "Briarbridge Patrol", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Wall of Roots", 1);
        // {1}{G} Sorcery Target creature you control deals damage equal to its power to target creature you don't control.
        addCard(Zone.HAND, playerA, "Rabid Bite");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Rabid Bite", "Briarbridge Patrol");
        addTarget(playerA, "Wall of Roots");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Clue Token", 1);
        assertPermanentCount(playerA, "Briarbridge Patrol", 1);
        assertPermanentCount(playerB, "Wall of Roots", 1);

        Permanent wall = getPermanent("Wall of Roots", playerB);
        Assert.assertEquals("Wall of Roots should have 3 damage to it", 3, wall.getDamage());
    }
}
