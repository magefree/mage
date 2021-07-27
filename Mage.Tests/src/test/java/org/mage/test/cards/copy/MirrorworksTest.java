
package org.mage.test.cards.copy;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 *
 * @author LevelX2
 */
public class MirrorworksTest extends CardTestPlayerBase {

    /**
     * If you play Mox Diamond, with Mirrorworks in play, and create a token
     * copy, and you have no lands in hand, the Mox will enter the battlefield
     * as usual instead of the graveyard.
     */
    @Test
    public void TestCopyWithoutLand() {
        addCard(Zone.HAND, playerA, "Mountain", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add one mana of any color.
        addCard(Zone.HAND, playerA, "Mox Diamond", 1); // Artifact {0}

        // Whenever another nontoken artifact enters the battlefield under your control, you may pay {2}.
        // If you do, create a token that's a copy of that artifact.
        addCard(Zone.BATTLEFIELD, playerA, "Mirrorworks", 1); // Artifact {5}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Diamond");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mox Diamond", 1);
        assertTappedCount("Island", true, 2);
        assertGraveyardCount(playerA, "Mountain", 1);

    }

    @Test
    public void TestCorrectCopy() {
        addCard(Zone.HAND, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 2);

        // If Mox Diamond would enter the battlefield, you may discard a land card instead. If you do, put Mox Diamond onto the battlefield. If you don't, put it into its owner's graveyard.
        // {T}: Add one mana of any color.
        addCard(Zone.HAND, playerA, "Mox Diamond", 1); // Artifact {0}

        // Whenever another nontoken artifact enters the battlefield under your control, you may pay {2}.
        // If you do, create a token that's a copy of that artifact.
        addCard(Zone.BATTLEFIELD, playerA, "Mirrorworks", 1); // Artifact {5}

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Mox Diamond");
        setChoice(playerA, true);

        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();

        assertPermanentCount(playerA, "Mox Diamond", 2);
        assertGraveyardCount(playerA, "Mountain", 2);

    }
}
