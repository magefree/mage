
package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MetallicMiminTest extends CardTestPlayerBase {

    /**
     * Additionally, if you play a Mimic naming, for example, "Dwarf", then if
     * you play a second Mimic also naming "Dwarf", the second Mimic won't enter
     * with a counter. Going by 616.2 in the Comp Rules, the second one should
     * be getting a counter as long as you name the same type as the first one.
     *
     * 611.3c Continuous effects that modify characteristics of permanents do so
     * simultaneously with the permanent entering the battlefield. They don't
     * wait until the permanent is on the battlefield and then change it.
     * Because such effects apply as the permanent enters the battlefield, they
     * are applied before determining whether the permanent will cause an
     * ability to trigger when it enters the battlefield. #
     *
     * Example: A permanent with the static ability “All white creatures get
     * +1/+1” is on the battlefield. A creature spell that would normally create
     * a 1/1 white creature instead creates a 2/2 white creature. The creature
     * doesn't enter the battlefield as 1/1 and then change to 2/2.
     *
     */
    @Test
    public void testMetallicMimic() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 4);

        // As Metallic Mimic enters the battlefield, choose a creature type.
        // Metallic Mimic is the chosen type in addition to its other types.
        // Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Metallic Mimic", 2); // Creature {2}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Metallic Mimic");
        setChoice(playerA, "Dwarf");
        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Metallic Mimic");
        setChoice(playerA, "Dwarf");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Metallic Mimic", 2);
        assertPowerToughness(playerA, "Metallic Mimic", 2, 1);
        assertPowerToughness(playerA, "Metallic Mimic", 3, 2);
    }

    @Test
    public void testMetallicMimicBramblewoodParagon() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // As Metallic Mimic enters the battlefield, choose a creature type.
        // Metallic Mimic is the chosen type in addition to its other types.
        // Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Metallic Mimic", 2); // Creature {2}

        // Each other Warrior creature you control enters the battlefield with an additional +1/+1 counter on it.
        // Each creature you control with a +1/+1 counter on it has trample.
        addCard(Zone.BATTLEFIELD, playerA, "Bramblewood Paragon", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Metallic Mimic");
        setChoice(playerA, "Warrior");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Metallic Mimic", 1);
        assertPowerToughness(playerA, "Metallic Mimic", 3, 2);
    }

    @Test
    public void testMetallicLasts() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        // As Metallic Mimic enters the battlefield, choose a creature type.
        // Metallic Mimic is the chosen type in addition to its other types.
        // Each other creature you control of the chosen type enters the battlefield with an additional +1/+1 counter on it.
        addCard(Zone.HAND, playerA, "Metallic Mimic", 2); // Creature {2}

        // Flash (You may cast this spell any time you could cast an instant.)
        // Each creature you control that's a Wolf or a Werewolf gets +1/+1 and has trample.
        addCard(Zone.BATTLEFIELD, playerA, "Howlpack Resurgence", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Metallic Mimic");
        setChoice(playerA, "Wolf");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Metallic Mimic", 1);
        assertPowerToughness(playerA, "Metallic Mimic", 3, 2);
    }

}
