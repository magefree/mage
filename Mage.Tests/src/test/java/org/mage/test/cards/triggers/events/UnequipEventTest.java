package org.mage.test.cards.triggers.events;

import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.InfectAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class UnequipEventTest extends CardTestPlayerBase {

    @Test
    public void testGraftedExoskeletonEvent() {
        // When Nazahn, Revered Bladesmith enters the battlefield, search your library for an Equipment card and reveal it. If you reveal a card named Hammer of Nazahn this way, put it onto the battlefield. Otherwise, put that card into your hand. Then shuffle your library.
        // Whenever an equipped creature you control attacks, you may tap target creature defending player controls.
        addCard(Zone.HAND, playerA, "Nazahn, Revered Bladesmith"); // Creature 5/4  {4}{G}{W}
        // Whenever Hammer of Nazahn or another Equipment enters the battlefiend under your control, you may attach that Equipment to target creature you control.
        // Equipped creature gets +2/+0 and has indestructible.
        // Equip {4}
        addCard(Zone.LIBRARY, playerA, "Hammer of Nazahn");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Equipped creature gets +2/+2 and has infect.
        // Whenever Grafted Exoskeleton becomes unattached from a permanent, sacrifice that permanent.
        // Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Grafted Exoskeleton", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nazahn, Revered Bladesmith");
        setChoice(playerA, "Hammer of Nazahn");

        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}");

        setStopAt(3, PhaseStep.BEGIN_COMBAT);
        execute();

        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Nazahn, Revered Bladesmith", 1);
        assertPowerToughness(playerA, "Nazahn, Revered Bladesmith", 9, 6);
        assertAbility(playerA, "Nazahn, Revered Bladesmith", IndestructibleAbility.getInstance(), true);
        assertAbility(playerA, "Nazahn, Revered Bladesmith", InfectAbility.getInstance(), true);

        assertPermanentCount(playerA, "Hammer of Nazahn", 1);
    }

    /**
     * I cast Beast Within on a Grafted Exoskeleton (equipped on a Nazahn with
     * also a Bloodforged Battle-Axe and Hammer of Nazahn), it got destroyed,
     * but Nazahn didn't get sacrificed. Perhaps of note is the fact that it got
     * equipped via Hammer of Nazahn's ability. Also I remember this interaction
     * working correctly in the past, so a recent-ish update must've broken it.
     */
    @Test
    public void testGraftedExoskeletonAndBeastWithinEvent() {
        // When Nazahn, Revered Bladesmith enters the battlefield, search your library for an Equipment card and reveal it. If you reveal a card named Hammer of Nazahn this way, put it onto the battlefield. Otherwise, put that card into your hand. Then shuffle your library.
        // Whenever an equipped creature you control attacks, you may tap target creature defending player controls.
        addCard(Zone.HAND, playerA, "Nazahn, Revered Bladesmith"); // Creature 5/4  {4}{G}{W}
        // Whenever Hammer of Nazahn or another Equipment enters the battlefiend under your control, you may attach that Equipment to target creature you control.
        // Equipped creature gets +2/+0 and has indestructible.
        // Equip {4}
        addCard(Zone.LIBRARY, playerA, "Hammer of Nazahn");
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 6);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // Destroy target permanent. Its controller creates a 3/3 green Beast creature token.
        addCard(Zone.HAND, playerA, "Beast Within"); // Instant {2}{G}
        // Equipped creature gets +2/+2 and has infect.
        // Whenever Grafted Exoskeleton becomes unattached from a permanent, sacrifice that permanent.
        // Equip {2}
        addCard(Zone.HAND, playerA, "Grafted Exoskeleton", 1); // Artifact Equipment - {4}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nazahn, Revered Bladesmith");
        addTarget(playerA, "Hammer of Nazahn");
        setChoice(playerA, true); // Put the hammer on the battlefield
        setChoice(playerA, true); // Attach the hammer to a creature
        addTarget(playerA, "Nazahn, Revered Bladesmith");

        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Grafted Exoskeleton");
        setChoice(playerA, true); // Attach the Grafted Exoskeleton to a creature
        addTarget(playerA, "Nazahn, Revered Bladesmith");
        
        castSpell(3, PhaseStep.BEGIN_COMBAT, playerA, "Beast Within", "Grafted Exoskeleton");

        setStopAt(3, PhaseStep.END_COMBAT);
        setStrictChooseMode(true);
        execute();
        assertAllCommandsUsed();
        
        assertLife(playerA, 20);
        assertLife(playerB, 20);

        assertPermanentCount(playerA, "Hammer of Nazahn", 1);
        assertGraveyardCount(playerA, "Beast Within", 1);
        assertPowerToughness(playerA, "Beast Token", 3, 3);
        assertGraveyardCount(playerA, "Grafted Exoskeleton", 1);
        assertGraveyardCount(playerA, "Nazahn, Revered Bladesmith", 1);

    }
}
