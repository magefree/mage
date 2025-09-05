package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 *
 * @author Jmlundeen
 */
public class ParkerLuckTest extends CardTestCommander4Players {

    /*
    Parker Luck
    {2}{B}
    Enchantment
    At the beginning of your end step, two target players each reveal the top card of their library. They each lose life equal to the mana value of the card revealed by the other player. Then they each put the card they revealed into their hand.
    */
    private static final String parkerLuck = "Parker Luck";

    /*
    Bear Cub
    {1}{G}
    Creature - Bear
    
    2/2
    */
    private static final String bearCub = "Bear Cub";

    /*
    Fugitive Wizard
    {U}
    Creature - Human Wizard
    
    1/1
    */
    private static final String fugitiveWizard = "Fugitive Wizard";

    @Test
    public void testParkerLuck() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, parkerLuck);
        addCard(Zone.LIBRARY, playerD, bearCub);
        addCard(Zone.LIBRARY, playerC, fugitiveWizard);

        addTarget(playerA, playerC);
        addTarget(playerA, playerD);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerC, 20 - 2);
        assertLife(playerD, 20 - 1);
        assertHandCount(playerC, 1);
        assertHandCount(playerD, 1);
    }

    @Test
    public void testParkerLuckOneLibraryEmpty() {
        setStrictChooseMode(true);
        skipInitShuffling();
        removeAllCardsFromLibrary(playerC);

        addCard(Zone.BATTLEFIELD, playerA, parkerLuck);
        addCard(Zone.LIBRARY, playerD, bearCub);

        addTarget(playerA, playerC);
        addTarget(playerA, playerD);

        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerC, 20 - 2);
        assertHandCount(playerD, 1);
    }
}