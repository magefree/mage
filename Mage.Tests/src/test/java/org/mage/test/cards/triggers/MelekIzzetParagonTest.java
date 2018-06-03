
package org.mage.test.cards.triggers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MelekIzzetParagonTest extends CardTestPlayerBase {

    /**
     * Wenn Melek, Izzet Paragon liegt und man einen Red/Blue Sun's Zenith von
     * der Bib spielt, wird er nicht kopiert, auch wenn der Effekt auf dem Stack
     * sichtbar ist.
     *
     * Meine Theorie ist, dass die Kopie beim in die Bib mischen den Originalen
     * nimmt und er daher nicht mehr dem Stack ist um selbst verrechnet zu
     * werden
     *
     */
    @Test
    public void testCopyZenith() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 5);
        // Play with the top card of your library revealed.
        // You may cast the top card of your library if it's an instant or sorcery card.
        // Whenever you cast an instant or sorcery spell from your library, copy it. You may choose new targets for the copy.
        addCard(Zone.BATTLEFIELD, playerA, "Melek, Izzet Paragon");

        // Red Sun's Zenith deals X damage to any target.
        // If a creature dealt damage this way would die this turn, exile it instead.
        // Shuffle Red Sun's Zenith into its owner's library.
        addCard(Zone.LIBRARY, playerA, "Red Sun's Zenith"); // {X}{R}
        skipInitShuffling();

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Red Sun's Zenith", playerB);
        setChoice(playerA, "X=4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Red Sun's Zenith", 0);

        assertLife(playerA, 20);
        assertLife(playerB, 12);

    }
}
