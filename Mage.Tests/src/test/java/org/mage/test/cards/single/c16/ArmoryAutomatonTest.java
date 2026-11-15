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
 * @author notgreat
 */
public class ArmoryAutomatonTest extends CardTestPlayerBase {

    private static final String automaton = "Armory Automaton";

    @Test
    public void testFlickerProtectionAndReattach() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 7);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Leonin Scimitar"); // Equipped creature gets +1/+1. Equip {1}
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
        // Memnite: Scimitar, Energy Blade
        checkPT("Memnite equipped", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Memnite", 1 + 1 + 4, 1 + 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        addTarget(playerA, "Leonin Scimitar");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // 400.7: "An object that moves from one zone to another becomes a new object with no memory of,
        // or relation to, its previous existence." The first trigger's source is gone; the flicker
        // enters a new Automaton with its own trigger.
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cloudshift", automaton, "Whenever");
        addTarget(playerA, "Sword of Vengeance^Sanctuary Blade^Enormous Energy Blade");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // New enters trigger resolves:
        //   Sword of Vengeance: attaches
        //   Sanctuary Blade: attaches, choosing black
        //   Energy Blade: leaves Memnite, attaches and taps the Automaton, then falls off
        // 702.16d: "Such Equipment or Fortifications become unattached from that permanent as a state-based
        // action, but remain on the battlefield."
        setChoice(playerA, true);
        setChoice(playerA, "Black");
        // First enters trigger resolves:
        //   Leonin Scimitar: stays on Memnite, since the Automaton it would attach to is gone
        setChoice(playerA, true);
        checkPermanentTapped("Energy Blade tapped it", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, automaton, true, 1);
        // Memnite: Leonin Scimitar. Automaton: Sword of Vengeance, Sanctuary Blade. Unattached: Energy Blade (and Vulshok Morningstar)
        checkPT("Memnite kept Scimitar", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Memnite", 1 + 1, 1 + 1);
        checkPT("Automaton after flicker", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, automaton, 2 + 2 + 2, 2);

        activateAbility(2, PhaseStep.PRECOMBAT_MAIN, playerB, "Equip {2}", "Grizzly Bears");
        // Bears: Morningstar
        checkPT("Bears equipped", 2, PhaseStep.POSTCOMBAT_MAIN, playerB, "Grizzly Bears", 2 + 2, 2 + 2);

        attack(3, playerA, automaton);
        addTarget(playerA, "Vulshok Morningstar^Sanctuary Blade^Enormous Energy Blade");
        addTarget(playerA, TestPlayer.TARGET_SKIP);
        // Attack trigger resolves:
        //   Vulshok Morningstar: leaves the Bears, attaches
        //   Sanctuary Blade: already attached, does nothing, so no color choice
        //   Energy Blade: can't attach through protection from black, so no tap
        // 701.3b: "If an effect tries to attach an Aura, Equipment, or Fortification to the object or
        // player it's already attached to, the effect does nothing."
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_COMBAT);
        execute();

        assertTapped(automaton, false);
        // 301.5d: "An Equipment's controller is separate from the equipped creature's controller"
        assertPermanentCount(playerB, "Vulshok Morningstar", 1);
        // Automaton: Sword of Vengeance, Sanctuary Blade, Vulshok Morningstar. Memnite: Leonin Scimitar. Bears: nothing
        assertPowerToughness(playerA, automaton, 2 + 2 + 2 + 2, 2 + 2);
        assertPowerToughness(playerA, "Memnite", 1 + 1, 1 + 1);
        assertPowerToughness(playerB, "Grizzly Bears", 2, 2);
    }
}
