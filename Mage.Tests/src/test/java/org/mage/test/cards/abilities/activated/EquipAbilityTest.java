package org.mage.test.cards.abilities.activated;

import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Zone.BATTLEFIELD, playerA, "Merfolk Spy");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Merfolk Spy");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent merfolk = getPermanent("Merfolk Spy", playerA);
        Assert.assertNotNull(merfolk);
        Assert.assertEquals(1, merfolk.getAttachments().size());
    }

    /**
     * Tests not being able to equip creature with shroud.
     */
    @Test
    public void testEquipShroud() {
        addCard(Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Zone.BATTLEFIELD, playerA, "Simic Sky Swallower");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        checkPlayableAbility("during", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", false);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent sky = getPermanent("Simic Sky Swallower", playerA);
        Assert.assertNotNull(sky);
        Assert.assertEquals(0, sky.getAttachments().size());
    }

    /**
     * Tests not being able to equip opponent's creature.
     */
    @Test
    public void testEquipOpponentsCreature() {
        addCard(Zone.BATTLEFIELD, playerA, "Ring of Evos Isle");
        addCard(Zone.BATTLEFIELD, playerB, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Plains");

        checkPlayableAbility("during", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", false);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent elves = getPermanent("Llanowar Elves", playerB);
        Assert.assertNotNull(elves);
        Assert.assertEquals(0, elves.getAttachments().size());
    }

    @Test
    public void testAsAttachesToCreatureAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3 + 5);

        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Sanctuary Blade");

        addCard(Zone.BATTLEFIELD, playerA, "Falkenrath Noble");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic");
        addCard(Zone.BATTLEFIELD, playerA, "Paleontologist's Pick-Axe");

        // As Sanctuary Blade becomes attached to a creature, choose a color.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Llanowar Elves");
        setChoice(playerA, "White");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // When Dinosaur Headdress enters the battlefield, attach it to target creature you control.
        // As Dinosaur Headdress becomes attached to a creature, choose an exiled creature card used to craft Dinosaur Headdress.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");
        addTarget(playerA, "Falkenrath Noble"); // Craft target
        addTarget(playerA, "Elvish Mystic"); // ETB target
        setChoice(playerA, "Falkenrath Noble"); // Becomes attached choice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAbility(playerA, "Llanowar Elves", ProtectionAbility.from(ObjectColor.WHITE), true);
        assertPermanentCount(playerA, "Dinosaur Headdress", 1);
        assertPermanentCount(playerA, "Falkenrath Noble", 1);
        assertPermanentCount(playerA, "Elvish Mystic", 0);
    }

}
