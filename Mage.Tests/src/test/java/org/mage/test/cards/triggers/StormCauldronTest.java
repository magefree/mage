/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class StormCauldronTest extends CardTestPlayerBase {

    /**
     * With Storm Cauldron in play (owned by opponent), I sacced Crystal Vein
     * for 2 mana... except it got returned to my hand, which shouldn't happen.
     * Haven't tested it with other sac lands yet.
     *
     * Relevant ruing for Storm Cauldron:
     *
     * 10/4/2004: If a land is tapped for mana and sacrificed all in one action,
     * it goes to the graveyard before the Cauldron can return it to the
     * player's hand.
     *
     */
    @Test
    public void testLandNotReturnedToHand() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // {T}: Add {C}.
        // {T}, Sacrifice Crystal Vein: Add {C}{C}.
        addCard(Zone.BATTLEFIELD, playerA, "Crystal Vein", 1);

        // Each player may play an additional land during each of their turns.
        // Whenever a land is tapped for mana, return it to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerB, "Storm Cauldron", 1);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Crystal Vein");
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mountain");

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Sacrifice");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Crystal Vein", 1);
        assertPermanentCount(playerA, "Mountain", 1);
        assertHandCount(playerA, "Crystal Vein", 0);

    }

}
