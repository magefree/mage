package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Whenever one or more cards leave your graveyard...
 *
 * @author JayDi85
 */
public class CardsLeaveGraveyardTriggeredAbilityTest extends CardTestPlayerBase {

    @Test
    public void test_TwoCardsReturnToHand_OneTrigger() {
        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on Willow Geist.
        addCard(Zone.BATTLEFIELD, playerA, "Willow Geist");
        //
        // Return up to two target creature cards from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Morbid Plunder"); // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 2);

        // return 2 cards at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morbid Plunder");
        addTarget(playerA, "Grizzly Bears^Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 2);
        assertCounterCount(playerA, "Willow Geist", CounterType.P1P1, 1);
    }

    @Test
    public void test_CastFromGraveyard() {
        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on Willow Geist.
        addCard(Zone.BATTLEFIELD, playerA, "Willow Geist");
        //
        // Draw a card.
        // Flashback {2}{U}
        addCard(Zone.GRAVEYARD, playerA, "Think Twice");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // cast moves the card from graveyard to stack
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Flashback");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Think Twice", 1);
        assertCounterCount(playerA, "Willow Geist", CounterType.P1P1, 1);
    }

    @Test
    public void test_OpponentGraveyard_NoTrigger() {
        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on Willow Geist.
        addCard(Zone.BATTLEFIELD, playerA, "Willow Geist");
        //
        // When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.
        addCard(Zone.HAND, playerA, "Bojuka Bog");
        addCard(Zone.GRAVEYARD, playerB, "Grizzly Bears", 2);

        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, 2);
        assertCounterCount(playerA, "Willow Geist", CounterType.P1P1, 0);
    }

    @Test
    public void test_Filter_CreatureCards() {
        // Whenever one or more creature cards leave your graveyard, create a 1/1 black Bat creature token with flying.
        addCard(Zone.BATTLEFIELD, playerA, "Desecrated Tomb");
        //
        // When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.
        addCard(Zone.HAND, playerA, "Bojuka Bog", 2);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 2);
        //
        addCard(Zone.HAND, playerA, "Shock"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears", 1);

        // non creature cards - no trigger
        playLand(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerA);
        checkExileCount("forest", 1, PhaseStep.BEGIN_COMBAT, playerA, "Forest", 2);
        checkPermanentCount("no bat", 1, PhaseStep.BEGIN_COMBAT, playerA, "Bat Token", 0);

        // creature cards - trigger
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Grizzly Bears");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Bat Token", 1);
    }

    @Test
    public void test_DuringYourTurn() {
        // Whenever one or more cards leave your graveyard during your turn, create a Treasure token.
        addCard(Zone.BATTLEFIELD, playerA, "Kheru Goldkeeper");
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        addCard(Zone.GRAVEYARD, playerA, "Forest", 1);
        //
        // When Bojuka Bog enters the battlefield, exile all cards from target player's graveyard.
        addCard(Zone.HAND, playerA, "Bojuka Bog", 1);
        addCard(Zone.HAND, playerB, "Bojuka Bog", 1);
        //
        addCard(Zone.HAND, playerA, "Shock"); // {R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);

        // opponent's turn - no trigger
        playLand(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Bojuka Bog");
        addTarget(playerB, playerA);
        checkExileCount("on opponent turn", 2, PhaseStep.BEGIN_COMBAT, playerA, "Grizzly Bears", 1);
        checkPermanentCount("on opponent turn", 2, PhaseStep.BEGIN_COMBAT, playerA, "Treasure Token", 0);

        // own turn - trigger
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Shock", "Ornithopter");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        playLand(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Bojuka Bog");
        addTarget(playerA, playerA);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Ornithopter", 1);
        assertPermanentCount(playerA, "Treasure Token", 1);
    }
}
