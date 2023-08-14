package org.mage.test.cards.single.onc;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.constants.PhaseStep;
import mage.constants.Zone;

/**
 * @author Xanderhall
 */
public class ChissGoriaForgeTyrantTest extends CardTestPlayerBase {
    
    private static final String CHISS = "Chiss-Goria, Forge Tyrant";
    private static final String CHALICE = "Marble Chalice";
    private static final String COLOSSUS = "Blightsteel Colossus"; 

    @Test
    public void test() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 15);
        addCard(Zone.BATTLEFIELD, playerA, CHALICE, 6);
        addCard(Zone.HAND, playerA, CHISS);
        addCard(Zone.LIBRARY, playerA, "Mountain", 4);
        addCard(Zone.LIBRARY, playerA, COLOSSUS, 1);
        skipInitShuffling();

        // Chiss-Goria should cost 3 mana, leaving 6 untapped
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, CHISS);

        attack(1, playerA, CHISS);
        // Chiss's on attack effect triggers, exiles the cards
        // Colossus should cost 6, tapping all mana
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, COLOSSUS, true);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertExileCount(playerA, 4);
        assertPermanentCount(playerA, COLOSSUS, 1);
        assertPermanentCount(playerA, CHISS, 1);
        assertTappedCount("Mountain", true, 9);
    }
}
