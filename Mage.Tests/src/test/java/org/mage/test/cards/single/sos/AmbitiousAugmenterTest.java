package org.mage.test.cards.single.sos;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AmbitiousAugmenterTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AmbitiousAugmenter Ambitious Augmenter} Ambitious Augmenter {G}
     * Creature — Turtle Wizard
     * Increment (Whenever you cast a spell, if the amount of mana you spent is greater than this creature’s power or toughness, put a +1/+1 counter on this creature.)
     * When this creature dies, if it had one or more counters on it, create a 0/0 green and blue Fractal creature token, then put this creature’s counters on that token.
     * 1/1
     */
    private static final String augmenter = "Ambitious Augmenter";

    // If one or more tokens would be created under your control,
    // those tokens plus that many 1/1 green Squirrel creature tokens are created instead.
    private static final String chatterfang = "Chatterfang, Squirrel General";

    // {2}{G} +3/+3 and put a trample counter.
    private static final String grown = "Fully Grown";

    private static final String doomblade = "Doom Blade";

    @Test
    public void test_Simple() {
        addCard(Zone.BATTLEFIELD, playerA, augmenter, 1);
        addCard(Zone.HAND, playerA, doomblade);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, doomblade, augmenter);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Fractal Token", 1);
        assertCounterCount(playerA, "Fractal Token", CounterType.P1P1, 1);
    }

    @Test
    public void test_Condition() {
        addCard(Zone.BATTLEFIELD, playerA, augmenter, 1);
        addCard(Zone.HAND, playerB, doomblade);
        addCard(Zone.BATTLEFIELD, playerB, "Swamp", 2);

        // Whenever a creature you control dies, each opponent loses 1 life and you gain 1 life.
        addCard(Zone.BATTLEFIELD, playerA, "Cauldron of Essence");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, doomblade, augmenter);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertLife(playerA, 20 + 1);
        assertPermanentCount(playerA, "Fractal Token", 0);
        assertPermanentCount(playerA, augmenter, 0);
    }

    @Test
    public void test_MultiCounters() {
        addCard(Zone.BATTLEFIELD, playerA, augmenter, 1);
        addCard(Zone.HAND, playerA, doomblade);
        addCard(Zone.HAND, playerA, grown);
        addCard(Zone.BATTLEFIELD, playerA, "Bayou", 5);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, grown, augmenter, true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, doomblade, augmenter, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Fractal Token", 1);
        assertCounterCount(playerA, "Fractal Token", CounterType.P1P1, 1);
        assertCounterCount(playerA, "Fractal Token", CounterType.TRAMPLE, 1);
    }

    @Test
    public void test_MultiTokens() {
        addCard(Zone.BATTLEFIELD, playerA, augmenter, 1);
        addCard(Zone.HAND, playerA, doomblade);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        addCard(Zone.BATTLEFIELD, playerA, chatterfang);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, doomblade, augmenter);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPermanentCount(playerA, "Fractal Token", 1);
        assertCounterCount(playerA, "Fractal Token", CounterType.P1P1, 1);
        assertPermanentCount(playerA, "Squirrel Token", 1);
        assertCounterCount(playerA, "Squirrel Token", CounterType.P1P1, 1);
    }
}
