package org.mage.test.cards.single.msh;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author muz
 */
public class TeamworkTest extends CardTestPlayerBase {

    private static final String goNuts = "Go Nuts!";
    private static final String maria = "Agent Maria Hill";
    private static final String lion = "Silvercoat Lion";
    private static final String bear = "Grizzly Bears";
    private static final String vanguard = "Elite Vanguard";

    @Test
    public void teamworkPaidWithAgentMariaHillChoosesBothModesAndTriggers() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.HAND, playerA, goNuts);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, maria);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, vanguard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goNuts);
        setChoice(playerA, true); // Pay Teamwork 3
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        addTarget(playerA, bear); // +1/+1 counter mode
        addTarget(playerA, bear); // fight mode, creature you control
        addTarget(playerA, vanguard); // fight mode, creature an opponent controls
        setChoice(playerA, maria + "^" + lion); // Teamwork creatures, total power 4
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Stop selecting teamwork creatures

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(maria, true);
        assertTapped(lion, true);
        assertCounterCount(playerA, maria, CounterType.P1P1, 1);
        assertHandCount(playerA, 1); // Agent Maria Hill drew the Island

        assertCounterCount(playerA, bear, CounterType.P1P1, 1);
        assertPermanentCount(playerA, bear, 1);
        assertGraveyardCount(playerB, vanguard, 1);
        assertGraveyardCount(playerA, goNuts, 1);
    }

    @Test
    public void teamworkPaidWithoutAgentMariaHillDoesNotTriggerHer() {
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Island");
        addCard(Zone.HAND, playerA, goNuts);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, maria);
        addCard(Zone.BATTLEFIELD, playerA, lion);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, vanguard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goNuts);
        setChoice(playerA, true); // Pay Teamwork 3
        setModeChoice(playerA, "1");
        setModeChoice(playerA, "2");
        addTarget(playerA, lion); // +1/+1 counter mode
        addTarget(playerA, lion); // fight mode, creature you control
        addTarget(playerA, vanguard); // fight mode, creature an opponent controls
        setChoice(playerA, lion + "^" + bear); // Teamwork creatures, total power 4
        setChoice(playerA, TestPlayer.CHOICE_SKIP); // Stop selecting teamwork creatures

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(maria, false);
        assertTapped(lion, true);
        assertTapped(bear, true);
        assertCounterCount(playerA, maria, CounterType.P1P1, 0);
        assertHandCount(playerA, 0);

        assertCounterCount(playerA, lion, CounterType.P1P1, 1);
        assertGraveyardCount(playerB, vanguard, 1);
        assertGraveyardCount(playerA, goNuts, 1);
    }

    @Test
    public void teamworkDeclinedChoosesOnlyOneModeAndDoesNotTriggerAgentMariaHill() {
        addCard(Zone.HAND, playerA, goNuts);
        addCard(Zone.BATTLEFIELD, playerA, "Forest");
        addCard(Zone.BATTLEFIELD, playerA, maria);
        addCard(Zone.BATTLEFIELD, playerA, bear);
        addCard(Zone.BATTLEFIELD, playerB, vanguard);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, goNuts);
        setChoice(playerA, false); // Don't pay Teamwork 3
        setModeChoice(playerA, "1");
        addTarget(playerA, bear);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertTapped(maria, false);
        assertCounterCount(playerA, maria, CounterType.P1P1, 0);
        assertCounterCount(playerA, bear, CounterType.P1P1, 1);
        assertPermanentCount(playerB, vanguard, 1);
        assertGraveyardCount(playerA, goNuts, 1);
    }
}
