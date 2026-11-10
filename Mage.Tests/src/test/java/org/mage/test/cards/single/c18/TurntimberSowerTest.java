package org.mage.test.cards.single.c18;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TurntimberSowerTest extends CardTestPlayerBase {

    @Test
    public void test_LandCardsMilled_OneTrigger() {
        removeAllCardsFromLibrary(playerA);

        // Whenever one or more land cards are put into your graveyard from anywhere, create a 0/1 green Plant creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Turntimber Sower");
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
        assertPermanentCount(playerA, "Plant Token", 1);
    }

    @Test
    public void test_NonLandCardsMilled_NoTrigger() {
        removeAllCardsFromLibrary(playerA);

        // Whenever one or more land cards are put into your graveyard from anywhere, create a 0/1 green Plant creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Turntimber Sower");
        //
        // Target player mills ten cards.
        addCard(Zone.HAND, playerA, "Glimpse the Unthinkable"); // {U}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        //
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Glimpse the Unthinkable", playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grizzly Bears", 2);
        assertPermanentCount(playerA, "Plant Token", 0);
    }

    @Test
    public void test_OpponentLandCardsMilled_NoTrigger() {
        removeAllCardsFromLibrary(playerB);

        // Whenever one or more land cards are put into your graveyard from anywhere, create a 0/1 green Plant creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Turntimber Sower");
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
        assertPermanentCount(playerA, "Plant Token", 0);
        assertPermanentCount(playerB, "Plant Token", 0);
    }

    @Test
    public void test_LandFromBattlefield() {
        // Whenever one or more land cards are put into your graveyard from anywhere, create a 0/1 green Plant creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Turntimber Sower");
        //
        // Destroy target land.
        addCard(Zone.HAND, playerA, "Stone Rain"); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Stone Rain", "Forest");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Forest", 1);
        assertPermanentCount(playerA, "Plant Token", 1);
    }
}
