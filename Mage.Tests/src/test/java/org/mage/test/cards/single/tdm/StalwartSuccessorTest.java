package org.mage.test.cards.single.tdm;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class StalwartSuccessorTest extends CardTestPlayerBase {

    /*
    Stalwart Successor
    {1}{B}{G}
    Creature — Human Warrior

    Menace (This creature can’t be blocked except by two or more creatures.)

    Whenever one or more counters are put on a creature you control, if it’s the first time counters have been put on that creature this turn, put a +1/+1 counter on that creature.

    3/2
     */
    private static final String stalwartSuccessor = "Stalwart Successor";
    /*
    Purestrain Genestealer
    {2}{G}
    Creature — Tyranid

    This creature enters with two +1/+1 counters on it.

    Vanguard Species — Whenever this creature attacks, you may remove a +1/+1 counter from it. If you do, search your library for a basic land card, put it onto the battlefield tapped, then shuffle.

    1/1
     */
    private static final String purestrainGenestealer = "Purestrain Genestealer";
    @Test
    public void testStalwartSuccessor() {

        addCard(Zone.BATTLEFIELD, playerA, stalwartSuccessor);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.HAND, playerA, purestrainGenestealer);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, purestrainGenestealer);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, purestrainGenestealer, CounterType.P1P1, 2 + 1);
    }
}
