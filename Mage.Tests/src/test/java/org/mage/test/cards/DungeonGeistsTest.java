package org.mage.test.cards;

import mage.Constants;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author BetaSteward
 */
public class DungeonGeistsTest extends CardTestPlayerBase {
    
    @Test
    public void testCard() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.HAND, playerA, "Dungeon Geists");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");        
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Dungeon Geists");
        setStopAt(2, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Dungeon Geists", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertTapped("Dungeon Geists", false);
        assertTapped("Craw Wurm", true);
    }

    @Test
    public void testCard1() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Constants.Zone.HAND, playerA, "Dungeon Geists");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");
        addCard(Constants.Zone.HAND, playerB, "Act of Treason");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Mountain", 3);
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Dungeon Geists");
        castSpell(2, Constants.PhaseStep.PRECOMBAT_MAIN, playerB, "Act of Treason", "Dungeon Geists");
        setStopAt(4, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Dungeon Geists", 1);
        assertPermanentCount(playerB, "Craw Wurm", 1);
        assertTapped("Dungeon Geists", false);
        assertTapped("Craw Wurm", false);
    }

    @Test
    public void testCard2() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Island", 9);
        addCard(Constants.Zone.HAND, playerA, "Dungeon Geists");
        addCard(Constants.Zone.HAND, playerA, "Mind Control");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Craw Wurm");
        
        castSpell(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Dungeon Geists");
        castSpell(1, Constants.PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Control", "Craw Wurm");
        setStopAt(3, Constants.PhaseStep.DRAW);
        execute();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertPermanentCount(playerA, "Dungeon Geists", 1);
        assertPermanentCount(playerA, "Craw Wurm", 1);
        assertTapped("Dungeon Geists", false);
        assertTapped("Craw Wurm", true);
    }

}
