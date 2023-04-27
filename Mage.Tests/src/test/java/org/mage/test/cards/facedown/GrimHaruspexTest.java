package org.mage.test.cards.facedown;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GrimHaruspexTest extends CardTestPlayerBase {
    @Test
    public void testMorphed() {
        setStrictChooseMode(true);
        
        addCard(Zone.HAND, playerA, "Wrath of God");
        // Morph {B}
        // Whenever another nontoken creature you control dies, draw a card.        
        addCard(Zone.HAND, playerA, "Grim Haruspex");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grim Haruspex", true);
        setChoice(playerA, true); // cast it face down as 2/2 creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Grim Haruspex", 1);
        assertHandCount(playerA, 0);
    }
}
