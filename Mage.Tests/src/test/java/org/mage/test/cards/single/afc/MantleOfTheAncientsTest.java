package org.mage.test.cards.single.afc;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.m.MantleOfTheAncients Mantle of the Ancients}
 * {3}{W}{W}
 * Enchantment — Aura
 * Enchant creature you control
 * When this Aura enters, return any number of target Aura and/or Equipment cards from your graveyard to the battlefield attached to enchanted creature.
 * Enchanted creature gets +1/+1 for each Aura and Equipment attached to it.
 *
 * @author notgreat
 */
public class MantleOfTheAncientsTest extends CardTestPlayerBase {

    /**
     * Ensure that only legal targets are returned, and of those only the legally attachable ones attach
     */
    @Test
    public void testCardReturnsCorrectAttachments() {
        String creature = "Skylasher";// Protection from Blue, 2/2 Creature and +1/+1 from first Mantle

        addCard(Zone.HAND, playerA, "Mantle of the Ancients", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 10);
        addCard(Zone.BATTLEFIELD, playerA, creature);
        addCard(Zone.BATTLEFIELD, playerA, "Grim Guardian"); // Counts number of enchantments entering

        // 301.5e: an Equipment put onto the battlefield attached to "an object it can't legally equip"
        // "enters the battlefield unattached".
        addCard(Zone.GRAVEYARD, playerA, "Konda's Banner"); // No attach, Not legendary, but is returned
        addCard(Zone.GRAVEYARD, playerA, "Gate Smasher", 2); // returned unattached 1st, then attached 2nd
        addCard(Zone.GRAVEYARD, playerA, "O-Naginata"); // Yes attach, Pow >= 3

        addCard(Zone.GRAVEYARD, playerA, "Aether Tunnel"); // No attach, Pro Blue, then Yes attach on 2nd try
        addCard(Zone.GRAVEYARD, playerA, "Reprobation"); // Yes attach, Enchant Creature and removes Pro Blue ability
        addCard(Zone.GRAVEYARD, playerA, "Indestructibility"); // Yes attach, Enchant Permanent
        addCard(Zone.GRAVEYARD, playerA, "Abundant Growth"); // No attach, Enchant Land

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mantle of the Ancients", creature);
        setChoice(playerA, "<i>Constellation"); //Stack trigger, Mantle + Grim
        addTarget(playerA, "Konda's Banner^O-Naginata^Aether Tunnel^Reprobation^Indestructibility^Abundant Growth^Gate Smasher");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, "<i>Constellation"); //Stack trigger, Grim x2
        // P/T isn't recalculated mid-resolution, so every attach is judged against the same 3/3 board
        checkPermanentCount("first Gate Smasher returned", 1, PhaseStep.BEGIN_COMBAT,  playerA, "Gate Smasher",1);
        checkPermanentCount("Aether Tunnel not returned", 1, PhaseStep.BEGIN_COMBAT,  playerA, "Aether Tunnel",0);

        // base 0/1, 4 attachments so +4/+4, O-Naginata +3/+0
        checkPT("pt check after first Mantle", 1, PhaseStep.BEGIN_COMBAT, playerA, creature, 7, 5);

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Mantle of the Ancients", creature);
        setChoice(playerA, "<i>Constellation</i>"); //Stack trigger, Mantle -> Grim
        addTarget(playerA, "Gate Smasher^Aether Tunnel");
        addTarget(playerA, TestPlayer.TARGET_SKIP);


        showBattlefield("after", 1, PhaseStep.END_TURN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAttachedTo(playerA, "Konda's Banner", creature, false);
        assertPermanentCount(playerA, "Konda's Banner", 1);
        assertPermanentCount(playerA, "Gate Smasher", 2);
        assertAttachedTo(playerA, "Gate Smasher", creature, true); //Once, p/t check below
        assertAttachedTo(playerA, "O-Naginata", creature, true);

        assertAttachedTo(playerA, "Aether Tunnel", creature, true);
        assertAttachedTo(playerA, "Reprobation", creature, true);
        assertAttachedTo(playerA, "Indestructibility", creature, true);
        assertPermanentCount(playerA, "Abundant Growth", 0);

        assertPermanentCount(playerA, "Mantle of the Ancients", 2);
        // base 0/1, 7 attachments so +14/+14, O-Naginata plus Aether Tunnel plus one Gate Smasher +7/+0
        assertPowerToughness(playerA, creature, 21, 15);
        assertLife(playerB, 15); // Mantle, Reprobation, Indestructibility + Mantle, Aether Tunnel
    }
}
