package org.mage.test.cards.single.soc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class SpiritOfResilienceTest extends CardTestPlayerBase {

    @Test
    public void test_CardsLeaveGraveyard_CounterOnly() {
        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on this creature, then you may have
        // this creature become a copy of an artifact or creature card from among those cards until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Spirit of Resilience"); // 2/2
        //
        // Return up to two target creature cards from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Morbid Plunder"); // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter", 1);

        // return 2 cards at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morbid Plunder");
        addTarget(playerA, "Grizzly Bears^Ornithopter");
        setChoice(playerA, false); // no copy

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertHandCount(playerA, "Grizzly Bears", 1);
        assertHandCount(playerA, "Ornithopter", 1);
        assertCounterCount(playerA, "Spirit of Resilience", CounterType.P1P1, 1);
    }

    @Test
    public void test_CardsLeaveGraveyard_BecomeCopy() {
        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on this creature, then you may have
        // this creature become a copy of an artifact or creature card from among those cards until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Spirit of Resilience"); // 2/2
        //
        // Return up to two target creature cards from your graveyard to your hand.
        addCard(Zone.HAND, playerA, "Morbid Plunder"); // {1}{B}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.GRAVEYARD, playerA, "Grizzly Bears", 1);
        addCard(Zone.GRAVEYARD, playerA, "Ornithopter", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Morbid Plunder");
        addTarget(playerA, "Grizzly Bears^Ornithopter");
        setChoice(playerA, true); // copy
        setChoice(playerA, "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, "Spirit of Resilience", 0);
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPowerToughness(playerA, "Grizzly Bears", 2 + 1, 2 + 1);
    }

    @Test
    public void test_OpponentCardsLeaveGraveyard_NoTrigger() {
        // Whenever one or more cards leave your graveyard, put a +1/+1 counter on this creature, then you may have
        // this creature become a copy of an artifact or creature card from among those cards until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Spirit of Resilience"); // 2/2
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
        assertCounterCount(playerA, "Spirit of Resilience", CounterType.P1P1, 0);
    }
}
