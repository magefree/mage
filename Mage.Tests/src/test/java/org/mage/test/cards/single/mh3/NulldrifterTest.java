package org.mage.test.cards.single.mh3;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class NulldrifterTest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_AllMana_UseNormalCost() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        // select normal cost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nulldrifter");
        setChoice(playerA, "Cast with no alternative cost");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 1);
        assertGraveyardCount(playerA, "Nulldrifter", 0);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 7);
    }

    @Test
    public void test_AllMana_UseEvokeCost_Human() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        // select evoke cost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nulldrifter");
        setChoice(playerA, "Cast with Evoke alternative cost");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 0);
        assertGraveyardCount(playerA, "Nulldrifter", 1);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 3);
    }

    @Test
    @Ignore // TODO: implement alternative cost choose by AI instead random
    public void test_AllMana_UseEvokeCost_AI() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 7);

        // make sure AI will use evoke cost
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 0);
        assertGraveyardCount(playerA, "Nulldrifter", 1);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 3);
    }

    @Test
    public void test_OnlyEvoke_UseEvokeCost_Human() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // select evoke cost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nulldrifter");
        setChoice(playerA, "Cast with Evoke alternative cost");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 0);
        assertGraveyardCount(playerA, "Nulldrifter", 1);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 3);
    }

    @Test
    @Ignore // TODO: implement alternative cost choose by AI instead random
    public void test_OnlyEvoke_UseEvokeCost_AI() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Island", 3);

        // make sure AI will use evoke cost
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 0);
        assertGraveyardCount(playerA, "Nulldrifter", 1);
        assertHandCount(playerA, 2);
        assertTappedCount("Island", true, 3);
    }

    @Test
    public void test_OnlyNormalCost_UseNormalCost_Human() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        // select normal cost
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Nulldrifter");
        setChoice(playerA, "Cast with no alternative cost");

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 1);
        assertGraveyardCount(playerA, "Nulldrifter", 0);
        assertHandCount(playerA, 2);
        assertTappedCount("Mountain", true, 7);
    }

    @Test
    @Ignore // TODO: implement alternative cost choose by AI instead random
    public void test_OnlyNormalCost_UseNormalCost_AI() {
        // When you cast this spell, draw two cards.
        // Evoke {2}{U} (You may cast this spell for its evoke cost. If you do, it’s sacrificed when it enters.)
        addCard(Zone.HAND, playerA, "Nulldrifter", 1); // {7}
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 7);

        // make sure AI will use normal cost
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Nulldrifter", 1);
        assertGraveyardCount(playerA, "Nulldrifter", 0);
        assertHandCount(playerA, 2);
        assertTappedCount("Mountain", true, 7);
    }
}
