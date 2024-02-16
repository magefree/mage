package org.mage.test.AI.basic;

import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.permanent.Permanent;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * @author JayDi85
 */
public class TestFrameworkCanPlayAITest extends CardTestPlayerBaseWithAIHelps {

    @Test
    public void test_AI_PlayOnePriorityAction() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 5);

        // AI must play one time
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 5 - 1);
    }

    @Test
    public void test_AI_PlayManyActionsInOneStep() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 5);

        // AI must play all step actions
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 3);
        assertPermanentCount(playerB, "Balduvian Bears", 5 - 3);
    }

    @Test
    public void test_AI_Attack() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);

        // AI must attack
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertLife(playerB, 20 - 2);
    }

    @Test
    public void test_AI_Block() {
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // AI must block
        attack(1, playerA, "Balduvian Bears");
        aiPlayStep(1, PhaseStep.DECLARE_BLOCKERS, playerB);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, 1);
        assertGraveyardCount(playerB, 1);
        assertLife(playerB, 20);
    }

    @Test
    @Ignore // AI can't play blade cause score system give priority for boost instead restriction effects like goad
    public void test_AI_GoadedByBloodthirstyBlade_Normal() {
        // Equipped creature gets +2/+0 and is goaded
        // {1}: Attach Bloodthirsty Blade to target creature an opponent controls. Activate this ability only any time you could cast a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Bloodthirsty Blade", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);

        // AI must play
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        Assert.assertEquals(1, currentGame.getBattlefield().getAllActivePermanents(CardType.CREATURE, currentGame).size());
        Permanent permanent = currentGame.getBattlefield().getAllActivePermanents(CardType.CREATURE, currentGame).get(0);
        Assert.assertEquals(1, permanent.getAttachments().size());
    }
}
