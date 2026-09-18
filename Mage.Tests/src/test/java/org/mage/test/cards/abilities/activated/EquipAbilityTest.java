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

        setStrictChooseMode(true);
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

        setStrictChooseMode(true);
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

        setStrictChooseMode(true);
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

    // 702.6c: "These equip abilities may legally target only a creature that's controlled by the
    // player activating the ability and that has the chosen quality."
    // 702.6d: "If a permanent has multiple equip abilities, any of its equip abilities may be activated."
    @Test
    public void testEquipRequirements() {
        addCard(Zone.BATTLEFIELD, playerA, "Blackblade Reforged");
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2, not legendary
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.HAND, playerA, "On Serra's Wings"); // {3}{W}, enchanted creature is legendary, +1/+1

        checkPlayableAbility("quality equip has no legal target", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip legendary creature", false);
        checkPlayableAbility("plain equip does", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {7}", true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "On Serra's Wings", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        // the aura flips each ability the other way: a legal target appears, and only 3 mana is left
        checkPlayableAbility("target is now legendary", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip legendary creature", true);
        checkPlayableAbility("no longer affordable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {7}", false);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip legendary creature", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 10, 10); // +1/+1 aura, +1/+1 per land
    }

    // 701.3b: "If an effect tries to attach an Aura, Equipment, or Fortification to an object or
    // player it can't be attached to, the Aura, Equipment, or Fortification doesn't move."
    @Test
    public void testEquipIllegalTargetDoesNotMoveEquipment() {
        addCard(Zone.BATTLEFIELD, playerA, "O-Naginata");
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde");      // 3/3, a legal host
        addCard(Zone.BATTLEFIELD, playerA, "Benevolent Bodyguard"); // 1/1, not legal host
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 4);

        checkPlayableAbility("equip is activatable even on an illegal host", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Barbarian Horde");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Benevolent Bodyguard");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Barbarian Horde", 6, 3);
        assertPowerToughness(playerA, "Benevolent Bodyguard", 1, 1);
        assertAttachedTo(playerA, "O-Naginata", "Barbarian Horde", true);
        assertAttachedTo(playerA, "O-Naginata", "Benevolent Bodyguard", false);
    }

    @Test
    public void testEquipFallsOff() {
        addCard(Zone.BATTLEFIELD, playerA, "O-Naginata");
        addCard(Zone.BATTLEFIELD, playerA, "Barbarian Horde");
        addCard(Zone.BATTLEFIELD, playerA, "Wastes", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Island", 3);
        addCard(Zone.HAND, playerB, "Befuddle");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Barbarian Horde");
        castSpell(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Befuddle", "Barbarian Horde");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Barbarian Horde", -1, 3);
        Assert.assertTrue(getPermanent("Barbarian Horde", playerA).getAttachments().isEmpty());
    }
}
