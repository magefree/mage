package org.mage.test.cards.control;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests the effect:
 *   - Target opponent gains control of {this}
 *
 * @author noxx
 */
public class TargetOpponentGainsControlTest extends CardTestPlayerBase {

    @Test
    public void testPermanentControlEffect() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Treacherous Pit-Dweller");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        // under opponent's control
        assertPermanentCount(playerB, "Treacherous Pit-Dweller", 1);
    }

    @Test
    public void testChangeControlEffectFromTwoCards() {
        addCard(Constants.Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Constants.Zone.HAND, playerA, "Unhallowed Pact", 3);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Treacherous Pit-Dweller");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Unhallowed Pact", "Treacherous Pit-Dweller");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");

        setStopAt(1, Constants.PhaseStep.END_TURN);
        execute();

        // went to graveyard
        assertGraveyardCount(playerA, "Unhallowed Pact", 1);

        // returned back
        assertPermanentCount(playerA, "Treacherous Pit-Dweller", 1);
    }
}
