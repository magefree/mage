
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author goblin
 */
public class FlameheartWerewolfTest extends CardTestPlayerBase {

    /**
     * https://github.com/magefree/mage/issues/2816
     */
    @Test
    public void testBlockingKalitas() {
        // this card is the second face of double-faced card

        // Flameheart Werewolf is a 3/2 with:
        // Whenever Flameheart Werewolf blocks or becomes blocked by a creature, Flameheart Werewolf deals 2 damage to that creature.
        // Kalitas, Traitor of Ghet is a 3/4 with:
        // Lifelink
        // If a nontoken creature an opponent controls would die, instead exile that card and put a 2/2 black Zombie creature token onto the battlefield
        addCard(Zone.BATTLEFIELD, playerA, "Flameheart Werewolf");
        addCard(Zone.BATTLEFIELD, playerB, "Kalitas, Traitor of Ghet");

        attack(2, playerB, "Kalitas, Traitor of Ghet");
        block(2, playerA, "Flameheart Werewolf", "Kalitas, Traitor of Ghet");

        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 23); // lifelinked

        // both should die
        assertPermanentCount(playerA, "Flameheart Werewolf", 0);
        assertExileCount("Flameheart Werewolf", 1); // exiled by Kalitas
        assertPermanentCount(playerB, "Kalitas, Traitor of Ghet", 0);
        assertGraveyardCount(playerB, "Kalitas, Traitor of Ghet", 1);
    }

    @Test
    public void testBlockedByTwo22s() {
        addCard(Zone.BATTLEFIELD, playerA, "Flameheart Werewolf");
        // Both 2/2 creatures should die before the combat starts
        addCard(Zone.BATTLEFIELD, playerB, "Falkenrath Reaver");
        addCard(Zone.BATTLEFIELD, playerB, "Wind Drake");

        attack(3, playerA, "Flameheart Werewolf");
        block(3, playerB, "Falkenrath Reaver", "Flameheart Werewolf");
        block(3, playerB, "Wind Drake", "Flameheart Werewolf");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Flameheart Werewolf was blocked, no trample
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // both 2/2s should die before they had a chance to deal damage
        // to Flameheart Werewolf
        assertGraveyardCount(playerA, "Kessig Forgemaster", 0);
        assertPermanentCount(playerA, "Flameheart Werewolf", 1);
        assertPermanentCount(playerB, "Falkenrath Reaver", 0);
        assertGraveyardCount(playerB, "Falkenrath Reaver", 1);
        assertPermanentCount(playerB, "Wind Drake", 0);
        assertGraveyardCount(playerB, "Wind Drake", 1);
    }

    @Test
    public void testKessigForgemaster() {
        addCard(Zone.BATTLEFIELD, playerA, "Kessig Forgemaster");
        // Both 1/1 creatures should die before the combat starts
        addCard(Zone.BATTLEFIELD, playerB, "Wily Bandar");
        addCard(Zone.BATTLEFIELD, playerB, "Stern Constable");

        // to prevent Kessig from transforming:
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerB, "Island");
        addCard(Zone.HAND, playerA, "Explosive Apparatus");
        addCard(Zone.HAND, playerB, "Explosive Apparatus");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Explosive Apparatus");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Explosive Apparatus");

        attack(3, playerA, "Kessig Forgemaster");
        block(3, playerB, "Wily Bandar", "Kessig Forgemaster");
        block(3, playerB, "Stern Constable", "Kessig Forgemaster");

        setStopAt(3, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        // Kessig Forgemaster was blocked, no trample
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        // both 1/1s should die before they had a chance to deal damage
        // to Kessig Forgemaster
        assertPermanentCount(playerA, "Kessig Forgemaster", 1);
        assertGraveyardCount(playerA, "Kessig Forgemaster", 0);
        assertPermanentCount(playerB, "Wily Bandar", 0);
        assertGraveyardCount(playerB, "Wily Bandar", 1);
        assertPermanentCount(playerB, "Stern Constable", 0);
        assertGraveyardCount(playerB, "Stern Constable", 1);
    }
}
