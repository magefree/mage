package org.mage.test.cards.single.dft;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class KetramoseTheNewDawnTest extends CardTestPlayerBase {

    /**
     * Whenever one or more cards are put into exile from graveyards and/or the battlefield
     * during your turn, you draw a card and lose 1 life.
     */
    private final String ketramose = "Ketramose, the New Dawn";
    /**
     * {T}: Target player exiles a card from their graveyard.
     * {1}, Exile Relic of Progenitus: Exile all cards from all graveyards. Draw a card.
     */
    private final String relic = "Relic of Progenitus";
    /**
     * Exile target creature you control, then return it to the battlefield under its owner's control.
     */
    private final String ephemerate = "Ephemerate";

    @Test
    public void testExile() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, ketramose);
        addCard(Zone.BATTLEFIELD, playerA, relic);
        addCard(Zone.HAND, playerA, ephemerate);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 10);
        addCard(Zone.GRAVEYARD, playerB, "Forest", 10);

        // exile single
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Target player");
        addTarget(playerA, playerB);
        addTarget(playerB, "Forest");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("exile single - after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 - 1);

        // exile multiple
        // must have two triggers: exile on cost and exile on resolve
        // exile on cost
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}, Exile {this}");
        checkStackSize("exile on cost - ability + trigger on stack", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 2);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);
        checkLife("exile on cost - after trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 - 1 - 1);
        // exile on resolve
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("exile on resolve - after trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 - 1 - 1 - 1);

        // exile ketramose itself and return
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ephemerate, ketramose);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkLife("exile itself - must trigger", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20 - 1 - 1 - 1 - 1);

        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}