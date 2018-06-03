
package org.mage.test.cards.abilities.keywords;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class HeroicTest extends CardTestPlayerBase {

    /**
     * When casting Dromoka's Command targeting two of my own Heroic creatures,
     * only one of them triggers. It appears to be the one targeted with mode 4
     * (fight) rather than the one targeted with mode 3 (+1/+1 counter).
     * Screenshot attached. Reproducible.
     */
    @Test
    public void testHeroicWithModal() {
        // Heroic - Whenever you cast a spell that targets Favored Hoplite, put a +1/+1 counter on Favored Hoplite and prevent all damage that would be dealt to it this turn.
        addCard(Zone.BATTLEFIELD, playerA, "Favored Hoplite", 1); // 1/2
        // Heroic — Whenever you cast a spell that targets Lagonna-Band Trailblazer, put a +1/+1 counter on Lagonna-Band Trailblazer.
        addCard(Zone.BATTLEFIELD, playerA, "Lagonna-Band Trailblazer"); // 0/4

        // Mode 3 = Put a +1/+1 counter on target creature
        // Mode 4 = Target creature you control fights target creature you don't control
        addCard(Zone.HAND, playerA, "Dromoka's Command", 1); // {G}{W}
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        addCard(Zone.BATTLEFIELD, playerB, "Silvercoat Lion");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Dromoka's Command", "mode=3Lagonna-Band Trailblazer^mode=4Favored Hoplite^Silvercoat Lion");
        // Silvercoat lion will be set by AI as only possible target
        setModeChoice(playerA, "3");
        setModeChoice(playerA, "4");

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertGraveyardCount(playerA, "Dromoka's Command", 1);

        assertPowerToughness(playerA, "Favored Hoplite", 2, 3);
        assertGraveyardCount(playerB, "Silvercoat Lion", 1);
        assertPowerToughness(playerA, "Lagonna-Band Trailblazer", 2, 6);

    }

}
