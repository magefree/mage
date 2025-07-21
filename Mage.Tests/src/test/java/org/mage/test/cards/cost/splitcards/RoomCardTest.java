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
}
