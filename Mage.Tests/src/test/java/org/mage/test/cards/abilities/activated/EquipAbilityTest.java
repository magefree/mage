package org.mage.test.cards.abilities.activated;

import mage.Constants;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author noxx
 */
public class EquipAbilityTest extends CardTestPlayerBase {

    /**
     * Tests equipping creature with hexproof
     */
    @Test
    public void testEquipHexproof() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Merfolk Spy");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Merfolk Spy");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent merfolk = getPermanent("Merfolk Spy", playerA);
        Assert.assertNotNull(merfolk);
        Assert.assertEquals(1, merfolk.getAttachments().size());
    }

    /**
     * Tests equipping creature with shroud
     */
    @Test
    public void testEquipShroud() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Simic Sky Swallower");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Simic Sky Swallower");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent sky = getPermanent("Simic Sky Swallower", playerA);
        Assert.assertNotNull(sky);
        Assert.assertEquals(0, sky.getAttachments().size());
    }

    /**
     * Tests equipping opponent's creature
     */
    @Test
    public void testEquipOpponentsCreature() {
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Constants.Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Constants.Zone.BATTLEFIELD, playerA, "Plains");

        activateAbility(1, Constants.PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Llanowar Elves");

        setStopAt(1, Constants.PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent elves = getPermanent("Llanowar Elves", playerB);
        Assert.assertNotNull(elves);
        Assert.assertEquals(0, elves.getAttachments().size());
    }

}
