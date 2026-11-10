package org.mage.test.cards.single.lcc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SkeletonCrewTest extends CardTestPlayerBase {

    @Test
    public void test_OtherCreatureLeavesGraveyard() {
        // Whenever one or more creature cards leave your graveyard, create a 2/2 black Skeleton Pirate creature token.
        addCard(Zone.BATTLEFIELD, playerA, "Skeleton Crew");
        //
        // Put target creature card from a graveyard onto the battlefield under your control.
        // You lose life equal to its mana value.
        addCard(Zone.HAND, playerA, "Reanimate"); // {B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Reanimate", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Skeleton Pirate Token", 1);
    }

    @Test
    @Ignore // TODO: leaves graveyard triggers look back in time (CR 603.10a), so the returned permanent must not trigger its own ability, see #13415
    public void test_ReturnItself_NoTrigger() {
        // Whenever one or more creature cards leave your graveyard, create a 2/2 black Skeleton Pirate creature token.
        // {5}{B}: Return Skeleton Crew from your graveyard to the battlefield tapped.
        addCard(Zone.GRAVEYARD, playerA, "Skeleton Crew");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 6);

        // return itself - its battlefield ability did not exist before the event
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{B}: Return");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Skeleton Crew", 1);
        assertPermanentCount(playerA, "Skeleton Pirate Token", 0);
    }
}
