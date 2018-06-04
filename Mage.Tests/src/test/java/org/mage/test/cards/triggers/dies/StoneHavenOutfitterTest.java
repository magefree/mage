
package org.mage.test.cards.triggers.dies;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class StoneHavenOutfitterTest extends CardTestPlayerBase {

    @Test
    public void testEquipped() {
        // Equipped creatures you control get +1/+1.
        // Whenever an equipped creature you control dies, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Stone Haven Outfitter", 1); // Creature 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        // Equipped creature has hexproof and haste.
        // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Swiftfoot Boots", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPowerToughness(playerA, "Silvercoat Lion", 3, 3);

    }

    @Test
    public void testEquippedDied() {
        // Equipped creatures you control get +1/+1.
        // Whenever an equipped creature you control dies, draw a card.
        addCard(Zone.BATTLEFIELD, playerA, "Stone Haven Outfitter", 1); // Creature 2/2
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        // Equipped creature has hexproof and haste.
        // Equip {1}
        addCard(Zone.BATTLEFIELD, playerA, "Swiftfoot Boots", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);

        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Equip", "Silvercoat Lion");

        castSpell(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Lightning Bolt", "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertGraveyardCount(playerA, "Silvercoat Lion", 1);

        assertHandCount(playerA, 1);

    }
}
