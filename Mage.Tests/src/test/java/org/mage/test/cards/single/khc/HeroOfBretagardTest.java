package org.mage.test.cards.single.khc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class HeroOfBretagardTest extends CardTestPlayerBase {

    @Test
    public void test_CardExiledFromHand() {
        // Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles one or
        // more permanents from the battlefield, put that many +1/+1 counters on Hero of Bretagard.
        addCard(Zone.BATTLEFIELD, playerA, "Hero of Bretagard"); // 1/1
        //
        // Target player reveals their hand. You choose an instant or sorcery card from it and exile that card.
        addCard(Zone.HAND, playerA, "Psychic Theft"); // {1}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Psychic Theft", playerA);
        setChoice(playerA, "Lightning Bolt");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN); // card returns to hand at the end step
        execute();

        assertExileCount(playerA, "Lightning Bolt", 1);
        assertCounterCount(playerA, "Hero of Bretagard", CounterType.P1P1, 1);
    }

    @Test
    public void test_TwoPermanentsExiledByOwnSpell_ThatManyCounters() {
        // Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles one or
        // more permanents from the battlefield, put that many +1/+1 counters on Hero of Bretagard.
        addCard(Zone.BATTLEFIELD, playerA, "Hero of Bretagard"); // 1/1
        //
        // Choose one -
        // * Exile all artifacts.
        // * Exile all creatures.
        // ...
        addCard(Zone.HAND, playerA, "Merciless Eviction"); // {4}{W}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Ornithopter", 2);

        // exile 2 artifacts at once
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Merciless Eviction");
        setModeChoice(playerA, "1");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerB, "Ornithopter", 2);
        assertCounterCount(playerA, "Hero of Bretagard", CounterType.P1P1, 2);
    }

    @Test
    public void test_PermanentExiledByOpponentSpell_NoTrigger() {
        // Whenever one or more cards are put into exile from your hand or a spell or ability you control exiles one or
        // more permanents from the battlefield, put that many +1/+1 counters on Hero of Bretagard.
        addCard(Zone.BATTLEFIELD, playerA, "Hero of Bretagard"); // 1/1
        //
        // Choose one -
        // * Exile all artifacts.
        // ...
        addCard(Zone.HAND, playerB, "Merciless Eviction"); // {4}{W}{B}
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 2);

        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Merciless Eviction");
        setModeChoice(playerB, "1");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, "Ornithopter", 2);
        assertCounterCount(playerA, "Hero of Bretagard", CounterType.P1P1, 0);
    }
}
