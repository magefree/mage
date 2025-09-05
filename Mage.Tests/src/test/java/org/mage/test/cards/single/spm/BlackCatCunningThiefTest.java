package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class BlackCatCunningThiefTest extends CardTestPlayerBase {

    /*
    Black Cat, Cunning Thief
    {3}{B}{B}
    Legendary Creature - Human Rogue Villain
    When Black Cat enters, look at the top nine cards of target opponent's library, exile two of them face down, then put the rest on the bottom of their library in a random order. You may play the exiled cards for as long as they remain exiled. Mana of any type can be spent to cast spells this way.
    2/3
    */
    private static final String blackCatCunningThief = "Black Cat, Cunning Thief";

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
    public void testBlackCatCunningThief() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.HAND, playerA, blackCatCunningThief);
        addCard(Zone.LIBRARY, playerB, bearCub);
        addCard(Zone.LIBRARY, playerB, fugitiveWizard);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 8);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, blackCatCunningThief);
        addTarget(playerA, playerB);
        setChoice(playerA, bearCub);
        setChoice(playerA, fugitiveWizard);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, bearCub, true);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, fugitiveWizard);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, bearCub, 1);
        assertPermanentCount(playerA, fugitiveWizard, 1);
    }
}