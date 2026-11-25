package org.mage.test.cards.single.lea;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.ArtifactWard Artifact Ward} {W}
 * Enchantment - Aura
 * Enchant creature
 * Enchanted creature can't be blocked by artifact creatures.
 * Prevent all damage that would be dealt to enchanted creature by artifact sources.
 * Enchanted creature can't be the target of abilities from artifact sources.
 * <p>
 * The Oracle text grants no protection, so artifact Equipment stays attached
 * (protection from artifacts would make it fall off, CR 702.16c, 704.5n).
 */
public class ArtifactWardTest extends CardTestPlayerBase {

    private static final String ward = "Artifact Ward";
    private static final String bears = "Grizzly Bears";

    @Test
    public void test_EquipmentStaysAttached() {
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Paradise Mantle");
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 2);
        addCard(Zone.HAND, playerA, ward);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", bears);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ward, bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertAttachedTo(playerA, ward, bears, true);
        assertAttachedTo(playerA, "Paradise Mantle", bears, true);
    }

    @Test
    public void test_ArtifactDamagePrevented() {
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, ward);
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut"); // 5/3 artifact creature, attacks each combat

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ward, bears);

        // Juggernaut attacks by itself (it attacks each combat if able)
        block(2, playerA, bears, "Juggernaut");

        setStrictChooseMode(true);
        setStopAt(2, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        assertPermanentCount(playerA, bears, 1);
        assertDamageReceived(playerA, bears, 0);
        assertDamageReceived(playerB, "Juggernaut", 2);
    }

    @Test
    public void test_OnlyArtifactAbilitiesCantTarget() {
        addCard(Zone.BATTLEFIELD, playerA, bears);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.HAND, playerA, ward);
        addCard(Zone.BATTLEFIELD, playerB, "Rod of Ruin"); // artifact: {3}, {T}: 1 damage to any target
        addCard(Zone.BATTLEFIELD, playerB, "Prodigal Sorcerer"); // creature: {T}: 1 damage to any target

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, ward, bears);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.POSTCOMBAT_MAIN);
        execute();

        Permanent warded = getPermanent(bears, playerA);
        Permanent rod = getPermanent("Rod of Ruin", playerB);
        Permanent sorcerer = getPermanent("Prodigal Sorcerer", playerB);
        Assert.assertFalse("an artifact source's ability can't target the enchanted creature",
                warded.canBeTargetedBy(rod, playerB.getId(), null, currentGame));
        Assert.assertTrue("a nonartifact source's ability can target the enchanted creature",
                warded.canBeTargetedBy(sorcerer, playerB.getId(), null, currentGame));
    }
}
