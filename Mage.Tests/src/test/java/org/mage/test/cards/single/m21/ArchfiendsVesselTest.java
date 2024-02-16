package org.mage.test.cards.single.m21;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class ArchfiendsVesselTest extends CardTestPlayerBase {

    private static final String archfiendsVessel = "Archfiend's Vessel";

    @Test
    public void enterFromGraveyard(){
        //When Archfiend's Vessel enters the battlefield, if it entered from your graveyard or you cast it from your graveyard,
        // exile it. If you do, create a 5/5 black Demon creature token with flying.

        addCard(Zone.GRAVEYARD, playerA, archfiendsVessel);
        addCard(Zone.HAND, playerA, "Exhume");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhume");
        addTarget(playerA, archfiendsVessel);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Demon Token", 1);
        assertPermanentCount(playerA, archfiendsVessel, 0);
        assertExileCount(playerA, archfiendsVessel, 1);
        assertGraveyardCount(playerA, archfiendsVessel, 0);
    }

    @Test
    public void playFromGraveyard(){
        //When Archfiend's Vessel enters the battlefield, if it entered from your graveyard or you cast it from your graveyard,
        // exile it. If you do, create a 5/5 black Demon creature token with flying.

        addCard(Zone.GRAVEYARD, playerA, archfiendsVessel);
        // Until end of turn, you may play cards from your graveyard.
        addCard(Zone.HAND, playerA, "Yawgmoth's Will");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archfiendsVessel);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Demon Token", 1);
        assertPermanentCount(playerA, archfiendsVessel, 0);
        assertExileCount(playerA, archfiendsVessel, 1);
        assertGraveyardCount(playerA, archfiendsVessel, 0);
    }

    @Test
    public void diesWithAbilityStack() {
        // If Archfiend’s Vessel leaves the battlefield while its triggered ability is on the stack,
        // you can’t exile it from the zone it’s put into, so you won’t create a Demon.

        addCard(Zone.GRAVEYARD, playerA, archfiendsVessel);
        addCard(Zone.HAND, playerA, "Exhume");
        addCard(Zone.HAND, playerA, "Fatal Push");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exhume");
        addTarget(playerA, archfiendsVessel);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Fatal Push", archfiendsVessel);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Demon Token", 0);
        assertPermanentCount(playerA, archfiendsVessel, 0);
        assertExileCount(playerA, archfiendsVessel, 0);
        assertGraveyardCount(playerA, archfiendsVessel, 1);
    }
}
