package org.mage.test.cards.single.spm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Jmlundeen
 */
public class TheSpotLivingPortalTest extends CardTestPlayerBase {

    /*
    The Spot, Living Portal
    {3}{W}{B}
    Legendary Creature - Human Scientist Villain
    When The Spot enters, exile up to one target nonland permanent and up to one target nonland permanent card from a graveyard.
    When The Spot dies, put him on the bottom of his owner's library. If you do, return the exiled cards to their owners' hands.
    4/4
    */
    private static final String theSpotLivingPortal = "The Spot, Living Portal";

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
    public void testTheSpotLivingPortal() {
        setStrictChooseMode(true);

        addCustomEffect_TargetDestroy(playerB);
        addCard(Zone.HAND, playerA, theSpotLivingPortal);
        addCard(Zone.GRAVEYARD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.BATTLEFIELD, playerB, fugitiveWizard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, theSpotLivingPortal);
        addTarget(playerA, bearCub);
        addTarget(playerA, fugitiveWizard);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "target destroy");
        addTarget(playerB, theSpotLivingPortal);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, bearCub, 1);
        assertHandCount(playerB, fugitiveWizard, 1);
        assertLibraryCount(playerA, theSpotLivingPortal, 1);
    }

    @Test
    public void testOnlyGraveyard() {
        setStrictChooseMode(true);

        addCustomEffect_TargetDestroy(playerB);
        addCard(Zone.HAND, playerA, theSpotLivingPortal);
        addCard(Zone.GRAVEYARD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.BATTLEFIELD, playerB, fugitiveWizard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, theSpotLivingPortal);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        addTarget(playerA, bearCub);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "target destroy");
        addTarget(playerB, theSpotLivingPortal);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, bearCub, 1);
        assertLibraryCount(playerA, theSpotLivingPortal, 1);
    }

    @Test
    public void testOnlyBattlefield() {
        setStrictChooseMode(true);

        addCustomEffect_TargetDestroy(playerB);
        addCard(Zone.HAND, playerA, theSpotLivingPortal);
        addCard(Zone.GRAVEYARD, playerA, bearCub);
        addCard(Zone.BATTLEFIELD, playerA, "Scrubland", 5);
        addCard(Zone.BATTLEFIELD, playerB, fugitiveWizard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, theSpotLivingPortal);
        addTarget(playerA, fugitiveWizard);
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "target destroy");
        addTarget(playerB, theSpotLivingPortal);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, fugitiveWizard, 1);
        assertLibraryCount(playerA, theSpotLivingPortal, 1);
    }
}