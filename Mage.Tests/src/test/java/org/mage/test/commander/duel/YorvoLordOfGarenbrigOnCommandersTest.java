package org.mage.test.commander.duel;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommanderDuelBase;

/**
 * @author JayDi85
 */
public class YorvoLordOfGarenbrigOnCommandersTest extends CardTestCommanderDuelBase {

    @Test
    public void test_TriggerOnSimpleCommander() {
        // Yorvo, Lord of Garenbrig enters the battlefield with four +1/+1 counters on it.
        // Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on Yorvo. Then if that creature’s power is greater than Yorvo’s power, put another +1/+1 counter on Yorvo.
        addCard(Zone.HAND, playerA, "Yorvo, Lord of Garenbrig", 5); // {G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        addCard(Zone.COMMAND, playerA, "Aggressive Mammoth"); // {3}{G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);

        // prepare yorvo
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorvo, Lord of Garenbrig");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aggressive Mammoth");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("must get +2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorvo, Lord of Garenbrig", CounterType.P1P1, 4 + 2);
        checkPermanentCount("must play commander", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Aggressive Mammoth", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_TriggerOnUroTitanOfNaturesWrath() {
        // Yorvo, Lord of Garenbrig enters the battlefield with four +1/+1 counters on it.
        // Whenever another green creature enters the battlefield under your control, put a +1/+1 counter on Yorvo. Then if that creature’s power is greater than Yorvo’s power, put another +1/+1 counter on Yorvo.
        addCard(Zone.HAND, playerA, "Yorvo, Lord of Garenbrig", 5); // {G}{G}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        //
        // When Uro enters the battlefield, sacrifice it unless it escaped.
        // Whenever Uro enters the battlefield or attacks, you gain 3 life and draw a card, then you may put a land card from your hand onto the battlefield.
        // Escape-{G}{G}{U}{U}, Exile five other cards from your graveyard. (You may cast this card from your graveyard for its escape cost.)
        addCard(Zone.COMMAND, playerA, "Uro, Titan of Nature's Wrath"); // {1}{G}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);

        // prepare yorvo
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorvo, Lord of Garenbrig");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // cast commander
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uro, Titan of Nature's Wrath");

        // If the entering green creature dies before Yorvo’s triggered ability resolves, use its power as it last
        // existed on the battlefield to determine whether Yorvo gets a second +1/+1 counter.
        // (2019-10-04)

        // order triggers to remove commander first
        setChoice(playerA, "Whenever {this} enters the battlefield or attacks"); // draw trigger
        setChoice(playerA, "Whenever another green creature enters the battlefield"); // get counters trigger
        //setChoice(playerA, "When {this} enters the battlefield, sacrifice it"); // sacrifice trigger must be on top

        setChoice(playerA, true); // return commander to command zone
        setChoice(playerA, false); // do not put land to battlefield

        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCounters("must get +2", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Yorvo, Lord of Garenbrig", CounterType.P1P1, 4 + 2);
        checkCommandCardCount("return commander", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Uro, Titan of Nature's Wrath", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
