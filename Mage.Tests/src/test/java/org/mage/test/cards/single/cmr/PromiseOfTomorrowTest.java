package org.mage.test.cards.single.cmr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */

public class PromiseOfTomorrowTest extends CardTestCommander4Players {

    @Test
    public void test_NormalCard() {
        // bug: https://github.com/magefree/mage/issues/7250

        // Whenever a creature you control dies, exile it.
        // At the beginning of each end step, if you control no creatures, sacrifice Promise of Tomorrow and return all
        // cards exiled with it to the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Promise of Tomorrow", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears@bear", 2);

        // destroy two creatures
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@bear.1");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "@bear.2");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 2);

        // must return
        checkGraveyardCount("after return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Promise of Tomorrow", 1);
        checkPermanentCount("after return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 2);        

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Commander_LeaveInZone() {
        // bug: https://github.com/magefree/mage/issues/7250

        // Whenever a creature you control dies, exile it.
        // At the beginning of each end step, if you control no creatures, sacrifice Promise of Tomorrow and return all
        // cards exiled with it to the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Promise of Tomorrow", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // prepare commander
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // destroy creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        setChoice(playerA, false); // move commander from graveyard to command zone
        setChoice(playerA, false); // move commander from exile to command zone

        // must return
        checkPermanentCount("after return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Promise of Tomorrow", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Commander_MoveToCommandZoneFirst() {
        // bug: https://github.com/magefree/mage/issues/7250

        // Whenever a creature you control dies, exile it.
        // At the beginning of each end step, if you control no creatures, sacrifice Promise of Tomorrow and return all
        // cards exiled with it to the battlefield under your control.
        addCard(Zone.BATTLEFIELD, playerA, "Promise of Tomorrow", 1);
        //
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // prepare commander
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}", 2);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        checkPermanentCount("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // destroy creature
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Balduvian Bears");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkExileCount("after die", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);

        // possible bug: Promise of Tomorrow tries to move commander card to exile from command zone with error
        setChoice(playerA, true); // move commander from graveyard to command zone
        setChoice(playerA, false); // move commander from exile to command zone

        // must return
        checkPermanentCount("after return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Balduvian Bears", 1);
        checkGraveyardCount("after return", 2, PhaseStep.PRECOMBAT_MAIN, playerA, "Promise of Tomorrow", 1);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();
    }
}
