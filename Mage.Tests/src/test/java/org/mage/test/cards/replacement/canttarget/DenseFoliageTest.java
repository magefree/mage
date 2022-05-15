package org.mage.test.cards.replacement.canttarget;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * {@link mage.cards.d.DenseFoliage Dense Foliage}
 * {2}{G}
 * Enchantment
 * Creatures can't be the targets of spells.
 *
 * @author Quercitron
 */
public class DenseFoliageTest extends CardTestPlayerBase {

    /**
     * Test tagrgeting spell, it shouldn't work.
     */
    @Test
    public void testSpellCantTarget() {
        addCard(Zone.HAND, playerA, "Lightning Bolt");

        addCard(Zone.BATTLEFIELD, playerA, "Dense Foliage");
        addCard(Zone.BATTLEFIELD, playerA, "Mountain");

        addCard(Zone.BATTLEFIELD, playerB, "Eager Cadet");

//        setStrictChooseMode(true);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt");
        addTarget(playerA, "Eager Cadet");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);

        try {
            execute();
            assertPermanentCount(playerB, "Eager Cadet", 1);
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("setup good targets")) {
                Assert.fail("must throw error about bad targets, but got:\n" + e.getMessage());
            }
        }
    }

    /**
     * Tests targeting activated ability, it should work.
     */
    @Test
    public void testAbilityCanTarget() {
        // Creatures can't be the targets of spells
        addCard(Zone.BATTLEFIELD, playerA, "Dense Foliage");
        //{T}: Prodigal Sorcerer deals 1 damage to any target.
        addCard(Zone.BATTLEFIELD, playerA, "Prodigal Sorcerer");

        addCard(Zone.BATTLEFIELD, playerB, "Eager Cadet");

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{T}:", "Eager Cadet");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertPermanentCount(playerB, "Eager Cadet", 0);
    }
}
