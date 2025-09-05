package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class MultiversalPassageTest extends CardTestPlayerBase {

    /*
    Multiversal Passage
    
    Land
    As this land enters, choose a basic land type. Then you may pay 2 life. If you don't, it enters tapped.
    This land is the chosen type.
    */
    private static final String multiversalPassage = "Multiversal Passage";

    @Test
    public void testMultiversalPassage() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, multiversalPassage);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, multiversalPassage);
        setChoice(playerA, "Swamp");
        setChoice(playerA, true); // untapped

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertSubtype(multiversalPassage, SubType.SWAMP);
        assertLife(playerA, 20 - 2);
    }
}