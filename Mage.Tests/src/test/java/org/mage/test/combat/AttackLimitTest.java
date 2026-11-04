package org.mage.test.combat;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 508.1c
 * The active player checks each creature they control to see whether it’s affected by any restrictions (effects that
 * say a creature can’t attack, or that it can’t attack unless some condition is met). If any restrictions are being
 * disobeyed, the declaration of attackers is illegal.
 * <p>
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
public class AttackLimitTest extends CardTestPlayerBase {

    @Test
    public void test_LimitOnDeclare_MirriWeatherlightDuelist() {
        // As long as Mirri is tapped, no more than one creature can attack you each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Mirri, Weatherlight Duelist", 1, true); // 3/2, tapped
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2

        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Silvercoat Lion", playerB); // can't attack

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_LimitOnDeclare_Crawlspace() {
        // No more than two creatures can attack you each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Crawlspace");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // human 2/1

        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Silvercoat Lion", playerB);
        attack(1, playerA, "Elite Vanguard", playerB); // can't attack

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2 - 2);
    }

    @Test
    public void test_LimitAndEnterAttacking_MirriWeatherlightDuelist() {
        // Any effects that say that the Angel, Demon, or Dragon creature can't attack (such as that of Propaganda
        // or if the creature has defender) affect only the declaration of attackers. They won't stop the creature
        // from entering the battlefield attacking.
        // (2020-08-07)
        //
        // Whenever Kaalia attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand
        // onto the battlefield tapped and attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Kaalia of the Vast"); // 2/2, flying
        addCard(Zone.HAND, playerA, "Serra Angel"); // angel 4/4, flying, vigilance
        //
        // As long as Mirri is tapped, no more than one creature can attack you each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Mirri, Weatherlight Duelist", 1, true); // 3/2, tapped

        attack(1, playerA, "Kaalia of the Vast", playerB);
        setChoice(playerA, true); // put a creature onto the battlefield
        setChoice(playerA, "Serra Angel"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Serra Angel", true);
        assertLife(playerB, 20 - 2 - 4);
    }

    @Test
    public void test_LimitAndEnterAttacking_JudoonEnforcers() {
        // Mobilize 1 (Whenever this creature attacks, create a tapped and attacking 1/1 red Warrior creature token.
        // Sacrifice it at the beginning of the next end step.)
        addCard(Zone.BATTLEFIELD, playerA, "Dragonback Lancer"); // 3/3, flying
        //
        // No more than one creature can attack you each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Judoon Enforcers"); // 8/8, trample

        attack(1, playerA, "Dragonback Lancer", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Warrior Token", true);
        assertLife(playerB, 20 - 3 - 1);
    }

    @Test
    public void test_LimitOnDeclare_JudoonEnforcers() {
        // No more than one creature can attack you each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Judoon Enforcers");
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2
        attack(1, playerA, "Grizzly Bears", playerB);
        attack(1, playerA, "Silvercoat Lion", playerB); // can't attack

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_LimitAndEnterAttacking_CavernsOfDespair() {
        // Whenever Kaalia attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand
        // onto the battlefield tapped and attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Kaalia of the Vast"); // 2/2, flying
        addCard(Zone.HAND, playerA, "Serra Angel"); // angel 4/4, flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        //
        // No more than two creatures can attack each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Caverns of Despair");

        attack(1, playerA, "Kaalia of the Vast", playerB);
        attack(1, playerA, "Grizzly Bears", playerB);
        setChoice(playerA, true); // put a creature onto the battlefield
        setChoice(playerA, "Serra Angel"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Serra Angel", true);
        assertLife(playerB, 20 - 2 - 2 - 4);
    }

    @Test
    public void test_LimitAndEnterAttacking_DuelingGrounds() {
        // Whenever Kaalia attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand
        // onto the battlefield tapped and attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Kaalia of the Vast"); // 2/2, flying
        addCard(Zone.HAND, playerA, "Serra Angel"); // angel 4/4, flying, vigilance
        //
        // No more than one creature can attack each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Dueling Grounds");

        attack(1, playerA, "Kaalia of the Vast", playerB);
        setChoice(playerA, true); // put a creature onto the battlefield
        setChoice(playerA, "Serra Angel"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Serra Angel", true);
        assertLife(playerB, 20 - 2 - 4);
    }

    @Test
    public void test_LimitAndEnterAttacking_SilentArbiter() {
        // Whenever Kaalia attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand
        // onto the battlefield tapped and attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Kaalia of the Vast"); // 2/2, flying
        addCard(Zone.HAND, playerA, "Serra Angel"); // angel 4/4, flying, vigilance
        //
        // No more than one creature can attack each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Silent Arbiter");

        attack(1, playerA, "Kaalia of the Vast", playerB);
        setChoice(playerA, true); // put a creature onto the battlefield
        setChoice(playerA, "Serra Angel"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Serra Angel", true);
        assertLife(playerB, 20 - 2 - 4);
    }

    @Test
    public void test_LimitAndEnterAttacking_Crawlspace() {
        // Whenever Kaalia attacks an opponent, you may put an Angel, Demon, or Dragon creature card from your hand
        // onto the battlefield tapped and attacking that opponent.
        addCard(Zone.BATTLEFIELD, playerA, "Kaalia of the Vast"); // 2/2, flying
        addCard(Zone.HAND, playerA, "Serra Angel"); // angel 4/4, flying, vigilance
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        //
        // No more than two creatures can attack you each combat.
        addCard(Zone.BATTLEFIELD, playerB, "Crawlspace");

        attack(1, playerA, "Kaalia of the Vast", playerB);
        attack(1, playerA, "Grizzly Bears", playerB);
        setChoice(playerA, true); // put a creature onto the battlefield
        setChoice(playerA, "Serra Angel"); // creature to put

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Serra Angel", true);
        assertLife(playerB, 20 - 2 - 2 - 4);
    }

    @Test
    public void test_LimitAndEnterAttacking_TheEternalWanderer() {
        // Mobilize 1 (Whenever this creature attacks, create a tapped and attacking 1/1 red Warrior creature token.
        // Sacrifice it at the beginning of the next end step.)
        addCard(Zone.BATTLEFIELD, playerA, "Dragonback Lancer"); // 3/3, flying
        //
        // No more than one creature can attack The Eternal Wanderer each combat.
        addCard(Zone.BATTLEFIELD, playerB, "The Eternal Wanderer"); // planeswalker, loyalty 5

        attack(1, playerA, "Dragonback Lancer", "The Eternal Wanderer");
        addTarget(playerA, "The Eternal Wanderer"); // defender for the Warrior token

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Warrior Token", true);
        assertCounterCount(playerB, "The Eternal Wanderer", CounterType.LOYALTY, 5 - 3 - 1);
    }

    @Test
    public void test_LimitAndEnterAttacking_TomikOrzhovLawmage() {
        // Mobilize 1 (Whenever this creature attacks, create a tapped and attacking 1/1 red Warrior creature token.
        // Sacrifice it at the beginning of the next end step.)
        addCard(Zone.BATTLEFIELD, playerA, "Dragonback Lancer"); // 3/3, flying
        //
        // Planeswalkers you control have "No more than one creature can attack this planeswalker each combat."
        addCard(Zone.BATTLEFIELD, playerB, "Tomik, Orzhov Lawmage"); // 2/3, flying
        addCard(Zone.BATTLEFIELD, playerB, "Ajani Goldmane"); // planeswalker, loyalty 4

        attack(1, playerA, "Dragonback Lancer", "Ajani Goldmane");
        addTarget(playerA, "Ajani Goldmane"); // defender for the Warrior token

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        assertTapped("Warrior Token", true);
        assertGraveyardCount(playerB, "Ajani Goldmane", 1); // 4 - 3 - 1 loyalty
    }
}
