package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Paradigm (SOS)
 * <p>
 * "Then exile this spell. After you first resolve a spell with this name, you
 * may cast a copy of it from exile without paying its mana cost at the
 * beginning of each of your first main phases."
 * <p>
 * Rulings (Scryfall, SOS release):
 * <ul>
 * <li>Paradigm creates a delayed triggered ability that triggers at the
 * beginning of each of your first main phases for the rest of the game. When
 * that ability resolves and the copy is created, you can choose not to cast
 * the copy; it then ceases to exist. The ability still triggers again on your
 * following first main phases.</li>
 * <li>Once a spell with paradigm has resolved, it doesn't matter what happens
 * to the card in exile — the ability keeps triggering and the copy is still
 * created.</li>
 * </ul>
 *
 * @author DeckLab
 */
public class ParadigmTest extends CardTestPlayerBase {

    // {3}{G}{G} Sorcery — Lesson
    // Put two +1/+1 counters on each creature you control.
    // Paradigm
    private static final String PRACTICUM = "Germination Practicum";

    private static final String BEARS = "Grizzly Bears"; // 2/2

    @Test
    public void testResolvingExilesAndPutsTwoCountersEach() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.HAND, playerA, PRACTICUM);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PRACTICUM);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, BEARS, CounterType.P1P1, 2); // two counters, not one
        assertPowerToughness(playerA, BEARS, 4, 4);
        assertExileCount(playerA, PRACTICUM, 1); // exiled as it resolves
        assertGraveyardCount(playerA, PRACTICUM, 0);
    }

    @Test
    public void testFreeCopyOnEachOfYourFirstMainPhases() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.HAND, playerA, PRACTICUM);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PRACTICUM);
        setChoice(playerA, true); // turn 3: cast the copy for free
        setChoice(playerA, true); // turn 5: cast the copy for free again

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, BEARS, CounterType.P1P1, 6); // turns 1, 3 and 5
        assertPowerToughness(playerA, BEARS, 8, 8);
        assertExileCount(playerA, PRACTICUM, 1); // the exiled card itself never moves
        assertGraveyardCount(playerA, PRACTICUM, 0); // resolved copies cease to exist
    }

    @Test
    public void testDeclinedCopyStillTriggersOnLaterTurns() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.HAND, playerA, PRACTICUM);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PRACTICUM);
        setChoice(playerA, false); // turn 3: decline the copy
        setChoice(playerA, true);  // turn 5: cast it

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, BEARS, CounterType.P1P1, 4); // turns 1 and 5 only
        assertExileCount(playerA, PRACTICUM, 1);
    }

    @Test
    public void testCounteredSpellGoesToGraveyardAndSetsUpNothing() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, BEARS);
        addCard(Zone.HAND, playerA, PRACTICUM);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 2);
        addCard(Zone.HAND, playerB, "Counterspell");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, PRACTICUM);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Counterspell", PRACTICUM);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertCounterCount(playerA, BEARS, CounterType.P1P1, 0);
        assertGraveyardCount(playerA, PRACTICUM, 1); // countered, not exiled
        assertExileCount(playerA, PRACTICUM, 0);
    }
}
