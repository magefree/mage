package org.mage.test.cards.single.msc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * 701.15a
 * Certain spells and abilities can goad a creature. Until the next turn of the controller of that spell or ability,
 * that creature is goaded.
 * <p>
 * 603.7c
 * A delayed triggered ability that refers to a particular object still affects it even if the object changes
 * characteristics. However, if that object is no longer in the zone it’s expected to be in at the time the delayed
 * triggered ability resolves, the ability won’t affect it. (Note that if that object left that zone and then returned,
 * it’s a new object and thus won’t be affected. See rule 400.7.)
 * <p>
 * 608.2h
 * If an effect requires information from the game (such as the number of creatures on the battlefield), the answer is
 * determined only once, when the effect is applied. If the effect requires information from a specific object,
 * including the source of the ability itself, the effect uses the current information of that object if it’s in the
 * public zone it was expected to be in; if it’s no longer in that zone, or if the effect has moved it from a public
 * zone to a hidden zone, the effect uses the object’s last known information. See rule 113.7a. If an ability states
 * that an object does something, it’s the object as it exists—or as it most recently existed—that does it, not the
 * ability.
 *
 * @author DarioBarrabinoDev, JayDi85
 */
public class KangDynastyTest extends CardTestPlayerBase {

    @Test
    public void test_ChapterI_TapGoadAndDraw() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target
        checkPermanentTapped("tapped by chapter I", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", true, 1);

        // turn 2: goaded bear must attack, the only other player is playerA

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("must be goaded by playerA",
                getPermanent("Grizzly Bears", playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 1); // draw from the bear's combat damage
    }

    @Test
    public void test_ChapterI_DrawEndsOnYourNextTurn() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        // turn 2: goaded bear attacks on its own

        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter II without targets

        // turn 4: the bear is not goaded anymore and attacks by choice
        attack(4, playerB, "Grizzly Bears", playerA);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        Assert.assertFalse("goad must end on playerA's next turn",
                getPermanent("Grizzly Bears", playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20 - 2 - 2);
        assertHandCount(playerA, 1 + 1); // turn 2 combat damage + turn 3 draw step, nothing on turn 4
    }

    @Test
    public void test_ChapterI_DrawOnlyForThoseCreatures() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        // turn 2: goaded bear attacks on its own, the lion attacks by choice
        attack(2, playerB, "Silvercoat Lion", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 2 - 2);
        assertHandCount(playerA, 1); // the lion wasn't chosen, so its damage draws nothing
    }

    @Test
    public void test_ChapterI_NoDrawForCombatDamageToCreature() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        // turn 2: goaded bear attacks on its own and deals its damage to the blocker only
        block(2, playerA, "Silvercoat Lion", "Grizzly Bears");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertGraveyardCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_ChapterI_NoDrawForNoncombatDamage() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        // {T}: Prodigal Pyromancer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Pyromancer"); // 1/1

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Prodigal Pyromancer"); // chapter I target

        // turn 2: it untaps in its controller's untap step, so it can deal noncombat damage while still on the battlefield
        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: ", playerA);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1);
        assertHandCount(playerA, 0);
    }

    @Test
    public void test_ChapterI_DoubleStrike_DrawTwice() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Fencing Ace"); // 1/1, double strike

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Fencing Ace"); // chapter I target

        // turn 2: goaded creature attacks on its own and deals combat damage twice

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 - 1 - 1);
        assertHandCount(playerA, 1 + 1); // first strike damage step + regular damage step
    }

    @Test
    public void test_ChapterI_SagaLeftBattlefield_StillDraw() {
        addCustomEffect_TargetDestroy(playerA);

        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target destroy", "Kang Dynasty");

        // turn 2: goaded bear attacks on its own

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Kang Dynasty", 1);
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 1);
    }

    @Test
    public void test_ChapterI_CreatureLeftAndReturned_NoDraw() {
        addCustomEffect_TargetBlink(playerA);

        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        // the bear returns under playerA's control as a new object
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "target blink", "Grizzly Bears");

        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter II without targets

        attack(3, playerA, "Grizzly Bears", playerB);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("new object must not be goaded",
                getPermanent("Grizzly Bears", playerA).getGoadingPlayers().isEmpty());
        assertLife(playerB, 20 - 2);
        assertHandCount(playerA, 1); // turn 3 draw step only
    }

    @Test
    public void test_ChapterII_TapGoadAndDraw() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // lion 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        // turn 2: goaded bear attacks on its own

        addTarget(playerA, "Silvercoat Lion"); // chapter II target
        checkPermanentTapped("tapped by chapter II", 3, PhaseStep.POSTCOMBAT_MAIN, playerB, "Silvercoat Lion", true, 1);

        // turn 4: goaded lion attacks on its own, the bear attacks by choice
        attack(4, playerB, "Grizzly Bears", playerA);

        setStrictChooseMode(true);
        setStopAt(4, PhaseStep.END_TURN);
        execute();

        Assert.assertTrue("lion must be goaded by chapter II",
                getPermanent("Silvercoat Lion", playerB).getGoadingPlayers().contains(playerA.getId()));
        Assert.assertFalse("bear's goad from chapter I must end on turn 3",
                getPermanent("Grizzly Bears", playerB).getGoadingPlayers().contains(playerA.getId()));
        assertLife(playerA, 20 - 2 - 2 - 2);
        assertHandCount(playerA, 1 + 1 + 1); // turn 2 bear + turn 3 draw step + turn 4 lion only
    }

    @Test
    public void test_ChapterIAndII_SameCreatureInOneTurn_DrawTwice() {
        // I, II -- For each opponent, tap up to one target creature that player controls. Goad those creatures.
        // Until your next turn, whenever any of those creatures deals combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        // Volt Charge deals 3 damage to any target. Proliferate.
        addCard(Zone.HAND, playerA, "Volt Charge"); // {2}{R}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}: Add {U}", 4); // pay Kang Dynasty by islands only
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, "Grizzly Bears"); // chapter I target

        // second lore counter on the same turn, so both chapters' draws exist at the same time
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Volt Charge", playerB);
        setChoice(playerA, "Kang Dynasty"); // proliferate the lore counter
        addTarget(playerA, "Grizzly Bears"); // chapter II target, same creature

        // turn 2: goaded bear attacks on its own, both delayed triggers go to the stack
        setChoice(playerA, "Until your next turn"); // order of two identical triggers

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.END_TURN);
        execute();

        assertCounterCount(playerA, "Kang Dynasty", CounterType.LORE, 2);
        assertLife(playerB, 20 - 3);
        assertLife(playerA, 20 - 2);
        assertHandCount(playerA, 1 + 1); // one draw from each chapter
    }

    @Test
    public void test_ChapterIII_BoostLockedInAndCantBeBlocked() {
        // III -- Target creature you control gets +1/+1 until end of turn for each card in your hand
        // and can't be blocked this turn.
        addCard(Zone.HAND, playerA, "Kang Dynasty"); // {3}{U}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        //
        addCard(Zone.HAND, playerA, "Ornithopter"); // {0}, changes the hand size after chapter III
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // lion 2/2
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears"); // bear 2/2

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Kang Dynasty");
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter I without targets
        addTarget(playerA, TestPlayer.TARGET_SKIP); // chapter II without targets
        addTarget(playerA, "Silvercoat Lion"); // chapter III target

        // three cards in hand as chapter III resolves: Ornithopter + turn 3 and turn 5 draws
        waitStackResolved(5, PhaseStep.PRECOMBAT_MAIN);
        castSpell(5, PhaseStep.PRECOMBAT_MAIN, playerA, "Ornithopter");

        attack(5, playerA, "Silvercoat Lion", playerB);
        block(5, playerB, "Grizzly Bears", "Silvercoat Lion"); // must be ignored, can't be blocked

        setStrictChooseMode(true);
        setStopAt(5, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ornithopter", 1);
        assertPowerToughness(playerA, "Silvercoat Lion", 2 + 3, 2 + 3); // the boost doesn't change with the hand size
        assertLife(playerB, 20 - (2 + 3));
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertGraveyardCount(playerA, "Kang Dynasty", 1);
    }
}
