package org.mage.test.cards.continuous;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * This tests that copy effects on a transformed card are being processed correctly.
 * See issue #11677
 *
 * @author DominionSpy
 */
public class DinosaurHeaddressTest extends CardTestPlayerBase {

    private static final String elvishMystic = "Elvish Mystic";
    private static final String falkenrathNoble = "Falkenrath Noble";
    private static final String grizzlyBears = "Grizzly Bears";
    private static final String llanowarElves = "Llanowar Elves";
    private static final String paleontologistsPickAxe = "Paleontologist's Pick-Axe";

    @Test
    public void doesNotApplyCopyEffectWhenNotTransformed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 3);

        addCard(Zone.BATTLEFIELD, playerA, falkenrathNoble);
        addCard(Zone.BATTLEFIELD, playerA, elvishMystic);
        addCard(Zone.BATTLEFIELD, playerA, paleontologistsPickAxe);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Elvish Mystic");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, elvishMystic, 1);
        assertPermanentCount(playerA, falkenrathNoble, 1);
    }

    @Test
    public void appliesCopyEffectWhenTransformed() {
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 5 + 2);

        addCard(Zone.BATTLEFIELD, playerA, falkenrathNoble);
        addCard(Zone.BATTLEFIELD, playerA, grizzlyBears);
        addCard(Zone.BATTLEFIELD, playerA, llanowarElves);
        addCard(Zone.BATTLEFIELD, playerA, elvishMystic);
        addCard(Zone.BATTLEFIELD, playerA, paleontologistsPickAxe);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Craft");
        addTarget(playerA, falkenrathNoble + "^" + grizzlyBears); // Craft targets
        addTarget(playerA, llanowarElves); // ETB target
        setChoice(playerA, falkenrathNoble); // Becomes attached choice

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        // Check that the copy effect turns Llanowar Elves into Falkenrath Noble
        assertPermanentCount(playerA, llanowarElves, 0);
        assertPermanentCount(playerA, falkenrathNoble, 1);

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Equip");
        addTarget(playerA, elvishMystic); // Equip target
        setChoice(playerA, grizzlyBears); // Becomes attached choice

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        // Check that the copy effect on Llanowar Elves is no longer being applied
        assertPermanentCount(playerA, llanowarElves, 1);
        assertPermanentCount(playerA, falkenrathNoble, 0);
        // Check that the copy effect turns Elvish Mystic into Grizzly Bears
        assertPermanentCount(playerA, elvishMystic, 0);
        assertPermanentCount(playerA, grizzlyBears, 1);
    }
}
