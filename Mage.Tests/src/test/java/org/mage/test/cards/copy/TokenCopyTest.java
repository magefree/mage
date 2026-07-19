package org.mage.test.cards.copy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

import mage.ObjectColor;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class TokenCopyTest extends CardTestPlayerBase {

    private static final String rite = "Rite of Replication";
    private static final String prowler = "Kessig Prowler";
    private static final String predator = "Sinuous Predator";
    private static final String brink = "Back from the Brink";

    private void checkProwlers(int prowlerCount, int predatorCount) {
        assertPermanentCount(playerA, prowler, prowlerCount);
        assertPermanentCount(playerA, predator, predatorCount);
        for (Permanent permanent : currentGame.getBattlefield().getAllActivePermanents()) {
            String copyPrefix = permanent.isCopy() ? "copy of " : "";
            int needManaValue;
            switch (permanent.getName()) {
                case prowler:
                    Assertions.assertEquals(2, permanent.getPower().getValue(), "Power of " + copyPrefix + prowler + " should be 2");
                    Assertions.assertEquals(1, permanent.getToughness().getValue(), "Toughness of " + copyPrefix + prowler + " should be 1");
                    Assertions.assertEquals(ObjectColor.GREEN, permanent.getColor(currentGame), copyPrefix + prowler + " should be green");
                    Assertions.assertTrue(permanent.hasSubtype(SubType.WEREWOLF, currentGame), copyPrefix + prowler + " should be a Werewolf");
                    Assertions.assertTrue(permanent.hasSubtype(SubType.HORROR, currentGame), copyPrefix + prowler + " should be a Horror");
                    Assertions.assertFalse(permanent.hasSubtype(SubType.ELDRAZI, currentGame), copyPrefix + prowler + " should not be an Eldrazi");
                    // front side of non-modal transforming dfc - mana value from front side
                    needManaValue = 1;
                    Assertions.assertEquals(needManaValue, permanent.getManaValue(), copyPrefix + prowler + " should have mana value");
                    Assertions.assertFalse(permanent.isTransformed(), copyPrefix + prowler + " should not be transformed");
                    break;
                case predator:
                    Assertions.assertEquals(4, permanent.getPower().getValue(), "Power of " + copyPrefix + predator + " should be 4");
                    Assertions.assertEquals(4, permanent.getToughness().getValue(), "Toughness of " + copyPrefix + predator + " should be 4");
                    Assertions.assertTrue(permanent.getColor(currentGame).isColorless(), copyPrefix + predator + " should be colorless");
                    Assertions.assertTrue(permanent.hasSubtype(SubType.ELDRAZI, currentGame), copyPrefix + predator + " should be an Eldrazi");
                    Assertions.assertTrue(permanent.hasSubtype(SubType.WEREWOLF, currentGame), copyPrefix + predator + " should be a Werewolf");
                    Assertions.assertFalse(permanent.hasSubtype(SubType.HORROR, currentGame), copyPrefix + predator + " should not be a Horror");
                    // back side of non-modal transforming dfc - mana value:
                    // - for original: from front side
                    // - for copy: 0
                    // 712.8e
                    // While a nonmodal double-faced permanent has its back face up, it has only the characteristics of its back face. 
                    // However, its mana value is calculated using the mana cost of its front face. 
                    // If a permanent is copying the back face of a nonmodal double-faced permanent (even if the object representing 
                    // that copy is itself a double-faced permanent), the mana value of that permanent is 0. See rule 202.3b.
                    needManaValue = permanent.isCopy() ? 0 : 1;
                    Assertions.assertEquals(needManaValue, permanent.getManaValue(), copyPrefix + predator + " should have mana value");
                    Assertions.assertTrue(permanent.isTransformed(), copyPrefix + prowler + " should be transformed");
                    break;
            }
        }
    }

    @Test
    public void testCopyDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Island", 4);
        addCard(Zone.BATTLEFIELD, playerA, prowler);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite, prowler);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        checkProwlers(1 + 1, 0);
    }

    @Test
    public void testCopyDFCAndTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 5 + 5 + 4);
        addCard(Zone.BATTLEFIELD, playerA, prowler);
        addCard(Zone.HAND, playerA, rite);

        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite, prowler);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{G}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{G}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        checkProwlers(0, 1 + 1);
    }

    @Test
    public void testCopyTransformedDFC() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 5 + 4);
        addCard(Zone.BATTLEFIELD, playerA, prowler);
        addCard(Zone.HAND, playerA, rite);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{4}{G}");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, rite, predator);

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerA, rite, 1);
        checkProwlers(0, 1 + 1);
    }

    @Test
    public void testBackFromTheBrink() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 6 + 1);
        addCard(Zone.BATTLEFIELD, playerA, brink);
        addCard(Zone.GRAVEYARD, playerA, prowler);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, prowler, 1);
        checkProwlers(1, 0);
    }

    @Test
    public void testBackFromTheBrinkTransform() {
        addCard(Zone.BATTLEFIELD, playerA, "Tropical Island", 6 + 1 + 5);
        addCard(Zone.BATTLEFIELD, playerA, brink);
        addCard(Zone.GRAVEYARD, playerA, prowler);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Exile");
        activateAbility(1, PhaseStep.POSTCOMBAT_MAIN, playerA, "{4}{G}");

        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertExileCount(playerA, prowler, 1);
        checkProwlers(0, 1);
    }
}
