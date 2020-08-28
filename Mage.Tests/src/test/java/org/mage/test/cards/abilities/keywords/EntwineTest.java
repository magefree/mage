
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class EntwineTest extends CardTestPlayerBase {

    @Test
    public void test_ToothAndNail() {
        setStrictChooseMode(true);
        
        addCard(Zone.LIBRARY, playerA, "Silvercoat Lion", 1);
        addCard(Zone.LIBRARY, playerA, "Pillarfield Ox", 1);
        
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 9);
        // Choose one -
        // Search your library for up to two creature cards, reveal them, put them into your hand, then shuffle your library;
        // or put up to two creature cards from your hand onto the battlefield.
        // Entwine {2}
        addCard(Zone.HAND, playerA, "Tooth and Nail"); // Sorcery {5}{G}{G}
        

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tooth and Nail");
        setChoice(playerA, "Yes"); // Message: Pay Entwine {2} ?
        addTarget(playerA, "Silvercoat Lion^Pillarfield Ox");

        setChoice(playerA, "Silvercoat Lion^Pillarfield Ox");        
        

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAllCommandsUsed();
        
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Pillarfield Ox", 1);
    }
}
    