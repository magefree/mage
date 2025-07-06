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
    @Test
    public void testETB() {
        // Bottomless Pool {U} When you unlock this door, return up to one target creature to its owner’s hand.
        // Locker Room {4}{U} Whenever one or more creatures you control deal combat damage to a player, draw a card.
        addCard(Zone.HAND, playerA, "Bottomless Pool // Locker Room");
        addCard(Zone.BATTLEFIELD, playerA, "Island", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears", 2);
        checkPlayableAbility("one land", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Bottomless Pool", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Bottomless Pool");
        addTarget(playerA, "Grizzly Bears");
        execute();
        assertPermanentCount(playerB, "Grizzly Bears", 1);
        assertHandCount(playerB, "Grizzly Bears", 1);
        assertPermanentCount(playerA, "Bottomless Pool", 1);
        assertType("Bottomless Pool", CardType.ENCHANTMENT, true);
        assertSubtype("Bottomless Pool", SubType.ROOM);
    }
}
