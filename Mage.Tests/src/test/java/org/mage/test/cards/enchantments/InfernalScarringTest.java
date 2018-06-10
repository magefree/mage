
package org.mage.test.cards.enchantments;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class InfernalScarringTest extends CardTestPlayerBase {

    /**
     * The 'draw a card, when this creature dies' didn't trigger after i
     * sacrificed a creature enchanted with infernal scarring (enchanted
     * creature was Fetid Imp and the sac trigger came from a Nantuko Husk).
     *
     */
    @Test
    public void testDiesTrigger() {
        // Sacrifice a creature: Nantuko Husk gets +2/+2 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Nantuko Husk", 1); // {2}{B}
        addCard(Zone.BATTLEFIELD, playerA, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 2);
        // Enchant creature
        // Enchanted creature gets +2/+0 and has "When this creature dies, draw a card."
        addCard(Zone.HAND, playerA, "Infernal Scarring"); // {1}{B}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Infernal Scarring", "Silvercoat Lion");

        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "Sacrifice a creature");
        setChoice(playerA, "Silvercoat Lion");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Nantuko Husk", 4, 4);
        assertGraveyardCount(playerA, "Infernal Scarring", 1);
        assertHandCount(playerA, 1);
    }

}
