package org.mage.test.cards.continuous;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class GoblinBushwhackerTest extends CardTestPlayerBase {

    @Test
    public void testKicker() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Goblin Bushwhacker - Creature — Goblin Warrior 1/1, R - Kicker {R} (You may pay an additional {R} as you cast this spell.)
        // When Goblin Bushwhacker enters the battlefield, if it was kicked, creatures you control get +1/+0 and gain haste until end of turn.
        addCard(Constants.Zone.HAND, playerA, "Goblin Bushwhacker");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Elite Vanguard");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Goblin Bushwhacker");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 3, 1);
    }
    
    /**
     * Tests doesn't work in library and in hand
     */
    @Test
    public void testDoesntWorkFromHand() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Constants.Zone.HAND, playerA, "Elite Vanguard");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Goblin Bushwhacker");
        addCard(Constants.Zone.LIBRARY, playerA, "Goblin Bushwhacker");
        addCard(Constants.Zone.GRAVEYARD, playerA, "Goblin Bushwhacker");

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Elite Vanguard", 1);
        assertPowerToughness(playerA, "Elite Vanguard", 2, 1);
    }

}