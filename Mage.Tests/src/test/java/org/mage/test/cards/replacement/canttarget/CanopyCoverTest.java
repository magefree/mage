
package org.mage.test.cards.replacement.canttarget;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class CanopyCoverTest extends CardTestPlayerBase {

    /**
     * Test spell
     */
    @Test
    public void testCantBeTargetedWithSpells() {
        // Enchanted creature can't be the target of spells or abilities your opponents control.
        addCard(Zone.HAND, playerA, "Canopy Cover"); // Enchantment - Aura
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2    Creature - Lion
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        setStrictChooseMode(true);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canopy Cover", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt");
        addTarget(playerB, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);

        try {
            execute();
            Assert.fail("must throw exception on execute");
        } catch (Throwable e) {
            if (!e.getMessage().contains("setup good targets")) {
                Assert.fail("must throw error about bad targets, but got:\n" + e.getMessage());
            }
        }

        assertPermanentCount(playerA, "Canopy Cover", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        // TODO: I don't know where Lightning Bolt ends up, but it's not in the Hand or the Graveyard
//        assertHandCount(playerB, "Lightning Bolt", 1);

        assertLife(playerA, 20);
        assertLife(playerB, 20);
    }

    @Test
    public void testCantBeTargetedWithAbilities() {
        // {U}, Sacrifice Aether Spellbomb: Return target creature to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerB, "Aether Spellbomb");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2    Creature - Lion
        // Enchanted creature can't be the target of spells or abilities your opponents control.
        addCard(Zone.HAND, playerA, "Canopy Cover"); // Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canopy Cover", "Silvercoat Lion");

        checkPlayableAbility("Can't spellbomb", 1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{U}, Sacrifice", false);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Canopy Cover", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);
    }
}
