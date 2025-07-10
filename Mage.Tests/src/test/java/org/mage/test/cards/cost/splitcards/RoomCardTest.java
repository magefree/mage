package org.mage.test.cards.cost.splitcards;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import org.junit.Test;
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
                "{U}: Unlock the left half. <i>(Activate only as a sorcery, and only if the left half is locked.)</i>");
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
}
