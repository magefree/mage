package org.mage.test.rollback;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CopyAnthemEffectTest extends CardTestPlayerBase {

    // Addresses issue #6618 - Creatures get +1/+1 when we cancel the cast of a spell

    // Creatures of one player get +1/+1 every time someone cancel the cast of a spell.
    // Looks like the repeating effect was Force Of Virtue's static ability
    // There was also a Mirrormade cast to copy Force of Virtue

    // Further investigation shown that the problem could be also reduced to any anthem effect copied by other permanent, then rollbacking
    @Test
    public void copyAnthemEffect() {
        addCard(Zone.BATTLEFIELD, playerA, "Runeclaw Bear"); // 2/2 vanilla creature
        addCard(Zone.BATTLEFIELD, playerA, "Glorious Anthem"); // creatures you control have +1/+1
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        addCard(Zone.HAND, playerA, "Copy Enchantment");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Copy Enchantment");
        setChoice(playerA, true);
        setChoice(playerA, "Glorious Anthem");

        rollbackTurns(3, PhaseStep.UPKEEP, playerA, 0);

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Runeclaw Bear", 4, 4);
    }

}
