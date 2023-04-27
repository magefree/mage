package org.mage.test.cards.split;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class CastSplitCardsWithSpliceTest extends CardTestPlayerBase {

    // splice cost applies for one fused spell, not to every part
    // https://github.com/magefree/mage/issues/6493

    @Test
    public void test_ThaliaGuardianOfThraben_CostModification_Fused() {
        removeAllCardsFromHand(playerA);

        // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben", 1);
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.HAND, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // cast fused
        // old bug: https://github.com/magefree/mage/issues/6493
        // * getPlayable checks fused cost (+1 modification)
        // * activate checks fused cost (+1 modification) andeach card's part (+1 modification)
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Wear // Tear");
        addTarget(playerA, "Bident of Thassa");
        addTarget(playerA, "Bow of Nylea");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Wear // Tear", 1);
        assertGraveyardCount(playerB, "Bident of Thassa", 1);
        assertGraveyardCount(playerB, "Bow of Nylea", 1);
        assertHandCount(playerA, 0);

        // must used all mana
        assertTappedCount("Mountain", true, 3);
        assertTappedCount("Plains", true, 1);
    }

    @Test
    public void test_ThaliaGuardianOfThraben_CostModification_FusedWithSplice() {
        removeAllCardsFromHand(playerA);

        // Noncreature spells cost {1} more to cast.
        addCard(Zone.BATTLEFIELD, playerA, "Thalia, Guardian of Thraben", 1);
        //
        // Draw a card.
        // Splice onto instant or sorcery {2}{U}
        addCard(Zone.HAND, playerA, "Everdream", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3); // for splice
        //
        // Wear {1}{R} Destroy target artifact.
        // Tear {W} Destroy target enchantment.
        addCard(Zone.HAND, playerA, "Wear // Tear", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2 + 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Bident of Thassa", 1); // Legendary Enchantment Artifact
        addCard(Zone.BATTLEFIELD, playerB, "Bow of Nylea", 1); // Legendary Enchantment Artifact

        // cast fused with splice
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "fused Wear // Tear");
        setChoice(playerA, true); // use splice
        addTarget(playerA, "Everdream"); // card to splice
        addTarget(playerA, "Bident of Thassa"); // target left
        addTarget(playerA, "Bow of Nylea"); // target right

        // must used all mana
        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, "Wear // Tear", 1);
        assertGraveyardCount(playerB, "Bident of Thassa", 1);
        assertGraveyardCount(playerB, "Bow of Nylea", 1);
        assertHandCount(playerA, 2); // splice card + draw effect from spliced

        // must used all mana
        assertTappedCount("Island", true, 3);
        assertTappedCount("Mountain", true, 3);
        assertTappedCount("Plains", true, 1);
    }
}
