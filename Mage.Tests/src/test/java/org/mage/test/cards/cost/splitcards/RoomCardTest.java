package org.mage.test.cards.cost.splitcards;

import mage.constants.CardType;
import mage.constants.EmptyNames;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author oscscull
 */
public class RoomCardTest extends CardTestPlayerBase {

    // Bottomless pool is cast. It unlocks, and the trigger to return a creature
    // should bounce one of two grizzly bears.
    @Test
    public void testBottomlessPoolETB() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);

        checkPlayableAbility("playerA can cast Bottomless Pool", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "Cast Bottomless Pool", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");

        // Target one of playerB's "Grizzly Bears" with the return effect.
        addTarget(playerA, "Grizzly Bears");
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Assertions:
        // Verify that one "Grizzly Bears" is still on playerB's battlefield.
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        // Verify that one "Grizzly Bears" has been returned to playerB's hand.
        assertHandCount(playerB, "Grizzly Bears", 1);
        // Verify that "Bottomless Pool" is on playerA's battlefield.
        assertPermanentCount(playerA, "Bottomless Pool", 1);
        // Verify that "Bottomless Pool" is an Enchantment.
        assertType("Bottomless Pool", CardType.ENCHANTMENT, true);
        // Verify that "Bottomless Pool" has the Room subtype.
        assertSubtype("Bottomless Pool", SubType.ROOM);
    }

    // Locker room is cast. It enters, and gives a coastal piracy effect that
    // triggers on damage.
    @Test
    public void testLockerRoomCombatDamageTrigger() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);

        // Cards to be drawn
        addCard(Zone.LIBRARY, playerA, "Plains", 2); // Expected cards to be drawn

        // 2 attackers
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Locker Room");
        attack(1, playerA, "Memnite");
        attack(1, playerA, "Memnite");
        // After combat damage, Memnites dealt combat damage to playerB (1 damage * 2).
        // 2 Locker Room triggers should go on the stack.
        checkStackSize("Locker Room trigger must be on the stack", 1, PhaseStep.COMBAT_DAMAGE, playerA, 2);
        checkStackObject("Locker Room trigger must be correct", 1, PhaseStep.COMBAT_DAMAGE, playerA,
                "Whenever a creature you control deals combat damage to an opponent, draw a card.", 2);

        // Stop at the end of the combat phase to check triggers.
        setStopAt(1, PhaseStep.END_COMBAT);
        execute();

        // Assertions after the first execute() (Locker Room and creatures are on
        // battlefield, combat resolved):
        assertPermanentCount(playerA, "Locker Room", 1);
        assertType("Locker Room", CardType.ENCHANTMENT, true);
        assertSubtype("Locker Room", SubType.ROOM);
        assertPermanentCount(playerA, "Memnite", 2);

        setStrictChooseMode(true);
        execute(); // Resolve the Locker Room trigger.

        // PlayerA should have drawn two plains cards
        assertHandCount(playerA, "Plains", 2);
    }

    @Test
    public void testBottomlessPoolUnlock() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);

        // 2 creatures owned by player A
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPlayableAbility("playerA can unlock Bottomless Pool", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{U}: Unlock the left half.", true);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{U}: Unlock the left half.");
        addTarget(playerA, "Memnite");
        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that one "Memnite" is still on playerA's battlefield.
        assertPermanentCount(playerA, "Memnite", 1);
        // Verify that one "Memnite" has been returned to playerA's hand.
        assertHandCount(playerA, "Memnite", 1);
        // Verify that "Bottomless Pool // Locker Room" is on playerA's battlefield.
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
        // Verify that "Bottomless Pool // Locker Room" is an Enchantment.
        assertType("Bottomless Pool // Locker Room", CardType.ENCHANTMENT, true);
        // Verify that "Bottomless Pool // Locker Room" has the Room subtype.
        assertSubtype("Bottomless Pool // Locker Room", SubType.ROOM);
    }

    @Test
    public void testFlickerNameAndManaCost() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Felidar Guardian");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 4);

        // creatures owned by player A
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        // resolve spell cast
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // unlock and trigger bounce on Memnite
        addTarget(playerA, "Memnite");
        // resolve bounce
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Felidar Guardian");
        // resolve spell cast
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // etb and flicker on Bottomless Pool
        setChoice(playerA, "Yes");
        addTarget(playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that one "Memnite" has been returned to playerA's hand.
        assertHandCount(playerA, "Memnite", 1);
        // Verify that a room with no name is on playerA's battlefield.
        assertPermanentCount(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 1);
        // Verify that "Felidar Guardian" is on playerA's battlefield.
        assertPermanentCount(playerA, "Felidar Guardian", 1);
        // Verify that a room with no name is an Enchantment.
        assertType(EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), CardType.ENCHANTMENT, true);
        // Verify that a room with no name has the Room subtype.
        assertSubtype(EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), SubType.ROOM);
    }

    @Test
    public void testFlickerCanBeUnlockedAgain() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Felidar Guardian");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // creatures owned by player A
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Black Knight", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        // resolve spell cast
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // unlock and trigger bounce on Memnite
        addTarget(playerA, "Memnite");
        // resolve bounce
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Felidar Guardian");
        // resolve spell cast
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // etb and flicker on Bottomless Pool
        setChoice(playerA, "Yes");
        addTarget(playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // can unlock again
        checkPlayableAbility("playerA can unlock Bottomless Pool", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{U}: Unlock the left half.", true);
        // unlock again
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{U}: Unlock the left half.");
        addTarget(playerA, "Black Knight");
        setStopAt(1, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that one "Memnite" has been returned to playerA's hand.
        assertHandCount(playerA, "Memnite", 1);
        // Verify that one "Black Knight" has been returned to playerA's hand.
        assertHandCount(playerA, "Black Knight", 1);
        // Verify that "Bottomless Pool" is on playerA's battlefield.
        assertPermanentCount(playerA, "Bottomless Pool", 1);
        // Verify that "Felidar Guardian" is on playerA's battlefield.
        assertPermanentCount(playerA, "Felidar Guardian", 1);
    }

    @Test
    public void testEerie() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Erratic Apparition", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        // resolve spell cast
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, "When you unlock"); // x2 triggers
        // don't bounce anything
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // resolve ability
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // unlock other side
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that "Bottomless Pool // Locker Room" is on playerA's battlefield.
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
        // Verify that "Erratic Apparition" is on playerA's battlefield.
        assertPermanentCount(playerA, "Erratic Apparition", 1);
        // Verify that "Erratic Apparition" has been pumped twice (etb + fully unlock)
        assertPowerToughness(playerA, "Erratic Apparition", 3, 5);
    }

    @Test
    public void testCopyOnStack() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "See Double");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        // Copy spell on the stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "See Double");
        setModeChoice(playerA, "1");
        addTarget(playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 3);
        addTarget(playerA, "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, "Ornithopter");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that one "Memnite" has been returned to playerA's hand.
        assertHandCount(playerA, "Memnite", 1);
        // Verify that one "Ornithopter" has been returned to playerA's hand.
        assertHandCount(playerA, "Ornithopter", 1);
        // Verify that 2 "Bottomless Pool" are on playerA's battlefield.
        assertPermanentCount(playerA, "Bottomless Pool", 2);
    }

    @Test
    public void testCopyOnBattlefield() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Clever Impersonator");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Ornithopter", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // Copy spell on the battlefield
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Clever Impersonator");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        setChoice(playerA, "Yes");
        setChoice(playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{U}: Unlock the left half.");
        addTarget(playerA, "Ornithopter");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that one "Memnite" has been returned to playerA's hand (from original
        // unlock).
        assertHandCount(playerA, "Memnite", 1);
        // Verify that "Ornithopter" has been returned to playerA's hand (from clone
        // unlock).
        assertHandCount(playerA, "Ornithopter", 1);
        // Verify that the original "Bottomless Pool" is on playerA's battlefield, and a
        // clone.
        assertPermanentCount(playerA, "Bottomless Pool", 2);
    }

    @Test
    public void testNameMatchOnStack() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.

        // Mindreaver
        // {U}{U}
        // Creature — Human Wizard
        // Heroic — Whenever you cast a spell that targets this creature, exile the top
        // three cards of target player’s library.
        // {U}{U}, Sacrifice this creature: Counter target spell with the same name as a
        // card exiled with this creature.

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Twiddle");
        addCard(Zone.BATTLEFIELD, playerA, "Mindreaver", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 6);
        addCard(Zone.LIBRARY, playerA, "Bottomless Pool // Locker Room", 1);
        addCard(Zone.LIBRARY, playerA, "Plains", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Twiddle");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        // tap or untap target permanent
        addTarget(playerA, "Mindreaver");
        // tap that permanent?
        setChoice(playerA, "No");
        // Whenever you cast a spell that targets this creature, exile the top
        // three cards of target player’s library.
        addTarget(playerA, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{U}{U}, Sacrifice {this}:");
        addTarget(playerA, "Bottomless Pool");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void testNameMatchOnFieldFromLocked() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        //
        // Opalescence
        // {2}{W}{W}
        // Enchantment
        // Each other non-Aura enchantment is a creature in addition to its other types
        // and has base power and base toughness each equal to its mana value.
        //
        // Glorious Anthem
        // {1}{W}{W}
        // Enchantment
        // Creatures you control get +1/+1.
        //
        // Cackling Counterpart
        // {1}{U}{U}
        // Instant
        // Create a token that's a copy of target creature you control.
        //
        // Bile Blight
        // {B}{B}
        // Instant
        // Target creature and all other creatures with the same name as that creature
        // get -3/-3 until end of turn.

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room", 4);
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.HAND, playerA, "Bile Blight");
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 17);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence");

        // Cast Bottomless Pool (unlocked left half)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Locker Room (unlocked right half)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Bottomless Pool then unlock Locker Room (both halves unlocked)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Create a fully locked room using Cackling Counterpart
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart");
        addTarget(playerA, "Bottomless Pool // Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Bile Blight targeting the fully locked room
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bile Blight");
        addTarget(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand());

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // The fully locked room should be affected by Bile Blight (-3/-3)
        // Since it's a 0/0 creature (mana value 0) +1/+1 from anthem, it becomes 1/1,
        // then -2/-2 after Bile Blight (dies)
        assertPermanentCount(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 0);
        // Token, so nothing should be in grave
        assertGraveyardCount(playerA, "Bottomless Pool // Locker Room", 0);

        // Other rooms should NOT be affected by Bile Blight since they have different
        // names
        // Bottomless Pool: 1/1 base + 1/1 from anthem = 2/2
        assertPowerToughness(playerA, "Bottomless Pool", 2, 2);
        // Locker Room: 5/5 base + 1/1 from anthem = 6/6
        assertPowerToughness(playerA, "Locker Room", 6, 6);
        // Bottomless Pool // Locker Room: 6/6 base + 1/1 from anthem = 7/7
        assertPowerToughness(playerA, "Bottomless Pool // Locker Room", 7, 7);

        // Verify remaining rooms are still on battlefield
        assertPermanentCount(playerA, "Bottomless Pool", 1);
        assertPermanentCount(playerA, "Locker Room", 1);
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
    }

    @Test
    public void testNameMatchOnFieldFromHalf() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        //
        // Opalescence
        // {2}{W}{W}
        // Enchantment
        // Each other non-Aura enchantment is a creature in addition to its other types
        // and has base power and base toughness each equal to its mana value.
        //
        // Glorious Anthem
        // {1}{W}{W}
        // Enchantment
        // Creatures you control get +1/+1.
        //
        // Cackling Counterpart
        // {1}{U}{U}
        // Instant
        // Create a token that's a copy of target creature you control.
        //
        // Bile Blight
        // {B}{B}
        // Instant
        // Target creature and all other creatures with the same name as that creature
        // get -3/-3 until end of turn.

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room", 4);
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.HAND, playerA, "Bile Blight");
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 17);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence");

        // Cast Bottomless Pool (unlocked left half)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Locker Room (unlocked right half)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Bottomless Pool then unlock Locker Room (both halves unlocked)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Create a fully locked room using Cackling Counterpart
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart");
        addTarget(playerA, "Bottomless Pool // Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Bile Blight targeting the half locked room
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bile Blight");
        addTarget(playerA, "Locker Room");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Locker Room and Bottomless Pool // Locker Room should both be affected by
        // Bile Blight
        // since they share the "Locker Room" name component

        // Locker Room: 5/5 base + 1/1 from anthem - 3/3 from Bile Blight = 3/3
        assertPowerToughness(playerA, "Locker Room", 3, 3);
        // Bottomless Pool // Locker Room: 6/6 base + 1/1 from anthem - 3/3 from Bile
        // Blight = 4/4
        assertPowerToughness(playerA, "Bottomless Pool // Locker Room", 4, 4);

        // Other rooms should NOT be affected
        // Bottomless Pool: 1/1 base + 1/1 from anthem = 2/2 (unaffected)
        assertPowerToughness(playerA, "Bottomless Pool", 2, 2);
        // Fully locked room: 0/0 base + 1/1 from anthem = 1/1 (unaffected)
        assertPowerToughness(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 1, 1);

        // Verify all rooms are still on battlefield
        assertPermanentCount(playerA, "Bottomless Pool", 1);
        assertPermanentCount(playerA, "Locker Room", 1);
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
        assertPermanentCount(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 1);
    }

    @Test
    public void testNameMatchOnFieldFromUnlocked() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        //
        // Opalescence
        // {2}{W}{W}
        // Enchantment
        // Each other non-Aura enchantment is a creature in addition to its other types
        // and has base power and base toughness each equal to its mana value.
        //
        // Glorious Anthem
        // {1}{W}{W}
        // Enchantment
        // Creatures you control get +1/+1.
        //
        // Cackling Counterpart
        // {1}{U}{U}
        // Instant
        // Create a token that's a copy of target creature you control.
        //
        // Bile Blight
        // {B}{B}
        // Instant
        // Target creature and all other creatures with the same name as that creature
        // get -3/-3 until end of turn.

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room", 4);
        addCard(Zone.HAND, playerA, "Cackling Counterpart");
        addCard(Zone.HAND, playerA, "Bile Blight");
        addCard(Zone.BATTLEFIELD, playerA, "Underground Sea", 17);
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem");
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence");

        // Cast Bottomless Pool (unlocked left half)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Locker Room (unlocked right half)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Bottomless Pool then unlock Locker Room (both halves unlocked)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Create a fully locked room using Cackling Counterpart
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cackling Counterpart");
        addTarget(playerA, "Bottomless Pool // Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Bile Blight targeting the fully locked room
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bile Blight");
        addTarget(playerA, "Bottomless Pool // Locker Room");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // All rooms except the fully locked room should be affected by Bile Blight
        // since they all share name components with "Bottomless Pool // Locker Room"

        // Bottomless Pool: 1/1 base + 1/1 from anthem - 3/3 from Bile Blight = -1/-1
        // (dies)
        assertPermanentCount(playerA, "Bottomless Pool", 0);
        assertGraveyardCount(playerA, "Bottomless Pool // Locker Room", 1);

        // Locker Room: 5/5 base + 1/1 from anthem - 3/3 from Bile Blight = 3/3
        assertPowerToughness(playerA, "Locker Room", 3, 3);

        // Bottomless Pool // Locker Room: 6/6 base + 1/1 from anthem - 3/3 from Bile
        // Blight = 4/4
        assertPowerToughness(playerA, "Bottomless Pool // Locker Room", 4, 4);

        // Fully locked room should NOT be affected (different name)
        // Fully locked room: 0/0 base + 1/1 from anthem = 1/1 (unaffected)
        assertPowerToughness(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 1, 1);

        // Verify remaining rooms are still on battlefield
        assertPermanentCount(playerA, "Locker Room", 1);
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
        assertPermanentCount(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 1);
    }

    @Test
    public void testCounterspellThenReanimate() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Counterspell");
        addCard(Zone.HAND, playerA, "Campus Renovation");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // Target creature for potential bounce (should not be bounced)
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 1);

        // Cast Bottomless Pool
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");

        // Counter it while on stack
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Counterspell");
        addTarget(playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Use Campus Renovation to return it from graveyard
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Campus Renovation");
        addTarget(playerA, "Bottomless Pool // Locker Room");

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Assertions:
        // Verify that "Grizzly Bears" is still on playerB's battlefield (not bounced)
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        // Verify that "Grizzly Bears" is not in playerB's hand
        assertHandCount(playerB, "Grizzly Bears", 0);
        // Verify that a room with no name is on playerA's battlefield
        assertPermanentCount(playerA, EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), 1);
        // Verify that the nameless room is an Enchantment
        assertType(EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), CardType.ENCHANTMENT, true);
        // Verify that the nameless room has the Room subtype
        assertSubtype(EmptyNames.FULLY_LOCKED_ROOM.getTestCommand(), SubType.ROOM);
        // Verify that Campus Renovation is in graveyard
        assertGraveyardCount(playerA, "Campus Renovation", 1);
        // Verify that Counterspell is in graveyard
        assertGraveyardCount(playerA, "Counterspell", 1);
    }

    @Test
    public void testPithingNeedleActivatedAbility() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        //
        // Opalescence
        // {2}{W}{W}
        // Enchantment
        // Each other non-Aura enchantment is a creature in addition to its other types
        // and has base power and base toughness each equal to its mana value.
        //
        // Diviner's Wand
        // {3}
        // Kindred Artifact — Wizard Equipment
        // Equipped creature has "Whenever you draw a card, this creature gets +1/+1
        // and gains flying until end of turn" and "{4}: Draw a card."
        // Whenever a Wizard creature enters, you may attach this Equipment to it.
        // Equip {3}
        //
        // Pithing Needle
        // {1}
        // Artifact
        // As Pithing Needle enters, choose a card name.
        // Activated abilities of sources with the chosen name can't be activated.

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Pithing Needle");
        addCard(Zone.BATTLEFIELD, playerA, "Opalescence");
        addCard(Zone.BATTLEFIELD, playerA, "Diviner's Wand");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 20);

        // Cast Bottomless Pool (unlocked left half only)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Equip Diviner's Wand
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {3}");
        addTarget(playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Pithing Needle naming the locked side
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Pithing Needle");
        setChoice(playerA, "Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Validate that the room can activate the gained ability
        checkPlayableAbility("Room can use Diviner's Wand ability", 1, PhaseStep.PRECOMBAT_MAIN, playerA,
                "{4}: Draw a card.", true);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Unlock the other side
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Validate that you can no longer activate the ability
        checkPlayableAbility("Room cannot use Diviner's Wand ability after unlock", 1, PhaseStep.PRECOMBAT_MAIN,
                playerA, "{4}: Draw a card.", false);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Verify the room is now fully unlocked
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
    }

    // Test converting one permanent into one room, then another (the room halves
    // should STAY UNLOCKED on the appropriate side!)
    @Test
    public void testUnlockingPermanentMakeCopyOfOtherRoom() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.
        //
        // Surgical Suite {1}{W}
        // When you unlock this door, return target creature card with mana value 3 or
        // less from your graveyard to the battlefield.
        // Hospital Room {3}{W}
        // Whenever you attack, put a +1/+1 counter on target attacking creature.
        //
        // Mirage Mirror {3}
        // {3}: Mirage Mirror becomes a copy of target artifact, creature, enchantment,
        // or
        // land until end of turn.

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.HAND, playerA, "Surgical Suite // Hospital Room");
        addCard(Zone.BATTLEFIELD, playerA, "Mirage Mirror");
        addCard(Zone.BATTLEFIELD, playerA, "Tundra", 20);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        // Cast Bottomless Pool (unlocked left half only)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Surgical Suite (unlocked left half only)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Surgical Suite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: {this} becomes a copy");
        addTarget(playerA, "Bottomless Pool // Locker Room");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");

        setStopAt(3, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}: {this} becomes a copy");
        addTarget(playerA, "Surgical Suite");

        attack(3, playerA, "Memnite");
        addTarget(playerA, "Memnite");
        setStopAt(3, PhaseStep.END_TURN);

        setStrictChooseMode(true);
        execute();

        // Verify unlocked Bottomless pool
        assertPermanentCount(playerA, "Bottomless Pool // Locker Room", 1);
        // Verify unlocked Surgical Suite
        assertPermanentCount(playerA, "Surgical Suite", 1);
        // Verify mirage mirror is Hospital Room
        assertPermanentCount(playerA, "Hospital Room", 1);
        // Memnite got a buff
        assertPowerToughness(playerA, "Memnite", 2, 2);
    }

    @Test
    public void testSakashimaCopiesRoomCard() {
        skipInitShuffling();
        // Bottomless Pool {U} When you unlock this door, return up to one target
        // creature to its owner's hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat
        // damage to a player, draw a card.

        // Sakashima the Impostor {2}{U}{U}
        // Legendary Creature — Human Rogue
        // You may have Sakashima the Impostor enter the battlefield as a copy of any
        // creature on the battlefield,
        // except its name is Sakashima the Impostor, it's legendary in addition to its
        // other types,
        // and it has "{2}{U}{U}: Return Sakashima the Impostor to its owner's hand at
        // the beginning of the next end step."

        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 10);

        addCard(Zone.HAND, playerB, "Sakashima the Impostor");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 10);

        // Cast Bottomless Pool (unlocked left half only)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // Cast Sakashima copying the room
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Sakashima the Impostor");
        setChoice(playerB, "Yes"); // Choose to copy
        waitStackResolved(2, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{U}: Unlock the right half.");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        setStopAt(2, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        // Verify Sakashima entered and is copying the room
        assertPermanentCount(playerB, "Sakashima the Impostor", 1);
    }
}