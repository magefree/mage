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
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Demon", 1);
        assertExileCount(playerA, archfiendsVessel, 1);

    }

    @Test
    public void playFromGraveyard(){
        //When Archfiend's Vessel enters the battlefield, if it entered from your graveyard or you cast it from your graveyard,
        // exile it. If you do, create a 5/5 black Demon creature token with flying.

        addCard(Zone.GRAVEYARD, playerA, archfiendsVessel);
        // Until end of turn, you may play cards from your graveyard.
        addCard(Zone.HAND, playerA, "Yawgmoth's Will");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 10);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yawgmoth's Will");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, archfiendsVessel);
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();
        assertAllCommandsUsed();

        assertPermanentCount(playerA, "Demon", 1);
        assertExileCount(playerA, archfiendsVessel, 1);

    }
}
