package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.players.Player;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author Susucr
 */
public class AmpedRaptorTest extends CardTestPlayerBase {

    /**
     * {@link mage.cards.a.AmpedRaptor Amped Raptor} {1}{R}
     * Creature — Dinosaur
     * First strike
     * When Amped Raptor enters the battlefield, you get {E}{E} (two energy counters). Then if you cast it from your hand, exile cards from the top of your library until you exile a nonland card. You may cast that card by paying an amount of {E} equal to its mana value rather than paying its mana cost.
     * 2/1
     */
    private static final String raptor = "Amped Raptor";

    private static void checkEnergyCount(String message, Player player, int expected) {
        Assert.assertEquals(message, expected, player.getCountersCount(CounterType.ENERGY));
    }

    @Test
    public void test_Cast_Bolt() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, raptor);
        addCard(Zone.LIBRARY, playerA, "Lightning Bolt");
        addCard(Zone.LIBRARY, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, raptor);
        setChoice(playerA, true); // yes to "you may cast"
        addTarget(playerA, playerB); // Target for Bolt

        runCode("1 energy spent", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (info, player, game) -> checkEnergyCount(info, player, 2 - 1));

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 3);
        assertExileCount(playerA, 1);
        assertExileCount(playerA, "Plains", 1);
    }

    @Test
    public void test_CastStomp() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, raptor);
        addCard(Zone.LIBRARY, playerA, "Bonecrusher Giant");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, raptor);
        setChoice(playerA, true); // yes to "you may cast"
        setChoice(playerA, "Cast Stomp"); // Choose Stomp
        addTarget(playerA, playerB);

        runCode("After Stomp Cast, no energy left (2-2)", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (info, player, game) -> checkEnergyCount(info, player, 0));

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20 - 2);
        assertExileCount(playerA, 1); // Giant on an adventure
        assertExileCount(playerA, "Bonecrusher Giant", 1);
    }

    @Test
    public void test_CantCast_BonecrusherGiant() {
        setStrictChooseMode(true);
        skipInitShuffling();

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, raptor);
        addCard(Zone.LIBRARY, playerA, "Bonecrusher Giant");
        addCard(Zone.LIBRARY, playerA, "Plains");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, raptor);
        setChoice(playerA, true); // yes to "you may cast"
        setChoice(playerA, "Cast Bonecrusher Giant"); // Choose Bonecrusher Giant, it can't be cast.

        runCode("No energy spent", 1, PhaseStep.BEGIN_COMBAT, playerA,
                (info, player, game) -> checkEnergyCount(info, player, 2));

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerB, 20);
        assertExileCount(playerA, 2); // Giant (not on an adventure) + Plains in exile
        assertExileCount(playerA, "Bonecrusher Giant", 1);
        assertExileCount(playerA, "Plains", 1);
    }
}
