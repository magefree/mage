package org.mage.test.cards.single.c16;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
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
    public void testAttachesEveryTargetOnEnter() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 5);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite");
        addCard(Zone.BATTLEFIELD, playerA, "Bonesplitter"); // Equipped creature gets +2/+0. Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Vulshok Morningstar"); // Equipped creature gets +2/+2. Equip {2}
        addCard(Zone.HAND, playerA, automaton);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip {2}", "Memnite");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, automaton);
        addTarget(playerA, "Bonesplitter^Vulshok Morningstar");
        setChoice(playerA, true);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertAttachedTo(playerA, "Bonesplitter", automaton, true);
        assertAttachedTo(playerA, "Vulshok Morningstar", automaton, true);
        assertAttachedTo(playerA, "Vulshok Morningstar", "Memnite", false);
        assertPowerToughness(playerA, automaton, 2 + 2 + 2, 2 + 2);
    }
}
