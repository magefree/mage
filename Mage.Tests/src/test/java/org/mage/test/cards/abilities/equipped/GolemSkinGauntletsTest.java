package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author noxx
 */
public class GolemSkinGauntletsTest extends CardTestPlayerBase {

    /**
     * Tests that creature will get +1/0 for each equipment
     */
    @Test
    public void testBoostOnEquip() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Heavy Arbalest"); // Equip {4}
        addCard(Zone.BATTLEFIELD, playerA, "Golem-Skin Gauntlets"); // +1/+0 per attached equipment, Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Shuko"); // +1/+0, Equip {0}
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard"); // Creature 2/1

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {4}", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPT("Gauntlets equipped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Elite Vanguard", 4, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {0}", "Elite Vanguard");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Elite Vanguard", 6, 1);
    }

}
