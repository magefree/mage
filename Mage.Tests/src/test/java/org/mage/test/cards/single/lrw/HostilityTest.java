
package org.mage.test.cards.single.lrw;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author Kranken, LevelX2
 */

public class HostilityTest extends CardTestPlayerBase {

    @Test
    public void testCombatDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Hostility");
        attack(1, playerA, "Hostility");

        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 14);
    }

    @Test
    public void testSpellDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Hostility");

        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, 5); // hostility, mountain, and 3 tokens
    }

    @Test
    public void testOpponentSpellDamage() {
        addCard(Zone.BATTLEFIELD, playerA, "Hostility");

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", playerB);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 17);
    }

}