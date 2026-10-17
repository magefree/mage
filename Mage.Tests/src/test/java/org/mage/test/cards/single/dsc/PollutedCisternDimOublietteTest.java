package org.mage.test.cards.single.dsc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class PollutedCisternDimOublietteTest extends CardTestPlayerBase {

    @Test
    public void test_CardsMilled_LoseLifeForEachCardType() {
        removeAllCardsFromLibrary(playerA);

        // Polluted Cistern {1}{B}
        // Whenever one or more cards are put into your graveyard from your library, each opponent loses 1 life for each card type among those cards.
        addCard(Zone.HAND, playerA, "Polluted Cistern // Dim Oubliette");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Forest", 2); // land
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 1); // creature
        addCard(Zone.LIBRARY, playerA, "Shock", 1); // instant

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Polluted Cistern");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // mill 4 cards with 3 card types at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Forest", 2);
        assertLife(playerB, 20 - 3);
    }

    @Test
    public void test_OpponentCardsMilled_NoTrigger() {
        removeAllCardsFromLibrary(playerB);

        // Polluted Cistern {1}{B}
        // Whenever one or more cards are put into your graveyard from your library, each opponent loses 1 life for each card type among those cards.
        addCard(Zone.HAND, playerA, "Polluted Cistern // Dim Oubliette");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerB, "Forest", 1);
        addCard(Zone.LIBRARY, playerB, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Polluted Cistern");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, 2);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void test_EachPlayerMills_OnlyYourCardsCount() {
        removeAllCardsFromLibrary(playerA);
        removeAllCardsFromLibrary(playerB);

        // Polluted Cistern {1}{B}
        // Whenever one or more cards are put into your graveyard from your library, each opponent loses 1 life for each card type among those cards.
        addCard(Zone.HAND, playerA, "Polluted Cistern // Dim Oubliette");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);
        //
        // Each player mills three cards. Then you return up to two creature cards from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Unseal the Necropolis"); // {2}{B}
        addCard(Zone.LIBRARY, playerA, "Forest", 3); // land
        addCard(Zone.LIBRARY, playerB, "Ornithopter", 3); // artifact creature

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Polluted Cistern");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // all players mill at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Unseal the Necropolis");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Ornithopter", 3);
        assertLife(playerB, 20 - 1); // only land
    }
}
