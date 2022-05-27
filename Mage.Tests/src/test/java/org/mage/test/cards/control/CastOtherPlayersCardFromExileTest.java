
package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.t.ThadaAdelAcquisitor Thada Adel, Acquisitor}
 * {1}{U}{U}
 * Legendary Creature — Merfolk Rogue
 * P/T 2/2
 * Islandwalk (This creature can’t be blocked as long as defending player controls an Island.)
 * Whenever Thada Adel, Acquisitor deals combat damage to a player, search that player’s library for an artifact card and exile it.
 * Then that player shuffles. Until end of turn, you may play that card.
 *
 * @author LevelX2
 */
public class CastOtherPlayersCardFromExileTest extends CardTestPlayerBase {

    /**
     * https://github.com/magefree/mage/issues/2721 Praetor's Grasp and Thada
     * Adel, Acquisitor, and possibly other cards with similar effects cause the
     * owner of stolen cards to be unable to cast those cards if they somehow
     * reacquire them.
     *
     * Specific examples I've encountered:
     *
     * In two separate games with Thada Adel, Acquisitor, I stole a Sol Ring and
     * cast it. After some board wipes and graveyard digging, the Sol Rings
     * found their way back to their owners' hands. They were unable to cast
     * them.
     *
     * I stole a Spine of Ish Sah with Praetor's Grasp. After a board wipe, the
     * Spine returned to its owner's hand and they were unable to cast it.
     */
    @Test
    public void testCastWithThadaAdelAcquisitor() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Thada Adel, Acquisitor", 1); // Creature {1}{U}{U} 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        // Sacrifice Bottle Gnomes: You gain 3 life.
        addCard(Zone.LIBRARY, playerB, "Bottle Gnomes", 8); // Creature {3} 1/3
        // Return target creature card from your graveyard to your hand.
        addCard(Zone.HAND, playerB, "Wildwood Rebirth"); // Instant {1}{G}

        attack(1, playerA, "Thada Adel, Acquisitor");
        addTarget(playerA, "Bottle Gnomes");

//        setStrictChooseMode(true);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bottle Gnomes");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Wildwood Rebirth", "Bottle Gnomes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bottle Gnomes");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertLife(playerA, 23);
        assertGraveyardCount(playerB, "Wildwood Rebirth", 1);
        assertPermanentCount(playerB, "Bottle Gnomes", 1);
    }

    @Test
    public void testCastWithThadaAdelAcquisitorReturnedFromBattlefield() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Thada Adel, Acquisitor", 1); // Creature {1}{U}{U} 2/2

        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Sacrifice Bottle Gnomes: You gain 3 life.
        addCard(Zone.LIBRARY, playerB, "Bottle Gnomes", 8); // Creature {3} 1/3
        // Return target creature you own to your hand.
        // Flashback {W}
        addCard(Zone.HAND, playerB, "Saving Grasp"); // Instant {U}

        attack(1, playerA, "Thada Adel, Acquisitor");
        addTarget(playerA, "Bottle Gnomes");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Bottle Gnomes");

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Saving Grasp", "Bottle Gnomes");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bottle Gnomes");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerB, 18);
        assertLife(playerA, 20);
        assertGraveyardCount(playerB, "Saving Grasp", 1);
        assertPermanentCount(playerB, "Bottle Gnomes", 1);
    }
}
