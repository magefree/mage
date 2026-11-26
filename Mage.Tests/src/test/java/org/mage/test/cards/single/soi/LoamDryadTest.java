package org.mage.test.cards.single.soi;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * Loam Dryad: "{T}, Tap an untapped creature you control: Add one mana of any
 * color." The cost targets one creature, so the ability's rule text has to use
 * the singular "creature"; the plural filter produced "Tap an untapped
 * creatures you control" and broke text-matched activation.
 */
public class LoamDryadTest extends CardTestPlayerBase {

    @Test
    public void testTapUntappedCreatureForMana() {
        addCard(Zone.BATTLEFIELD, playerA, "Loam Dryad", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Memnite", 1);

        activateManaAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}, Tap an untapped creature you control");
        setChoice(playerA, "Memnite");
        setChoice(playerA, "White");

        // The pool is checked in the step that made the mana: it empties as the
        // step ends, so an after-execute assert would read zero.
        checkManaPool("one white mana", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "W", 1);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.PRECOMBAT_MAIN);
        execute();

        assertTapped("Loam Dryad", true);
        assertTapped("Memnite", true);
    }
}
