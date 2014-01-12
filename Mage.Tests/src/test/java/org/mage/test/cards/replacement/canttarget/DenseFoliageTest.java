package org.mage.test.cards.replacement.canttarget;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * DenseFoliage: Creatures can't be the targets of spells.
 *
 * @author Quercitron
 */
public class DenseFoliageTest extends CardTestPlayerBase {

    /**
     * Test spell
     */
    @Test
    public void testSpellCantTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Dense Foliage");
        addCard(Zone.HAND, playerA, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.BATTLEFIELD, playerB, "Eager Cadet");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Eager Cadet");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Eager Cadet", 1);
    }

    /**
     * Tests activated ability
     */
    @Test
    public void testAbilityCanTarget() {
        addCard(Zone.BATTLEFIELD, playerA, "Dense Foliage");
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Sorcerer");

        addCard(Zone.BATTLEFIELD, playerB, "Eager Cadet");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:", "Eager Cadet");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Eager Cadet", 0);
    }
}
