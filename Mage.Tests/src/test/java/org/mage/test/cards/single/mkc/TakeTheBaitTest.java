package org.mage.test.cards.single.mkc;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author matoro
 */
public class TakeTheBaitTest extends CardTestPlayerBase {

    /*
    Take the Bait
    {2}{R}{W}
    Instant
    Cast this spell only during an opponent's turn and only during combat.
    Prevent all combat damage that would be dealt to you and planeswalkers you control this turn. Untap all attacking creatures and goad them. After this phase, there is an additional combat phase.
    */

    @Test
    public void testTakeTheBait() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest Bear");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Jace, Arcane Strategist");
        addCard(Zone.HAND, playerB, "Take the Bait");

        attack(1, playerA, "Balduvian Bears", playerB);
        attack(1, playerA, "Grizzly Bears", "Jace, Arcane Strategist");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Take the Bait");

        setStopAt(1, PhaseStep.COMBAT_DAMAGE);
        execute();

        assertTapped("Balduvian Bears", false);
        assertTapped("Grizzly Bears", false);
        assertTapped("Forest Bear", false);
        assertLife(playerA, 20);
        assertLife(playerB, 20);
        assertCounterCount(playerB, "Jace, Arcane Strategist", CounterType.LOYALTY, 4);

        Assert.assertEquals(Stream.of(playerB.getId()).collect(Collectors.toSet()), getPermanent("Balduvian Bears").getGoadingPlayers());
        Assert.assertEquals(Stream.of(playerB.getId()).collect(Collectors.toSet()), getPermanent("Grizzly Bears").getGoadingPlayers());
        Assert.assertEquals(Collections.emptySet(), getPermanent("Forest Bear").getGoadingPlayers());
    }

    @Test
    public void testTakeTheBait2() {
        setStrictChooseMode(true);

        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerA, "Forest Bear");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Jace, Arcane Strategist");
        addCard(Zone.HAND, playerB, "Take the Bait");

        attack(1, playerA, "Balduvian Bears", playerB);
        attack(1, playerA, "Grizzly Bears", "Jace, Arcane Strategist");
        castSpell(1, PhaseStep.DECLARE_BLOCKERS, playerB, "Take the Bait");

        addTarget(playerA, playerB); // for Balduvian Bears
        addTarget(playerA, "Jace, Arcane Strategist"); // for Grizzly Bears
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertTapped("Balduvian Bears", true);
        assertTapped("Grizzly Bears", true);
        assertTapped("Forest Bear", false);
        assertLife(playerB, 20);
        assertCounterCount(playerB, "Jace, Arcane Strategist", CounterType.LOYALTY, 4);

        // still goaded
        Assert.assertEquals(Stream.of(playerB.getId()).collect(Collectors.toSet()), getPermanent("Balduvian Bears").getGoadingPlayers());
        Assert.assertEquals(Stream.of(playerB.getId()).collect(Collectors.toSet()), getPermanent("Grizzly Bears").getGoadingPlayers());
        Assert.assertEquals(Collections.emptySet(), getPermanent("Forest Bear").getGoadingPlayers());
    }
}
