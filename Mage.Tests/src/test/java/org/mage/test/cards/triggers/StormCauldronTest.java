package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class StormCauldronTest extends CardTestPlayerBase {

    @Test
    public void testRegular() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Storm Cauldron", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Mountain", 1);
        assertPermanentCount(playerA, "Mountain", 0);
    }

    /**
     * With Storm Cauldron in play (owned by opponent), I sacced Crystal Vein
     * for 2 mana... except it got returned to my hand, which shouldn't happen.
     * Haven't tested it with other sac lands yet.
     * <p>
     * Relevant ruing for Storm Cauldron:
     * <p>
     * 10/4/2004: If a land is tapped for mana and sacrificed all in one action,
     * it goes to the graveyard before the Cauldron can return it to the
     * player's hand.
     */
    @Test
    public void testLandNotReturnedToHand() {
        addCard(Zone.HAND, playerA, "Mountain", 1);
        // {T}: Add {C}.
        // {T}, Sacrifice Crystal Vein: Add {C}{C}.
        addCard(Zone.HAND, playerA, "Crystal Vein", 1);
        //
        // Each player may play an additional land during each of their turns.
        // Whenever a land is tapped for mana, return it to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerB, "Storm Cauldron", 1);

        // can play two lands
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crystal Vein");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");

        activateManaAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{T}, Sacrifice");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Crystal Vein", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertHandCount(playerA, "Crystal Vein", 0);
    }
}
