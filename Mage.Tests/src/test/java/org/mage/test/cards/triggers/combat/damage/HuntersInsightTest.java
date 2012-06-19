package org.mage.test.cards.triggers.combat.damage;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 *
 *   Whenever this creature deals combat damage to a player or planeswalker, draw that many cards.
 */
public class HuntersInsightTest extends CardTestPlayerBase {

    @Test
    public void testDrawingCards() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Constants.Zone.HAND, playerA, "Hunter's Insight", 1);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Stampeding Rhino", 1);

        attack(3, playerA, "Stampeding Rhino");
        castSpell(3, Constants.PhaseStep.DECLARE_BLOCKERS, playerA, "Hunter's Insight", "Stampeding Rhino");

        setStopAt(3, Constants.PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 16);

        // +1 in draw phase
        // +4 in combat
        assertHandCount(playerA, 5);
    }

}
