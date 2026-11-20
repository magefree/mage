package org.mage.test.cards.single.dom;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class NabanDeanOfIterationTest extends CardTestPlayerBase {

    @Test
    public void test_OneOrMoreWizardsEnter_TriggersAdditionalTime() {
        // If a Wizard entering the battlefield under your control causes a triggered ability of a permanent you control
        // to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Naban, Dean of Iteration");
        //
        // Whenever one or more tokens enter the battlefield under your control, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Kambal, Profiteering Mayor");
        //
        // Create two 1/1 blue Merfolk Wizard creature tokens.
        addCard(Zone.HAND, playerA, "Summon the School"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // two wizards enter at once - one trigger + one additional trigger
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Summon the School");
        setChoice(playerA, "Whenever one or more tokens you control enter"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Merfolk Wizard Token", 2);
        assertLife(playerA, 20 + 2);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_OpponentWizardsEnter_NoAdditionalTrigger() {
        // If a Wizard entering the battlefield under your control causes a triggered ability of a permanent you control
        // to trigger, that ability triggers an additional time.
        addCard(Zone.BATTLEFIELD, playerA, "Naban, Dean of Iteration");
        //
        // Whenever another creature enters the battlefield, you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Soul Warden");
        //
        // Create two 1/1 blue Merfolk Wizard creature tokens.
        addCard(Zone.HAND, playerB, "Summon the School"); // {3}{W}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 4);

        // opponent's wizards enter - normal triggers only
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Summon the School");
        setChoice(playerA, "Whenever another creature enters"); // two triggers order

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Merfolk Wizard Token", 2);
        assertLife(playerA, 20 + 2);
    }
}
