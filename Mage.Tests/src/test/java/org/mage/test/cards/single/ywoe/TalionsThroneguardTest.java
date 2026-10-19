package org.mage.test.cards.single.ywoe;

import mage.constants.PhaseStep;
import mage.constants.Zone;

import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class TalionsThroneguardTest extends CardTestPlayerBase {

    @Test
    public void testBargainTaxesThePhysicalCardOfACopiedPermanent() {
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.HAND, playerA, "Talion's Throneguard");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Clone");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Clone");
        setChoice(playerB, true);
        setChoice(playerB, "Grizzly Bears");
        castSpell(2, PhaseStep.POSTCOMBAT_MAIN, playerA, "Talion's Throneguard");
        setChoice(playerA, true);
        setChoice(playerA, "Memnite");
        addTarget(playerA, "Grizzly Bears[only copy]");
        checkPlayableAbility("copied permanent's physical card is taxed", 4, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Clone", false);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Clone", 1);
    }

    @Test
    public void testBargainPermanentlyRaisesCost() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);
        addCard(Zone.HAND, playerA, "Talion's Throneguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Talion's Throneguard");
        setChoice(playerA, true);
        setChoice(playerA, "Memnite");
        addTarget(playerA, "Grizzly Bears");
        checkPlayableAbility("tax persists", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Cast Grizzly Bears", false);
        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Memnite", 1);
    }

    @Test
    public void testWithoutBargainBouncesSpell() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.HAND, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 4);
        addCard(Zone.HAND, playerB, "Talion's Throneguard");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Talion's Throneguard", null, "Grizzly Bears");
        addTarget(playerB, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerB, "Talion's Throneguard", 1);
    }

}
