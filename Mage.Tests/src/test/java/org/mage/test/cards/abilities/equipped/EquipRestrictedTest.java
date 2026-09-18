package org.mage.test.cards.abilities.equipped;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2, notgreat
 */
public class EquipRestrictedTest extends CardTestPlayerBase {

    /**
     * 701.3a: an Aura, Equipment or Fortification "can't be attached to an object or player it
     * couldn't enchant, equip, or fortify, respectively" -- by any means, not just its own equip.
     */
    @Test
    public void testEquipKondasBannerToNonLegendary() {
        addCard(Zone.BATTLEFIELD, playerB, "Auriok Windwalker");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");
        addCard(Zone.BATTLEFIELD, playerB, "Konda's Banner");

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Attach target Equipment you control to target creature you control.", "Konda's Banner");
        addTarget(playerB, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        Permanent equipment = getPermanent("Konda's Banner", playerB);
        Assert.assertTrue("Konda's Banner may not be attached", equipment.getAttachedTo() == null);
    }

    /**
     * 702.6c: "Additional restrictions for an equip ability don't restrict what the Equipment may be
     * attached to." Excalibur's legendary restriction is on its equip ability, not a static one.
     */
    @Test
    public void testEquipQualityRestrictionDoesNotBlockDirectAttach() {
        addCard(Zone.BATTLEFIELD, playerB, "Auriok Windwalker");
        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion"); // 2/2, not legendary
        addCard(Zone.BATTLEFIELD, playerB, "Excalibur, Sword of Eden");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2); // affordable, so the check below is about the target

        checkPlayableAbility("no legendary to equip", 2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip legendary creature", false);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "{T}: Attach target Equipment you control to target creature you control.", "Excalibur, Sword of Eden");
        addTarget(playerB, "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAttachedTo(playerB, "Excalibur, Sword of Eden", "Silvercoat Lion", true);
        assertPowerToughness(playerB, "Silvercoat Lion", 12, 2); // +10/+0
    }

    /**
     * 701.3b: a legal target that is an illegal host -- the Equipment "doesn't move". The restriction
     * is read off the permanent as it stands, so On Serra's Wings makes the same equip stick.
     */
    @Test
    public void testEquipKondasBannerFollowsLegendarySupertype() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2, not legendary
        addCard(Zone.BATTLEFIELD, playerA, "Konda's Banner");
        addCard(Zone.HAND, playerA, "On Serra's Wings"); // enchanted creature is legendary, +1/+1

        checkPlayableAbility("equip is activatable on a non-legendary", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", true);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "On Serra's Wings", "Silvercoat Lion");

        checkPT("aura only, banner did not attach", 1, PhaseStep.END_TURN, playerA, "Silvercoat Lion", 3, 3);

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Konda's Banner", "Silvercoat Lion", true);
        assertPowerToughness(playerA, "Silvercoat Lion", 5, 5); // +1/+1 aura, +1/+1 shared color, +1/+1 shared type
    }

    /**
     * 301.5d: "An Equipment's controller is separate from the equipped creature's controller; the two
     * need not be the same." Hence AttachableToRestrictedAbility.canEquip passes a null controller.
     */
    @Test
    public void testMagneticTheftAttachesAcrossControllers() {
        addCard(Zone.BATTLEFIELD, playerA, "O-Naginata"); // +3/+0, can only attach to power 3+
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.HAND, playerA, "Magnetic Theft", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Craw Wurm"); // 6/4, power >= 3
        addCard(Zone.BATTLEFIELD, playerB, "Memnite"); // 1/1, power < 3 until O-Naginata's own +3/+0

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magnetic Theft", "O-Naginata^Craw Wurm", true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Magnetic Theft", "O-Naginata^Memnite", true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerB, "O-Naginata", "Craw Wurm", true);
        assertAttachedTo(playerB, "O-Naginata", "Memnite", false);
        assertPowerToughness(playerB, "Craw Wurm", 9, 4);
        assertPowerToughness(playerB, "Memnite", 1, 1);
        assertPermanentCount(playerA, "O-Naginata", 1);
    }

    /**
     * 301.5c: "An Equipment that equips an illegal or nonexistent permanent becomes unattached from
     * that permanent but remains on the battlefield. (This is a state-based action...)"
     */
    @Test
    public void testEquipGateSmasherAndUnattached() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        // {1}{W}: Kranioceros gets +0/+3 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Kranioceros");// 5/2
        // Gate Smasher can be attached only to a creature with toughness 4 or greater.
        // Equipped creature gets +3/+0 and has trample.
        // Equip {3}
        addCard(Zone.BATTLEFIELD, playerA, "Gate Smasher");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{1}{W}:");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Kranioceros");

        checkPT("attached while pumped", 1, PhaseStep.END_TURN, playerA, "Kranioceros", 8, 5);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertPowerToughness(playerA, "Kranioceros", 5, 2);
        Permanent equipment = getPermanent("Gate Smasher", playerA);
        Assert.assertTrue("Gate Smasher may no longer be attached", equipment.getAttachedTo() == null);
    }

    /**
     * The 301.5c check runs with the Equipment's own bonus applied, so O-Naginata keeps its host legal
     * once attached -- unlike the attach itself, where that same bonus does not yet count.
     */
    @Test
    public void testEquipONaginataSustainsItsOwnHost() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite"); // 1/1, only a legal host while pumped
        addCard(Zone.BATTLEFIELD, playerA, "O-Naginata");
        addCard(Zone.HAND, playerA, "Giant Growth"); // +3/+3 until end of turn

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Giant Growth", "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Memnite");

        checkPT("pumped and equipped", 1, PhaseStep.END_COMBAT, playerA, "Memnite", 7, 4);

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, "O-Naginata", "Memnite", true);
        assertPowerToughness(playerA, "Memnite", 4, 1); // pump gone, but its own +3/+0 holds power at 3+
    }
}
