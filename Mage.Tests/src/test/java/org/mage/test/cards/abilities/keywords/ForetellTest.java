/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author jeffwadsworth
 */
public class ForetellTest extends CardTestPlayerBase {

    @Test
    public void testForetellKeyword() {
        // verify that the foretold card is in the exile zone
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);  // Foretell cost 2 mana
        addCard(Zone.HAND, playerA, "Behold the Multiverse");  // (Instant) Scry 2 and draw 2 cards
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, "Behold the Multiverse", 1);

    }

    @Test
    public void testForetoldCastSameTurnAsForetold() {
        // verify that foretold card can't be cast the same turn it was foretold
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);  // Foretell cost 2 mana and {1}{U} for foretell cast from exile
        addCard(Zone.HAND, playerA, "Behold the Multiverse");  // (Instant) Scry 2 and draw 2 cards
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
        checkPlayableAbility("Can't cast turn it was forefold", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Foretell {1}{U}", false);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, "Behold the Multiverse", 1); // still in exile because it can't be cast the same turn
    }

    @Test
    public void testForetoldCastOtherTurnAsForetold() {
        // verify that foretold card can be cast on a turn other than the one it was foretold
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);  // Foretell cost 2 mana and {1}{U} for foretell cast from exile
        addCard(Zone.HAND, playerA, "Behold the Multiverse");  // (Instant) Scry 2 and draw 2 cards
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore");
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerA, "Foretell {1}{U}");
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertAllCommandsUsed();
        assertExileCount(playerA, "Behold the Multiverse", 0); // no longer in exile
        assertGraveyardCount(playerA, "Behold the Multiverse", 1);  // now in graveyard
        assertHandCount(playerA, 2); // 2 cards drawn
    }
    
    @Test
    public void testDreamDevourerTrigger() {
        // Issue #8876
        setStrictChooseMode(true);
        addCard(Zone.BATTLEFIELD, playerA, "Dream Devourer", 1); // if a card is foretelled, this gets +2 power
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5); // 3 mana for suspend and 2 for foretell
        addCard(Zone.HAND, playerA, "Sol Talisman", 1);  // Suspend card
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1); // card to foretell
        
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Susp"); // suspend the Sol Talisman
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fore"); // foretell the Lightning Bolt
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        
        assertAllCommandsUsed();
        assertExileCount(playerA, "Sol Talisman", 1); // suspend card in exile
        assertExileCount(playerA, "Lightning Bolt", 1); // foretold card in exile
        assertPowerToughness(playerA, "Dream Devourer", 2, 3);  // +2 power boost from trigger due to foretell of Lightning Bolt
    }
}
