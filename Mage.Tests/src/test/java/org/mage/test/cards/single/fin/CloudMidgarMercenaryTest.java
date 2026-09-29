package org.mage.test.cards.single.fin;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

public class CloudMidgarMercenaryTest extends CardTestPlayerBase {

    @Test
    public void testEquippedTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Cloud, Midgar Mercenary");
        addCard(Zone.BATTLEFIELD, playerA, "Sword of Forge and Frontier");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Cloud, Midgar Mercenary");
        attack(1, playerA, "Cloud, Midgar Mercenary");
        setChoice(playerA, "Whenever");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertExileCount(playerA, 4);
    }

    @Test
    public void testGainsTriggers() {
        addCard(Zone.BATTLEFIELD, playerA, "Cloud, Midgar Mercenary");
        addCard(Zone.BATTLEFIELD, playerA, "Power Fist");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Cloud, Midgar Mercenary");
        attack(1, playerA, "Cloud, Midgar Mercenary");
        setChoice(playerA, "Whenever");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Cloud, Midgar Mercenary", 6, 5);
    }

    @Test
    public void testNotEquipped() {
        addCard(Zone.BATTLEFIELD, playerA, "Cloud, Midgar Mercenary");
        addCard(Zone.HAND, playerA, "Invocation of Saint Traft");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");
        addCard(Zone.BATTLEFIELD, playerA, "Island");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Invocation of Saint Traft", "Cloud, Midgar Mercenary");
        attack(1, playerA, "Cloud, Midgar Mercenary");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.DECLARE_BLOCKERS);
        execute();

        assertPermanentCount(playerA, "Angel Token", 1);
    }

}
