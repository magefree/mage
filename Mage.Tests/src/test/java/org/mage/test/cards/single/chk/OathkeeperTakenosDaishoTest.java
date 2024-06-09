package org.mage.test.cards.single.chk;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author xenohedron
 */
public class OathkeeperTakenosDaishoTest extends CardTestPlayerBase {

    static final String equipment = "Oathkeeper, Takeno's Daisho";
    static final String samurai = "Devoted Retainer";
    static final String removeEquipped = "Path of Peace";
    static final String removeEquipment = "Lucky Offering";

    @Test
    public void testReanimateEquipped(){

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        addCard(Zone.BATTLEFIELD, playerA, equipment, 1);
        addCard(Zone.BATTLEFIELD, playerA, samurai, 1);
        addCard(Zone.HAND, playerA, removeEquipped, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", samurai);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, removeEquipped, samurai);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, samurai, 1);
        assertLife(playerA, 24);

    }

    @Test
    public void testDontReanimateEquipped(){

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 4);
        addCard(Zone.BATTLEFIELD, playerA, equipment, 1);
        addCard(Zone.BATTLEFIELD, playerA, "Elite Vanguard", 1);
        addCard(Zone.HAND, playerA, removeEquipped, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Elite Vanguard");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, removeEquipped, "Elite Vanguard");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Elite Vanguard", 1);
        assertLife(playerA, 24);

    }

    @Test
    public void testExileEquipped(){

        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2 + 1);
        addCard(Zone.BATTLEFIELD, playerA, equipment, 1);
        addCard(Zone.BATTLEFIELD, playerA, samurai, 1);
        addCard(Zone.HAND, playerA, removeEquipment, 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", samurai);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, removeEquipment, equipment);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertExileCount(playerA, samurai, 1);
        assertLife(playerA, 23);

    }

}
