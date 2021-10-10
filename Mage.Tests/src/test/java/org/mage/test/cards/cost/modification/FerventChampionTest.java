package org.mage.test.cards.cost.modification;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */

public class FerventChampionTest extends CardTestPlayerBase {

    @Test
    public void testFerventChampion() {
        setStrictChooseMode(true);
        // First strike, Haste
        // Whenever Fervent Champion attacks, another target attacking Knight you control gets +1/+0 until end of turn.
        // Equip abilities you activate that target Fervent Champion cost {3} less to activate.
        addCard(Zone.BATTLEFIELD, playerA, "Fervent Champion");

        // Equipped creature gets +2/+2 and has protection from red and from blue.
        // Whenever equipped creature deals combat damage to a player, Sword of Fire 
        // and Ice deals 2 damage to any target and you draw a card.
        // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Sword of Fire and Ice", 1);        

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip");
        addTarget(playerA, "Fervent Champion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAllCommandsUsed();
        
        assertPowerToughness(playerA, "Fervent Champion", 3,3);
    }
}