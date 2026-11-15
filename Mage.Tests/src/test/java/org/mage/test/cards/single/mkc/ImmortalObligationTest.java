package org.mage.test.cards.single.mkc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.15b
 * Goaded is a designation a permanent can have. A goaded creature attacks each combat if able and
 * attacks a player other than the controller of the permanent, spell, or ability that caused it to
 * be goaded if able. Goaded is neither an ability nor part of the permanent’s copiable values.
 *
 * @author brahle, JayDi85
 */
public class ImmortalObligationTest extends CardTestPlayerBase {

    @Test
    public void test_ReturnUnderOwnerControl_CantAttackYou() {
        // Return target creature card from an opponent's graveyard to the battlefield under their control with a
        // duty counter on it. For as long as that creature has a duty counter on it, it is goaded, can't attack you
        // or a permanent you control, and can't block creatures you control.
        addCard(Zone.HAND, playerA, "Immortal Obligation"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        // turn 2: a goaded creature attacks if able, but the only player it could attack is the caster
        checkMayAttackDefender("can't attack you", 2, playerB, "Centaur Courser", playerA, false);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerB, "Centaur Courser", 1);
        assertCounterCount(playerB, "Centaur Courser", CounterType.DUTY, 1);
        assertTapped("Centaur Courser", false);
        assertLife(playerA, 20);
    }

    @Test
    public void test_CantAttackPermanentYouControl() {
        // For as long as that creature has a duty counter on it, it is goaded, can't attack you or a permanent you
        // control, and can't block creatures you control.
        addCard(Zone.HAND, playerA, "Immortal Obligation"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Ajani Goldmane"); // planeswalker, loyalty 4
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        // turn 2: a goaded creature attacks if able, but it can't attack the caster or the caster's planeswalker

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertTapped("Centaur Courser", false);
        assertCounterCount(playerA, "Ajani Goldmane", CounterType.LOYALTY, 4);
        assertLife(playerA, 20);
    }

    @Test
    public void test_CantBlockCreaturesYouControl() {
        // For as long as that creature has a duty counter on it, it is goaded, can't attack you or a permanent you
        // control, and can't block creatures you control.
        addCard(Zone.HAND, playerA, "Immortal Obligation"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser"); // 3/3

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        attack(1, playerA, "Grizzly Bears");
        block(1, playerB, "Centaur Courser", "Grizzly Bears"); // must be ignored as an illegal block

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_DutyCounterRemoved_NotGoadedAndCanAttackYou() {
        // Promise of Loyalty ruling (same counter-bound restriction):
        // If the vow counter is removed from the creature, it can attack as normal.
        // (2021-04-16)
        addCard(Zone.HAND, playerA, "Immortal Obligation"); // {1}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        //
        addCard(Zone.GRAVEYARD, playerB, "Centaur Courser"); // 3/3
        addCard(Zone.BATTLEFIELD, playerB, "Vampire Hexmage"); // sacrifice: remove all counters from target permanent

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Immortal Obligation", "Centaur Courser");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sacrifice {this}: Remove all counters", "Centaur Courser");
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        // turn 2: no restriction, and no goad to force the attack (no attack command, so it stays home)
        checkMayAttackDefender("can attack you", 2, playerB, "Centaur Courser", playerA, true);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerB, "Centaur Courser", CounterType.DUTY, 0);
        assertTapped("Centaur Courser", false);
        assertLife(playerA, 20);
    }
}
