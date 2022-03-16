
package org.mage.test.cards.abilities.other;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class SpellFizzlesTest extends CardTestPlayerBase {

    @Test
    public void testSpellFizzlesWithNoLegalTargets() throws Exception {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        // Devoid
        // Tap up to two target creatures. Those creatures don't untap during their controller's next untap step.
        // Create a 1/1 colorless Eldrazi Scion creature token onto the battlefield. It has "Sacrifice this creature: Add {C}."
        addCard(Zone.HAND, playerA, "Adverse Conditions"); // {3}{U}

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Mountain", 1);
        addCard(Zone.HAND, playerB, "Lightning Bolt", 1);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Adverse Conditions", "Silvercoat Lion");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Lightning Bolt", "Silvercoat Lion", "Adverse Conditions");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Adverse Conditions", 1);
        assertGraveyardCount(playerB, "Lightning Bolt", 1);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPermanentCount(playerA, "Eldrazi Scion Token", 0); // All targets were illegal - spell fizzles

    }

}
