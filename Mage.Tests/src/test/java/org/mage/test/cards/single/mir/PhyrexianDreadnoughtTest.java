package org.mage.test.cards.single.mir;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class PhyrexianDreadnoughtTest extends CardTestPlayerBase {

    /*
    Phyrexian Dreadnought
    {1}
    Artifact Creature — Phyrexian Dreadnought

    Trample

    When this creature enters, sacrifice it unless you sacrifice any number of creatures with total power 12 or greater.

    12/12
     */
    private static final String phyrexianDreadnought = "Phyrexian Dreadnought";

    @Test
    public void testPhyrexianDreadnoughtCanPay() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, phyrexianDreadnought);
        addCard(Zone.BATTLEFIELD, playerA, phyrexianDreadnought + "@sacTarget");
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phyrexianDreadnought);
        setChoice(playerA, true);
        setChoice(playerA, "@sacTarget");
        setChoice(playerA, TestPlayer.CHOICE_SKIP);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, phyrexianDreadnought, 1);
        assertGraveyardCount(playerA, phyrexianDreadnought, 1);
    }

    @Test
    public void testPhyrexianDreadnoughtCantPay() {
        setStrictChooseMode(true);

        addCard(Zone.HAND, playerA, phyrexianDreadnought);
        addCard(Zone.BATTLEFIELD, playerA, "Island");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, phyrexianDreadnought);

        setStopAt(1, PhaseStep.END_TURN);
        setChoice(playerA, false);
        execute();

        assertGraveyardCount(playerA, phyrexianDreadnought, 1);
    }
}
