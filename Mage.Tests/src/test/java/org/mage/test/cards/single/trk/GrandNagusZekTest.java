package org.mage.test.cards.single.trk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class GrandNagusZekTest extends CardTestPlayerBase {

    @Test
    public void testDyingOnceSimultaneous() {
        addCard(Zone.BATTLEFIELD, playerA, "Grand Nagus Zek");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        addCard(Zone.HAND, playerA, "Doom Blade");
        addCard(Zone.HAND, playerA, "Wrath of God");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after Doom Blade", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treasure Token", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grand Nagus Zek", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    @Test
    public void testBlink() {
        addCard(Zone.BATTLEFIELD, playerA, "Grand Nagus Zek");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 2);

        addCard(Zone.HAND, playerA, "Doom Blade", 2);
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after first Doom Blade", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treasure Token", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", "Grand Nagus Zek");
        setChoice(playerA, "White");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after Cloudshift", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treasure Token", 0);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Doom Blade", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grand Nagus Zek", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }

    @Test
    public void testTokenCopy() {
        addCard(Zone.BATTLEFIELD, playerA, "Feldon of the Third Path");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.GRAVEYARD, playerA, "Grand Nagus Zek");

        addCard(Zone.HAND, playerA, "Go for the Throat");
        addCard(Zone.HAND, playerA, "Wrath of God");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}, {T}: Create a token", "Grand Nagus Zek");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after token copy", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grand Nagus Zek", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Go for the Throat", "Grizzly Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after first death", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Treasure Token", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wrath of God");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Grand Nagus Zek", 1);
        assertGraveyardCount(playerA, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Feldon of the Third Path", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }
}
