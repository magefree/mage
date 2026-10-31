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

    @Test
    public void test_EnterAttackingThatPlayerOrPlaneswalkerNotBattle_AdelineResplendentCathar() {
        // Whenever you attack, for each opponent, create a 1/1 white Human creature token
        // that's tapped and attacking that player or a planeswalker they control.
        addCard(Zone.BATTLEFIELD, playerA, "Adeline, Resplendent Cathar"); // */4, vigilance
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, attacker
        addCard(Zone.BATTLEFIELD, playerA, "Invasion of Segovia"); // battle protected by playerB, defense 4

        attack(3, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Human Token", true);
        assertCounterCount(playerA, "Invasion of Segovia", CounterType.DEFENSE, 4);
        assertLife(playerB, 20 - 2 - 1);
    }

    @Test
    public void test_GainControlAndAttackingThatPlayer_MidnightCrusaderShuttle() {
        // Whenever this Vehicle attacks, defending player faces a villainous choice - That player sacrifices a creature
        // of their choice, or you gain control of a creature of your choice that player controls until end of turn.
        // If you gain control of a creature this way, tap it, and it's attacking that player.
        // Crew 2
        addCard(Zone.BATTLEFIELD, playerA, "Midnight Crusader Shuttle"); // vehicle 3/4
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2, crew
        //
        addCard(Zone.BATTLEFIELD, playerB, "Hill Giant"); // giant 3/3

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Crew 2");
        setChoice(playerA, "Grizzly Bears"); // crew

        attack(3, playerA, "Midnight Crusader Shuttle", playerB);
        setChoice(playerB, false); // villainous choice: give control of a creature
        setChoice(playerA, "Hill Giant"); // creature to gain control

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Hill Giant", true);
        assertLife(playerB, 20 - 3 - 3);
    }

    @Test
    public void test_EnterAttackingThatPlayer_OwlbearCub() {
        // Whenever this creature attacks a player who controls eight or more lands, look at the top eight cards of
        // your library. You may put a creature card from among them onto the battlefield tapped and attacking that
        // player. Put the rest on the bottom of your library in a random order.
        addCard(Zone.BATTLEFIELD, playerA, "Owlbear Cub"); // 3/3
        removeAllCardsFromLibrary(playerA);
        addCard(Zone.LIBRARY, playerA, "Grizzly Bears"); // bear 2/2
        skipInitShuffling();
        //
        addCard(Zone.BATTLEFIELD, playerB, "Island", 8);

        attack(1, playerA, "Owlbear Cub", playerB); // no draw on the first turn
        setChoice(playerA, "Grizzly Bears"); // creature to put onto the battlefield

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Grizzly Bears", true);
        assertLife(playerB, 20 - 3 - 2);
    }

    @Test
    public void test_EnterAttackingThatPlayerOrPlaneswalker_ZaraRenegadeRecruiter() {
        // Whenever Zara attacks, look at defending player's hand. You may put a creature card from it onto the
        // battlefield under your control tapped and attacking that player or a planeswalker they control.
        // Return that creature to its owner's hand at the beginning of the next end step.
        addCard(Zone.BATTLEFIELD, playerA, "Zara, Renegade Recruiter"); // 4/3, flying
        //
        addCard(Zone.HAND, playerB, "Hill Giant"); // giant 3/3

        attack(3, playerA, "Zara, Renegade Recruiter", playerB);
        setChoice(playerA, "Hill Giant"); // creature to put onto the battlefield

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertPermanentCount(playerA, "Hill Giant", 1);
        assertTapped("Hill Giant", true);
        assertLife(playerB, 20 - 4 - 3);
    }
}
