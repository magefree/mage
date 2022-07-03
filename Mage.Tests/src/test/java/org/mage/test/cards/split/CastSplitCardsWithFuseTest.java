package org.mage.test.cards.split;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author LevelX2
 */
public class CastSplitCardsWithFuseTest extends CardTestPlayerBase {

    @Test
    public void testCastWear() {
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);

        // INSTANT
        // Wear {1}{R}
        // Destroy target artifact.
        // Tear {W}
        // Destroy target enchantment.
        // Fuse (You may cast one or both halves of this card from your hand.)
        addCard(Zone.HAND, playerA, "Wear // Tear");

        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Wear", "Juggernaut");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Wear // Tear", 1);

        assertGraveyardCount(playerB, "Juggernaut", 1);
    }

    @Test
    public void testCastTear() {
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);

        // INSTANT
        // Wear {1}{R}
        // Destroy target artifact.
        // Tear {W}
        // Destroy target enchantment.
        // Fuse (You may cast one or both halves of this card from your hand.)
        addCard(Zone.HAND, playerA, "Wear // Tear");

        // All creatures have protection from black.
        addCard(Zone.BATTLEFIELD, playerB, "Absolute Grace");

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Tear", "Absolute Grace");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Wear // Tear", 1);

        assertGraveyardCount(playerB, "Absolute Grace", 1);
    }

    @Test
    public void testCastingFusedSpell() {
        // TODO: AI can't distribute mana for future (e.g. it consume W instead R and can't cast next split half that require W)
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1 + 1);
        // INSTANT
        // Wear {1}{R}
        // Destroy target artifact.
        // Tear {W}
        // Destroy target enchantment.
        // Fuse (You may cast one or both halves of this card from your hand.)
        addCard(Zone.HAND, playerA, "Wear // Tear");

        // All creatures have protection from black.
        addCard(Zone.BATTLEFIELD, playerB, "Absolute Grace"); // Enchantment
        addCard(Zone.BATTLEFIELD, playerB, "Juggernaut"); // Artifact

        // showAvailableAbilities("abils", 1, PhaseStep.PRECOMBAT_MAIN, playerA);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Wear // Tear");
        addTarget(playerA, "Juggernaut");
        addTarget(playerA, "Absolute Grace");
        //playerA.addTarget("Absolute Grace");
        // showBattlefield("after", 1, PhaseStep.BEGIN_COMBAT, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.BEGIN_COMBAT);
        execute();
        assertAllCommandsUsed();

        assertGraveyardCount(playerA, "Wear // Tear", 1);

        assertGraveyardCount(playerB, "Juggernaut", 1);
        assertGraveyardCount(playerB, "Absolute Grace", 1);
    }
}
