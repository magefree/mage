package org.mage.test.cards.single.woe;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

public class FaerieFencingTest extends CardTestPlayerBase {
    
    private static String SPELL = "Faerie Fencing";
    private static String FAERIE = "Faerie Miscreant";
    private static String LION = "Silvercoat Lion";

    @Test
    public void testEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);    
        addCard(Zone.BATTLEFIELD, playerA, FAERIE, 1);
        addCard(Zone.HAND, playerA, SPELL, 1);

        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerB, LION, 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        // Player A casts Faerie Fencing for 1, controls a Faerie so lion should get -4/-4
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, SPELL, LION);
        setChoice(playerA, "X=1");
        
        // Player B kills Faerie in response
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", FAERIE, SPELL);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        setStrictChooseMode(true);
        execute();

        // Lion should still have died
        assertGraveyardCount(playerB, LION, 1);
    }
}