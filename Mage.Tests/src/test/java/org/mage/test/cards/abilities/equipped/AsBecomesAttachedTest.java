package org.mage.test.cards.abilities.equipped;

import mage.ObjectColor;
import mage.abilities.keyword.ProtectionAbility;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Tests that the wording "as {this} becomes equipped to a creature..." is working correctly.
 * As per rules 603.6d and 614.1c, these should be treated as a static ability with a
 * replacement effect. They should NOT cause a trigger to be put on the stack.
 *
 * @author DominionSpy
 */
public class AsBecomesAttachedTest extends CardTestPlayerBase {

    /**
     * Sanctuary Blade {2}
     * Artifact - Equipment
     * As Sanctuary Blade becomes attached to a creature, choose a color.
     * Equipped creature gets +2/+0 and has protection from the last chosen color.
     * Equip {3}
     */
    @Test
    public void test_SanctuaryBladeAbility() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2 + 2);
        addCard(Zone.BATTLEFIELD, playerA, "Sanctuary Blade");
        addCard(Zone.BATTLEFIELD, playerA, "Llanowar Elves");
        addCard(Zone.BATTLEFIELD, playerA, "Elvish Mystic");

        // As Sanctuary Blade becomes attached to a creature, choose a color.
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Llanowar Elves");
        setChoice(playerA, "White");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN, 1);

        // Check that there is no trigger on the stack after the equip ability has resolved
        checkStackSize("stack is empty", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 0);
        checkAbility("llanowar elves must have protection", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Llanowar Elves", ProtectionAbility.class, true);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip", "Elvish Mystic");
        setChoice(playerA, "Blue");
        waitStackResolved(1, PhaseStep.POSTCOMBAT_MAIN, 1);

        // Do the same check for switching from one creature to another
        checkStackSize("stack is empty", 1, PhaseStep.POSTCOMBAT_MAIN, playerA, 0);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertAbility(playerA, "Llanowar Elves", ProtectionAbility.from(ObjectColor.WHITE), false);
        assertAbility(playerA, "Elvish Mystic", ProtectionAbility.from(ObjectColor.BLUE), true);
    }
}
