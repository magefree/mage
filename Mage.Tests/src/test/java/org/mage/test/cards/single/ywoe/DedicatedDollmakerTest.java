package org.mage.test.cards.single.ywoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class DedicatedDollmakerTest extends CardTestPlayerBase {

    @Test
    public void testOpponentGetsNonlegendaryArtifactCopy() {
        addCard(Zone.BATTLEFIELD, playerB, "Isamaru, Hound of Konda");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, "Dedicated Dollmaker");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dedicated Dollmaker");
        addTarget(playerA, "Isamaru, Hound of Konda");
        runCode("artifact copy", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            Permanent token = game.getBattlefield().getAllActivePermanents(playerB.getId()).stream()
                    .filter(permanent -> permanent.getName().equals("Isamaru, Hound of Konda")).findFirst().get();
            org.junit.Assert.assertTrue(token.isToken());
            org.junit.Assert.assertTrue(token.isArtifact(game));
            org.junit.Assert.assertFalse(token.isLegendary(game));
        });
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Isamaru, Hound of Konda", 1);
        assertPermanentCount(playerB, "Isamaru, Hound of Konda", 1);
    }

    @Test
    public void testTokenProtectionOnlyOnce() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.HAND, playerA, "Dedicated Dollmaker");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dedicated Dollmaker");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Tokens");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 3);
        addCard(Zone.HAND, playerB, "Volcanic Fallout");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Volcanic Fallout");
        checkPlayableAbility("used once", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}: Tokens", false);
        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Dedicated Dollmaker", 1);
    }

}
