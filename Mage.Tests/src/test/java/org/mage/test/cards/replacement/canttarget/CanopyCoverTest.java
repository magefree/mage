
package org.mage.test.cards.replacement.canttarget;

import mage.constants.PhaseStep;
import mage.constants.Zone;
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
        addCard(Zone.HAND, playerB, "Lightning Bolt");
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2    Creature - Lion
        // Enchanted creature can't be the target of spells or abilities your opponents control.
        addCard(Zone.HAND, playerA, "Canopy Cover"); // Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canopy Cover", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Canopy Cover", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertHandCount(playerB, "Lightning Bolt", 1);
    }

    @Test
    public void testCantBeTargetedWithAbilities() {
        // {U},Sacrifice Aether Spellbomb: Return target creature to its owner's hand.
        addCard(Zone.BATTLEFIELD, playerB, "Aether Spellbomb");
        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);

        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion"); // 2/2    Creature - Lion
        // Enchanted creature can't be the target of spells or abilities your opponents control.
        addCard(Zone.HAND, playerA, "Canopy Cover"); // Enchantment - Aura

        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Canopy Cover", "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerB, "{U},Sacrifice", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Canopy Cover", 1);
        assertPermanentCount(playerA, "Silvercoat Lion", 1);

        assertGraveyardCount(playerB, "Aether Spellbomb", 0);
        assertPermanentCount(playerB, "Aether Spellbomb", 1);
    }
}
