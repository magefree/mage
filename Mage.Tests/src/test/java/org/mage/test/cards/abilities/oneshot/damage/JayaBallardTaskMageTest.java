
package org.mage.test.cards.abilities.oneshot.damage;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class JayaBallardTaskMageTest extends CardTestPlayerBase {

    @Test
    public void testDamageNormal() {
        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to any target. A creature dealt damage this way can't be regenerated this turn.
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard, Task Mage");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jaya Ballard, Task Mage", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerA, 14);
        assertLife(playerB, 14);
    }

    @Test
    public void testDamageWithDeathPitsOfRath() {
        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to any target. A creature dealt damage this way can't be regenerated this turn.
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard, Task Mage");
        // Whenever a creature is dealt damage, destroy it. It can't be regenerated.
        addCard(Zone.BATTLEFIELD, playerA, "Death Pits of Rath");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jaya Ballard, Task Mage", 1);
        assertPermanentCount(playerA, "Death Pits of Rath", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pillarfield Ox", 1);

        assertLife(playerA, 14);
        assertLife(playerB, 14);
    }

    @Test
    public void testDamageWithRepercussion() {
        // {R}, {tap}, Discard a card: Destroy target blue permanent.
        // {1}{R}, {tap}, Discard a card: Jaya Ballard, Task Mage deals 3 damage to any target. A creature dealt damage this way can't be regenerated this turn.
        // {5}{R}{R}, {tap}, Discard a card: Jaya Ballard deals 6 damage to each creature and each player.
        addCard(Zone.BATTLEFIELD, playerA, "Jaya Ballard, Task Mage");
        // Whenever a creature is dealt damage, Repercussion deals that much damage to that creature's controller.
        addCard(Zone.BATTLEFIELD, playerA, "Repercussion");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);
        addCard(Zone.HAND, playerA, "Silvercoat Lion", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Pillarfield Ox", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{5}{R}{R}");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Jaya Ballard, Task Mage", 1);
        assertPermanentCount(playerA, "Repercussion", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Pillarfield Ox", 2);

        assertLife(playerA, 8);
        assertLife(playerB, 2);
    }

}
