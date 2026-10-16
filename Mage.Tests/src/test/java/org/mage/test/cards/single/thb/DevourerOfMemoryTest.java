package org.mage.test.cards.single.thb;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class DevourerOfMemoryTest extends CardTestPlayerBase {

    @Test
    public void test_CardsMilled_OneTrigger() {
        removeAllCardsFromLibrary(playerA);

        // Whenever one or more cards are put into your graveyard from your library, Devourer of Memory gets +1/+1 until end of turn and can't be blocked this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Devourer of Memory"); // 2/1
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);
        addCard(Zone.LIBRARY, playerA, "Forest", 1);

        // mill 3 cards at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, 3 + 1); // milled + glimpse
        assertPowerToughness(playerA, "Devourer of Memory", 2 + 1, 1 + 1);
    }

    @Test
    public void test_OpponentCardsMilled_NoTrigger() {
        removeAllCardsFromLibrary(playerB);

        // Whenever one or more cards are put into your graveyard from your library, Devourer of Memory gets +1/+1 until end of turn and can't be blocked this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Devourer of Memory"); // 2/1
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Grizzly Bears", 2);
        assertPowerToughness(playerA, "Devourer of Memory", 2, 1);
    }
}
