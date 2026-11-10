package org.mage.test.cards.single.bro;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TitaniaVoiceOfGaeaTest extends CardTestPlayerBase {

    @Test
    public void test_LandCardsMilled_OneTrigger() {
        removeAllCardsFromLibrary(playerA);

        // Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Titania, Voice of Gaea");
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Forest", 2);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1);

        // mill 2 lands and 1 creature at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Forest", 2);
        assertLife(playerA, 20 + 2);
    }

    @Test
    public void test_OpponentLandCardsMilled_NoTrigger() {
        removeAllCardsFromLibrary(playerB);

        // Whenever one or more land cards are put into your graveyard from anywhere, you gain 2 life.
        addCard(Zone.BATTLEFIELD, playerA, "Titania, Voice of Gaea");
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerB, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Forest", 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }
}
