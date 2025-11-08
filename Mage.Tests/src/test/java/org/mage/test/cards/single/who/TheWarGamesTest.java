package org.mage.test.cards.single.who;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class TheWarGamesTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.t.TheWarGames The War Games} {2}{W}{W}
     * Enchantment — Saga
     * I — Each player creates three tapped 1/1 white Warrior creature tokens. The tokens are goaded for as long as this Saga remains on the battlefield.
     * II, III — Put a +1/+1 counter on each Warrior creature.
     * IV — You may exile a nontoken creature you control. When you do, exile all Warriors.
     */
    private static final String war = "The War Games";

    @Test
    public void test_SimplePlay_NoExile() {
        addCard(Zone.HAND, playerA, war, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);

        // no attack

        // turn 2
        // 3 1/1 must attack A

        // turn 3
        // 3 2/2 must attack B

        // turn 4
        // 3 2/2 must attack A

        // turn 5
        // 3 3/3 must attack B

        // turn 6
        // 3 3/3 must attack A

        // turn 7
        setChoice(playerA, false);
        // no mandatory attack

        // turn 8
        // no mandatory attack

        setStrictChooseMode(true);
        setStopAt(8, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Warrior Token", 3);
        assertPermanentCount(playerB, "Warrior Token", 3);
        assertLife(playerA, 20 - 3 - 6 - 9);
        assertLife(playerB, 20 - 6 - 9);
    }

    @Test
    public void test_SimplePlay_Exile() {
        addCard(Zone.HAND, playerA, war, 1);

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, war);

        // no attack

        // turn 2
        // 3 1/1 must attack A

        // turn 3
        // 3 2/2 must attack B

        // turn 4
        // 3 2/2 must attack A

        // turn 5
        // 3 3/3 must attack B

        // turn 6
        // 3 3/3 must attack A

        // turn 7
        setChoice(playerA, true);
        setChoice(playerA, "Memnite"); // exiling all Warriors

        setStrictChooseMode(true);
        setStopAt(7, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Warrior Token", 0);
        assertPermanentCount(playerB, "Warrior Token", 0);
        assertExileCount(playerA, "Memnite", 1);
        assertLife(playerA, 20 - 3 - 6 - 9);
        assertLife(playerB, 20 - 6 - 9);
    }
}
