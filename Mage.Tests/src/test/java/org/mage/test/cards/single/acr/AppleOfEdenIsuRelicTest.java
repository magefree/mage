package org.mage.test.cards.single.acr;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class AppleOfEdenIsuRelicTest extends CardTestPlayerBase {

    // {T}, Pay 4 life, Sacrifice Apple of Eden: Look at target opponent's hand and exile those cards face down.
    // You may play those cards this turn, and mana of any type can be spent to cast them.
    // Until end of turn, whenever you play a land or cast a spell this way, its owner draws a card.
    // At the beginning of the next end step, return the exiled cards to their owner's hand.
    // Activate only as a sorcery.
    private static final String apple = "Apple of Eden, Isu Relic";
    private static final String activate = "{T}, Pay 4 life, Sacrifice";

    @Test
    public void testExileHandAndPlayCards() {
        addCard(Zone.BATTLEFIELD, playerA, apple);
        // black mana only, so Lightning Bolt needs the "mana of any type" clause
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Forest");
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, activate, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("hand exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 0);
        checkExileCount("exiled bolt", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", 1);
        checkExileCount("exiled forest", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Forest", 1);
        checkExileCount("exiled bears", 1, PhaseStep.PRECOMBAT_MAIN, playerB, "Grizzly Bears", 1);

        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.BEGIN_COMBAT);
        checkHandCount("owner drew for the spell", 1, PhaseStep.BEGIN_COMBAT, playerB, 1);

        playLand(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Forest");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN);
        checkHandCount("owner drew for the land", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, 2);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        assertLife(playerA, 20 - 4);
        assertLife(playerB, 20 - 3);
        assertGraveyardCount(playerA, apple, 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertPermanentCount(playerA, "Forest", 1);
        // the card that wasn't played returns, the played ones don't
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, "Lightning Bolt", 0);
        assertHandCount(playerB, "Forest", 0);
        assertHandCount(playerB, 3);
        assertExileCount(playerB, 0);
    }

    @Test
    public void testUnplayedCardsReturnAtEndStep() {
        addCard(Zone.BATTLEFIELD, playerA, apple);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, activate, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkHandCount("hand exiled", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 0);
        checkExileCount("still exiled before end step", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertHandCount(playerB, "Lightning Bolt", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, 2);
        assertExileCount(playerB, 0);
    }

    @Test
    public void testCardsDrawnLaterAreNotExiled() {
        addCard(Zone.BATTLEFIELD, playerA, apple);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Shock");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, activate, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // the drawn card goes to the owner's hand and stays there
        checkHandCount("drawn card in hand", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, 1);
        checkExileCount("only shock still exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Shock", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerB, 20 - 3);
        assertHandCount(playerB, "Shock", 1);
        assertHandCount(playerB, 2);
        assertExileCount(playerB, 0);
    }

    @Test
    public void testLandPlayFollowsNormalRules() {
        addCard(Zone.BATTLEFIELD, playerA, apple);
        addCard(Zone.HAND, playerA, "Plains");
        addCard(Zone.HAND, playerB, "Forest");

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Plains");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, activate, playerB);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // the land for the turn was already played
        checkPlayableAbility("no land drop left", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Play Forest", false);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertPermanentCount(playerA, "Plains", 1);
        assertPermanentCount(playerA, "Forest", 0);
        assertHandCount(playerB, "Forest", 1);
        assertHandCount(playerB, 1);
    }

    @Test
    public void testActivateOnlyAsSorcery() {
        addCard(Zone.BATTLEFIELD, playerA, apple);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.HAND, playerB, "Grizzly Bears");

        checkPlayableAbility("main phase", 1, PhaseStep.PRECOMBAT_MAIN, playerA, activate, true);
        checkPlayableAbility("combat", 1, PhaseStep.DECLARE_ATTACKERS, playerA, activate, false);
        checkPlayableAbility("opponent's turn", 2, PhaseStep.PRECOMBAT_MAIN, playerA, activate, false);
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", playerB);
        checkPlayableAbility("spell on the stack", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, activate, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, apple, 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
    }

    @Test
    public void testOpponentWithEmptyHand() {
        addCard(Zone.BATTLEFIELD, playerA, apple);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, activate, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.CLEANUP);
        execute();

        assertLife(playerA, 20 - 4);
        assertGraveyardCount(playerA, apple, 1);
        assertHandCount(playerB, 0);
        assertExileCount(playerB, 0);
    }
}
