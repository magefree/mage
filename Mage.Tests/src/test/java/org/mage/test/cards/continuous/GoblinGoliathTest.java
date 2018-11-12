package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class GoblinGoliathTest extends CardTestPlayerBase {

    @Test
    public void test_DoubleDamage() {
        // test double damage on creature (no), on yourself (no) and on opponent (yes) by bold damage

        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 20);
        // {3}{R}, {T}: If a source you control would deal damage to an opponent this turn, it deals double that damage to that player instead.
        addCard(Zone.BATTLEFIELD, playerA, "Goblin Goliath");
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4); // 3 damage
        addCard(Zone.BATTLEFIELD, playerB, "Barktooth Warbeard"); // 6/5

        // normal damage without ability
        castSpell(1, PhaseStep.UPKEEP, playerA, "Lightning Bolt", playerB);
        checkLife("normal damage to opponent", 1, PhaseStep.PRECOMBAT_MAIN, playerB, 20 - 3);

        // activate double damage
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{3}{R}");
        // cast bolt to player (2x damage)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerB);
        checkLife("double damage to opponent", 1, PhaseStep.END_COMBAT, playerB, 20 - 3 - 3 * 2);
        // cast bolt to creature (1x damage)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", "Barktooth Warbeard");
        checkPermanentCount("normal damage to creature", 1, PhaseStep.END_COMBAT, playerB, "Barktooth Warbeard", 1);
        // cast bolt to yourself (1x damage)
        castSpell(1, PhaseStep.BEGIN_COMBAT, playerA, "Lightning Bolt", playerA);
        checkLife("normal damage to yourself", 1, PhaseStep.END_COMBAT, playerA, 20 - 3);

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 4);
        assertLife(playerA, 20 - 3);
        assertLife(playerB, 20 - 3 - 3 * 2);
        assertGraveyardCount(playerB, "Barktooth Warbeard", 0);
    }
}