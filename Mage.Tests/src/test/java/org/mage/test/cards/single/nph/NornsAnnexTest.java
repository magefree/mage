package org.mage.test.cards.single.nph;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Created by glerman on 23/6/15
 */
public class NornsAnnexTest extends CardTestPlayerBase {

    @Test
    public void testNornsAnnex() {
        setStrictChooseMode(true);
        
        // {W/P} ({W/P} can be paid with either or 2 life.)
        // Creatures can't attack you or a planeswalker you control unless their controller pays {W/P} for each of those creatures.      
        addCard(Zone.BATTLEFIELD, playerA, "Norn's Annex");
        addCard(Zone.BATTLEFIELD, playerB, "Brindle Boar"); // Creature 2/2

        attack(2, playerB, "Brindle Boar", playerA);
        setChoice(playerB, true); // Pay {W/P} to attack?
        setChoice(playerB, true); // Pay 2 life instead of {W}?
        
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 18);
        assertLife(playerB, 18);

    }
}
