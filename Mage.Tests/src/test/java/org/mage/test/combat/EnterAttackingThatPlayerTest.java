package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 508.4
 * If a creature is put onto the battlefield attacking, its controller chooses which defending player, planeswalker
 * a defending player controls, or battle a defending player protects it’s attacking as it enters the battlefield
 * (unless the effect that put it onto the battlefield specifies what it’s attacking). Similarly, if an effect states
 * that a creature is attacking, its controller chooses which defending player, planeswalker a defending player
 * controls, or battle a defending player protects it’s attacking (unless the effect has already specified). Such
 * creatures are “attacking” but, for the purposes of trigger events and effects, they never “attacked.” They remain
 * attacking creatures until they’re removed from combat or the combat phase ends, whichever comes first.
 *
 * @author JayDi85
 */
public class EnterAttackingThatPlayerTest extends CardTestPlayerBase {

    @Test
    public void test_EnterAttackingThatPlayer_StampedeSurfer() {
        // Whenever this creature attacks, for each opponent, you create a 2/2 green Boar creature token
        // that's tapped and attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Stampede Surfer"); // 4/4
        //
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Stampede Surfer", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Boar Token", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 4 - 2);
    }

    @Test
    public void test_EnterAttackingThatPlayer_EchoingAssault() {
        // Whenever you attack a player, choose target nontoken creature that's attacking that player.
        // Create a token that's a copy of that creature, except it's 1/1. The token enters tapped and attacking that player.
        addCard(Zone.BATTLEFIELD, playerA, "Echoing Assault");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);
        addTarget(playerA, "Grizzly Bears"); // creature to copy

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 2);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 2 - 1);
    }

    @Test
    public void test_EnterAttackingThatPlayer_CombatCalligrapher() {
        // Whenever a player attacks one of your opponents, that attacking player creates a tapped 2/1 white and black
        // Inkling creature token with flying that's attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Combat Calligrapher"); // 3/3
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Inkling Token", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3);
        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_EnterAttackingThatPlayerOrPlaneswalker_AdelineResplendentCathar() {
        // Whenever you attack, for each opponent, create a 1/1 white Human creature token
        // that's tapped and attacking that player or a planeswalker they control.
        addCard(Zone.BATTLEFIELD, playerA, "Adeline, Resplendent Cathar"); // */4, vigilance
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerB, "Jace Beleren"); // planeswalker, loyalty 3

        attack(3, playerA, "Grizzly Bears", playerB);
        addTarget(playerA, "Jace Beleren"); // defender for the Human token

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Human Token", true);
        assertCounterCount(playerB, "Jace Beleren", CounterType.LOYALTY, 3 - 1);
        assertLife(playerB, 20 - 2);
    }
}
