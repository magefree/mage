package org.mage.test.cards.replacement;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class PulmonicSliverTest extends CardTestPlayerBase {
    
    @Test
    public void testKillSpellOnOtherSliver() {
        
        /*
        Pulmonic Sliver - {3}{W}{W} - Sliver
        All Sliver creatures have flying.
        All Slivers have "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead."
        */
        addCard(Zone.BATTLEFIELD, playerA, "Pulmonic Sliver");
        addCard(Zone.BATTLEFIELD, playerA, "Venom Sliver"); // 1/1 slivers get deathtouch
        addCard(Zone.HAND, playerB, "Doom Blade");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);
        
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Doom Blade");
        addTarget(playerB, "Venom Sliver");
        setChoice(playerA, true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Doom Blade", 1);
        assertGraveyardCount(playerA, "Venom Sliver", 0);
        assertPermanentCount(playerA, "Venom Sliver", 0);
        assertLibraryCount(playerA, "Venom Sliver", 1);
    }
    
    @Test
    public void testDamnationOnSlivers() {
        
        /*
        Pulmonic Sliver - {3}{W}{W} - Sliver
        All Sliver creatures have flying.
        All Slivers have "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead."
        */
        addCard(Zone.BATTLEFIELD, playerA, "Pulmonic Sliver");
        addCard(Zone.BATTLEFIELD, playerA, "Venom Sliver"); // 1/1 slivers get deathtouch
        addCard(Zone.HAND, playerB, "Damnation");
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 4);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Damnation");
        setChoice(playerA, true);
        setChoice(playerA, true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Damnation", 1);
        assertGraveyardCount(playerA, "Venom Sliver", 0);
        assertPermanentCount(playerA, "Venom Sliver", 0);
        assertLibraryCount(playerA, "Venom Sliver", 1);
        assertGraveyardCount(playerA, "Pulmonic Sliver", 0);
        assertPermanentCount(playerA, "Pulmonic Sliver", 0);
        assertLibraryCount(playerA, "Pulmonic Sliver", 1);
    }
    
    @Test
    public void testExileOnSliver() {
        
        /*
        Pulmonic Sliver - {3}{W}{W} - Sliver
        All Sliver creatures have flying.
        All Slivers have "If this permanent would be put into a graveyard, you may put it on top of its owner's library instead."
        */
        addCard(Zone.BATTLEFIELD, playerA, "Pulmonic Sliver");
        addCard(Zone.BATTLEFIELD, playerA, "Venom Sliver"); // 1/1 slivers get deathtouch
        addCard(Zone.HAND, playerB, "Path to Exile");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 1);
        
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Path to Exile");
        addTarget(playerB, "Venom Sliver");
        setChoice(playerA, true); // should not even have this as a choice
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        
        assertGraveyardCount(playerB, "Path to Exile", 1);
        assertGraveyardCount(playerA, "Venom Sliver", 0);
        assertPermanentCount(playerA, "Venom Sliver", 0);
        assertLibraryCount(playerA, "Venom Sliver", 0);
        assertExileCount("Venom Sliver", 1);
    }
}