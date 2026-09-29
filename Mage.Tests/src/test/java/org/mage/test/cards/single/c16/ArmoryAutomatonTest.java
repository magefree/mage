package org.mage.test.cards.single.c16;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.player.TestPlayer;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.a.ArmoryAutomaton Armory Automaton} {3}, 2/2
 * Whenever Armory Automaton enters or attacks, you may attach any number of target Equipment to it.
 *
 * @author notgreat, Claude Opus 5.5
 */
public class ArmoryAutomatonTest extends CardTestPlayerBase {

    private static final String automaton = "Armory Automaton";

    @Test
    public void testFlickerProtectionAndReattach() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter"); // Equipped creature gets +2/+0. Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Enormous Energy Blade"); // Black. Equipped creature gets +4/+0. Whenever this becomes attached to a creature, tap that creature. Equip {2}
        addCard(Zone.BATTLEFIELD, playerA, "Sanctuary Blade"); // As this becomes attached to a creature, choose a color. Equipped creature gets +2/+0 and has protection from the last chosen color.
        addCard(Zone.BATTLEFIELD, playerA, "Sword of Vengeance"); // Equipped creature gets +2/+0 and has first strike, vigilance, trample, and haste.
        addCard(Zone.HAND, playerA, automaton);
        addCard(Zone.HAND, playerA, "Cloudshift");
        addCard(Zone.BATTLEFIELD, playerB, "Plains", 2);
        addCard(Zone.BATTLEFIELD, playerB, "Grizzly Bears");
        addCard(Zone.BATTLEFIELD, playerB, "Vulshok Morningstar"); // Equipped creature gets +2/+2. Equip {2}

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {1}", "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        addTarget(playerA, "Bonesplitter");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // 400.7: "An object that moves from one zone to another becomes a new object with no memory of,
        // or relation to, its previous existence." The first trigger's source is gone; the flicker
        // enters a new Automaton with its own trigger.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", automaton, "Whenever");
        // 702.16d: "Such Equipment or Fortifications become unattached from that permanent as a state-based
        // action, but remain on the battlefield." The Energy Blade is attached alongside the protection, so it
        // leaves Memnite, taps the Automaton, then falls off.
        addTarget(playerA, "Sword of Vengeance^Sanctuary Blade^Enormous Energy Blade");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, true); // new enters trigger
        setChoice(playerA, "Black");
        setChoice(playerA, true); // first trigger still asks, but has nothing to attach to
        checkPermanentTapped("Energy Blade tapped it", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, automaton, true, 1);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {2}", "Grizzly Bears");

        // 701.3b: "If an effect tries to attach an Aura, Equipment, or Fortification to the object or
        // player it's already attached to, the effect does nothing." Nor can the Energy Blade attach now,
        // so neither becomes-attached ability applies again: no color choice, no tap.
        attack(3, playerA, automaton);
        addTarget(playerA, "Vulshok Morningstar^Sanctuary Blade^Enormous Energy Blade");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertAttachedTo(playerA, "Bonesplitter", "Memnite", true);
        assertAttachedTo(playerA, "Bonesplitter", automaton, false);
        assertAttachedTo(playerA, "Enormous Energy Blade", automaton, false);
        assertAttachedTo(playerA, "Enormous Energy Blade", "Memnite", false);
        assertAttachedTo(playerA, "Sanctuary Blade", automaton, true);
        assertAttachedTo(playerA, "Sword of Vengeance", automaton, true);
        assertTapped(automaton, false);
        // 301.5d: "An Equipment's controller is separate from the equipped creature's controller"
        assertAttachedTo(playerA, "Vulshok Morningstar", automaton, true);
        assertAttachedTo(playerB, "Vulshok Morningstar", "Grizzly Bears", false);
        assertPermanentCount(playerB, "Vulshok Morningstar", 1);
        assertPowerToughness(playerA, automaton, 2 + 2 + 2 + 2, 2 + 2);
        assertPowerToughness(playerA, "Memnite", 1 + 2, 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }
}
