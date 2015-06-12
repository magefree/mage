package org.mage.test.cards.control;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Treacherous Pit-Dweller");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // under opponent's control
        assertPermanentCount(playerB, "Treacherous Pit-Dweller", 1);
    }

    @Test
    public void testChangeControlEffectFromTwoCards() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        // Enchant creature
        // When enchanted creature dies, return that card to the battlefield under your control.
        addCard(Zone.HAND, playerA, "Unhallowed Pact", 1); // {2}{B}
        // Undying
        // When Treacherous Pit-Dweller enters the battlefield from a graveyard, target opponent gains control of it.
        addCard(Zone.BATTLEFIELD, playerA, "Treacherous Pit-Dweller"); // 4/3
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);

        castSpell(1, PhaseStep.UPKEEP, playerA, "Lightning Bolt", "Treacherous Pit-Dweller"); // comes back with undying
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unhallowed Pact", "Treacherous Pit-Dweller"); 
        
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller"); // Treacherous Pit-Dweller is now 5/4
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Treacherous Pit-Dweller");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // went to graveyard
        assertGraveyardCount(playerA, "Unhallowed Pact", 1);
        assertGraveyardCount(playerA, "Lightning Bolt", 3);

        // returned back
        assertGraveyardCount(playerA, "Treacherous Pit-Dweller", 0);
        assertPermanentCount(playerB, "Treacherous Pit-Dweller", 1); // opponent gets it because ETB of Dweller resolves always last
    }
}
