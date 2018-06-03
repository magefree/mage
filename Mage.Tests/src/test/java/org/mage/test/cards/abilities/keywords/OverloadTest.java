
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class OverloadTest extends CardTestPlayerBase {

    /**
     * My opponent cast an overloaded Vandalblast, and Xmage would not let me
     * cast Mental Misstep on it.
     *
     * The CMC of a card never changes, and Vandalblast's CMC is always 1.
     *
     * 4/15/2013 Casting a spell with overload doesn't change that spell's mana
     * cost. You just pay the overload cost instead.
     */
    @Test
    public void testCastByOverloadDoesNotChangeCMC() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Destroy target artifact you don't control.
        // Overload {4}{R} (You may cast this spell for its overload cost. If you do, change its text by replacing all instances of "target" with "each.")
        addCard(Zone.HAND, playerA, "Vandalblast");

        addCard(Zone.BATTLEFIELD, playerB, "Island", 1);
        // Counter target spell with converted mana cost 1.
        addCard(Zone.HAND, playerB, "Mental Misstep", 1);
        addCard(Zone.BATTLEFIELD, playerB, "War Horn", 2);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Vandalblast with overload");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerB, "Mental Misstep", "Vandalblast");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Vandalblast", 1);
        assertGraveyardCount(playerB, "Mental Misstep", 1);

        assertPermanentCount(playerB, "War Horn", 2);

    }

}
