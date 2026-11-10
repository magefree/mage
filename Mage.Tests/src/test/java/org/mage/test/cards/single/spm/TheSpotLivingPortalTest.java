package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Jmlundeen
 */
public class TheSpotLivingPortalTest extends CardTestPlayerBase {

    @Test
    public void test_ExilePermanentAndGraveyardCard_ReturnOnDies() {
        addCustomEffect_TargetDestroy(playerB);

        // When The Spot enters, exile up to one target nonland permanent and up to one target nonland permanent card
        // from a graveyard.
        // When The Spot dies, put him on the bottom of his owner's library. If you do, return the exiled cards to their
        // owners' hands.
        addCard(Zone.HAND, playerA, "The Spot, Living Portal"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        //
        addCard(Zone.GRAVEYARD, playerA, "Bear Cub"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Fugitive Wizard"); // wizard 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Spot, Living Portal");
        addTarget(playerA, "Fugitive Wizard"); // target for exile from the battlefield
        addTarget(playerA, "Bear Cub"); // target for exile from a graveyard

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "target destroy");
        addTarget(playerB, "The Spot, Living Portal");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Bear Cub", 1);
        assertHandCount(playerB, "Fugitive Wizard", 1);
        assertLibraryCount(playerA, "The Spot, Living Portal", 1);
    }

    @Test
    public void test_ExileGraveyardCardOnly_ReturnOnDies() {
        addCustomEffect_TargetDestroy(playerB);

        // When The Spot enters, exile up to one target nonland permanent and up to one target nonland permanent card
        // from a graveyard.
        // When The Spot dies, put him on the bottom of his owner's library. If you do, return the exiled cards to their
        // owners' hands.
        addCard(Zone.HAND, playerA, "The Spot, Living Portal"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        //
        addCard(Zone.GRAVEYARD, playerA, "Bear Cub"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Fugitive Wizard"); // wizard 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Spot, Living Portal");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no target from the battlefield
        addTarget(playerA, "Bear Cub"); // target for exile from a graveyard

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "target destroy");
        addTarget(playerB, "The Spot, Living Portal");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Bear Cub", 1);
        assertPermanentCount(playerB, "Fugitive Wizard", 1);
        assertLibraryCount(playerA, "The Spot, Living Portal", 1);
    }

    @Test
    public void test_ExilePermanentOnly_ReturnOnDies() {
        addCustomEffect_TargetDestroy(playerB);

        // When The Spot enters, exile up to one target nonland permanent and up to one target nonland permanent card
        // from a graveyard.
        // When The Spot dies, put him on the bottom of his owner's library. If you do, return the exiled cards to their
        // owners' hands.
        addCard(Zone.HAND, playerA, "The Spot, Living Portal"); // {3}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        //
        addCard(Zone.GRAVEYARD, playerA, "Bear Cub"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Fugitive Wizard"); // wizard 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "The Spot, Living Portal");
        addTarget(playerA, "Fugitive Wizard"); // target for exile from the battlefield
        addTarget(playerA, TestPlayer.TARGET_SKIP); // no target from a graveyard

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "target destroy");
        addTarget(playerB, "The Spot, Living Portal");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerB, "Fugitive Wizard", 1);
        assertGraveyardCount(playerA, "Bear Cub", 1);
        assertLibraryCount(playerA, "The Spot, Living Portal", 1);
    }
}