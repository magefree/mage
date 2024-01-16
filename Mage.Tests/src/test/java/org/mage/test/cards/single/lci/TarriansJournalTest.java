package org.mage.test.cards.single.lci;

import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class TarriansJournalTest extends CardTestPlayerBase {

    private static final String tj = "Tarrian's Journal";
    private static final String transformAbility = "{2}, {T}, Discard your hand: Transform";
    private static final String mayCastAbility = "{T}: You may cast a creature spell from your graveyard this turn. ";
    // If you do, it enters the battlefield with a finality counter on it and is a Vampire in addition to its other types.
    private static final String edgarMarkov = "Edgar Markov";
    // Whenever you cast another Vampire spell, if Edgar Markov is in the command zone or on the battlefield, create a 1/1 black Vampire creature token.
    private static final String kraken = "Kraken Hatchling"; // not a vampire

    @Test
    public void testFunctionality() {
        addCard(Zone.BATTLEFIELD, playerA, tj);
        addCard(Zone.BATTLEFIELD, playerA, edgarMarkov);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.HAND, playerA, kraken);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, transformAbility);

        checkHandCount("Hand discarded", 1, PhaseStep.BEGIN_COMBAT, playerA, 0);
        checkGraveyardCount("Kraken discarded", 1, PhaseStep.BEGIN_COMBAT, playerA, kraken, 1);
        checkPermanentCount("transformed", 1, PhaseStep.BEGIN_COMBAT, playerA, "The Tomb of Aclazotz", 1);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, mayCastAbility);

        castSpell(3, PhaseStep.POSTCOMBAT_MAIN, playerA, kraken);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, kraken, 1);
        assertPermanentCount(playerA, "Vampire Token", 1);
        assertSubtype(kraken, SubType.VAMPIRE);
        assertCounterCount(kraken, CounterType.FINALITY, 1);
    }
}
