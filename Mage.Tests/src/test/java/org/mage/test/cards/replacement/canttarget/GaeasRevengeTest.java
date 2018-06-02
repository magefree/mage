
package org.mage.test.cards.replacement.canttarget;

import mage.abilities.keyword.TrampleAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class GaeasRevengeTest extends CardTestPlayerBase {

    /**
     * Test spell
     */
    @Test
    public void testGreenCanTargetWithSpells() {
        addCard(Zone.HAND, playerA, "Titanic Growth");
        // Gaea's Revenge can't be countered.
        // Haste
        // Gaea's Revenge can't be the target of nongreen spells or abilities from nongreen sources.
        addCard(Zone.BATTLEFIELD, playerA, "Gaea's Revenge"); // 8/5    Creature - Elemental

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Titanic Growth", "Gaea's Revenge");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPowerToughness(playerA, "Gaea's Revenge", 12, 9);
    }

    @Test
    public void testGreenCanTargetWithAnAbilitiy() {
        // Gaea's Revenge can't be countered.
        // Haste
        // Gaea's Revenge can't be the target of nongreen spells or abilities from nongreen sources.
        addCard(Zone.BATTLEFIELD, playerB, "Gaea's Revenge"); // 8/5    Creature - Elemental
        // Whenever a creature you control becomes blocked, it gets +1/+1 until end of turn.
        // {1}{G}: Target creature you control gains trample until end of turn.
        addCard(Zone.BATTLEFIELD, playerB, "Somberwald Alpha"); // 3/2    Creature - Wolf

        addCard(Zone.BATTLEFIELD, playerB, "Forest", 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{1}{G}: Target creature", "Gaea's Revenge");

        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAbility(playerB, "Gaea's Revenge", TrampleAbility.getInstance(), true);
        assertPowerToughness(playerB, "Gaea's Revenge", 8, 5);
    }
}
