package org.mage.test.cards.single.hob;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class ElrondMoonReaderTest extends CardTestPlayerBase {

    /**
     * Elrond, Moon-Reader {2}{U}
     * Legendary Creature - Elf Noble 3/3
     * Whenever you activate an ability of a creature, draw a card. This ability triggers only once each turn.
     * {5}{U}{U}: Exile up to two other target nonland permanents you control. Return those cards to the battlefield
     * under their owner's control at the beginning of the next end step.
     */
    private static final String elrond = "Elrond, Moon-Reader";

    @Test
    public void test_ActivateAbilityOfCreature_Draw() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1);
        assertHandCount(playerA, "Island", 1);
    }

    @Test
    public void test_ManaAbilityOfCreature_Draw() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // {T}: Add {G}

        // An activated ability of a creature that's also a mana ability (such as "{T}: Add {G}") can cause
        // Elrond's first ability to trigger.
        // (2026-06-29)
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Llanowar Elves", true);
        assertHandCount(playerA, "Island", 1);
    }

    @Test
    public void test_OnlyOnceEachTurn() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.LIBRARY, playerA, "Island", 3); // cards to draw
        //
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves"); // {T}: Add {G}
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);
        checkHandCardCount("one draw on turn 1", 1, PhaseStep.END_TURN, playerA, "Island", 1);

        // turn 3: triggers again
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: {this} deals 1 damage", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertLife(playerB, 20 - 1 - 1);
        assertHandCount(playerA, "Island", 1 + 1 + 1); // turn 1 trigger + turn 3 draw step + turn 3 trigger
    }

    @Test
    public void test_LandAndNoncreatureAbilities_NoTrigger() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1); // {T}: Add {G}
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // {T}: Add {C}

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {G}");
        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {C}");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertTapped("Forest", true);
        assertTapped("Mind Stone", true);
        assertHandCount(playerA, "Island", 0);
    }

    @Test
    public void test_OpponentActivates_NoTrigger() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer"); // {T}: 1 damage to any target

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: {this} deals 1 damage", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
        assertHandCount(playerA, "Island", 0);
    }

    @Test
    public void test_AbilityOfCreatureCardInHand_NoTrigger() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.LIBRARY, playerA, "Island", 2); // cards to draw
        //
        addCard(Zone.HAND, playerA, "Street Wraith"); // Cycling - Pay 2 life

        // a creature card in hand is not a creature, so cycling doesn't trigger
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cycling");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Street Wraith", 1);
        assertHandCount(playerA, "Island", 1); // cycling only
    }

    @Test
    public void test_Flicker_ReturnNewObjects() {
        skipInitShuffling();
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        addCard(Zone.LIBRARY, playerA, "Island"); // card to draw
        //
        addCard(Zone.BATTLEFIELD, playerA, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mind Stone"); // {T}: Add {C}

        addCounters(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Grizzly Bears", CounterType.P1P1, 1);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{U}{U}: Exile");
        addTarget(playerA, "Grizzly Bears^Mind Stone"); // up to two other nonland permanents
        checkPermanentCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Grizzly Bears", 0);
        checkPermanentCount("exiled", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mind Stone", 0);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        // After each permanent returns to the battlefield, it will be a new object with no connection to the
        // permanent that was exiled. It won't be in combat or have any additional abilities it may have had when it
        // was exiled. Any counters on it cease to exist; Auras attached to it are put into their owner's graveyards;
        // and any Equipment will become unattached.
        // (2026-06-29)
        assertPermanentCount(playerA, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Mind Stone", 1);
        assertCounterCount(playerA, "Grizzly Bears", CounterType.P1P1, 0);
        assertHandCount(playerA, "Island", 1); // the flicker is an ability of Elrond itself
    }

    @Test
    public void test_Flicker_TokenDoesNotReturn() {
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);
        //
        addCard(Zone.HAND, playerA, "Raise the Alarm"); // {1}{W}, create two 1/1 Soldier tokens
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Raise the Alarm");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{U}{U}: Exile");
        addTarget(playerA, "Soldier Token"); // up to two other nonland permanents
        addTarget(playerA, TestPlayer.TARGET_SKIP);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.UPKEEP);
        execute();

        // If a token is exiled this way, it will cease to exist and will not return to the battlefield.
        // (2026-06-29)
        assertPermanentCount(playerA, "Soldier Token", 2 - 1);
    }

    @Test
    public void test_Flicker_OnlyOtherPermanents() {
        addCard(Zone.BATTLEFIELD, playerA, elrond);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{U}{U}: Exile");
        addTarget(playerA, elrond); // Elrond itself can't be chosen

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            Assert.assertTrue(e.getMessage(), e.getMessage().startsWith(
                    "PlayerA - Targets list was setup by addTarget with [Elrond, Moon-Reader], but not used"));
        }
    }
}
