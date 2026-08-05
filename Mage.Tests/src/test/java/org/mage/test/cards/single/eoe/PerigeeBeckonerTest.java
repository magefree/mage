package org.mage.test.cards.single.eoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PerigeeBeckonerTest extends CardTestPlayerBase {

    @Test
    public void testCreatureSacrificedToGitrogReturns() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "The Gitrog, Ravenous Ride");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.HAND, playerA, "Perigee Beckoner");
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Perigee Beckoner");
        addTarget(playerA, "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Saddle");
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);
        attack(1, playerA, "The Gitrog, Ravenous Ride", playerB);
        setChoice(playerA, "Grizzly Bears");
        setChoice(playerA, "Plains"); //Important step; if no land is chosen the test suceedes always
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 0);
        assertTapped("Grizzly Bears", true);
        assertHandCount(playerA, 4);
        assertPermanentCount(playerA, "Plains", 1);
    }
}
