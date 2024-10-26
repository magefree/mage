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
    public void test_AI_PlayOnePriority_FromSingle() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 1);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 3);

        // AI must play one time
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);
        // make sure runtime commands can be called with same priority after stack resolve
        checkGraveyardCount("after resolve", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", 1);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 3 - 1);
    }

    @Test
    public void test_AI_PlayOnePriority_FromMultiple() {
        // must choose only 1 spell

        addCard(Zone.HAND, playerA, "Lightning Bolt", 3);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 3);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 5);

        // AI must play one time
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA, false); // stop after first spell

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 5 - 1);
    }

    @Test
    public void test_AI_PlayOnePriority_WithChoicesOnCast() {
        // 1/1
        // {2}{W}, Discard a card: Aven Trooper gets +1/+2 until end of turn.
        addCard(Zone.BATTLEFIELD, playerA, "Aven Trooper", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Plains", 3);
        addCard(Zone.HAND, playerA, "Mountain", 3);

        // AI must play one time and make all choices on cast
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();

        assertPowerToughness(playerA, "Aven Trooper", 1 + 1, 1 + 2);
    }

    @Test
    public void test_AI_PlayOnePriority_WithChoicesOnResolve() {
        // {2}{B}, {T}: Target player discards a card. Activate only as a sorcery.
        addCard(Zone.BATTLEFIELD, playerA, "Cat Burglar", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Swamp", 3);
        //
        addCard(Zone.HAND, playerB, "Mountain", 5);

        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{B}, {T}: Target player", playerB);

        // AI must be able to use choices
        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerB);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertGraveyardCount(playerB, "Mountain", 1);
    }

    @Test
    public void test_AI_PlayManyPrioritiesInOneStep() {
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
    public void test_AI_CleanStepCommands() {
        // some step commands don't have priorities, so it must be clean another way, e.g. on start of the turn
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);

        setStopAt(3, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute(); // on failed clean code it will raise error about not used command
    }

    @Test
    public void test_AI_PlayStep_CallRuntimeCommandsInsideAIControl() {
        // make sure runtime check commands can be called under AI control

        runCode("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertFalse("must be non AI before", player.isComputer());
        });

        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, PhaseStep.END_TURN, playerA);

        checkLife("on same start", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20);
        runCode("on inner step", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
            Assert.assertTrue("must be AI", player.isComputer());
        });
        //
        checkLife("on inner step", 1, PhaseStep.DECLARE_ATTACKERS, playerA, 20);
        runCode("on inner step", 1, PhaseStep.BEGIN_COMBAT, playerA, (info, player, game) -> {
            Assert.assertTrue("must be AI", player.isComputer());
        });
        //
        checkLife("on same end", 1, PhaseStep.END_TURN, playerA, 20);

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_AI_PlayPriority_CallRuntimeCommandsInsideAIControl() {
        // make sure runtime check commands can be called under AI control

        runCode("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertFalse("must be non AI before", player.isComputer());
        });

        aiPlayPriority(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        checkLife("on same start", 1, PhaseStep.PRECOMBAT_MAIN, playerA, 20);
        runCode("on same start", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            Assert.assertFalse("must be non AI after", player.isComputer());
        });

        setStopAt(1, PhaseStep.END_TURN);
        setStrictChooseMode(true);
        execute();
    }

    @Test
    public void test_AI_PlayManyPrioritiesInManySteps() {
        addCard(Zone.HAND, playerA, "Lightning Bolt", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 2);
        //
        addCard(Zone.BATTLEFIELD, playerB, "Balduvian Bears", 1);
        //
        addCard(Zone.HAND, playerA, "Mountain", 1);

        // AI must play multiple actions in multiple steps:
        // - cast bolt
        // - play land on second main
        aiPlayStep(1, PhaseStep.DECLARE_ATTACKERS, PhaseStep.POSTCOMBAT_MAIN, playerA);

        setStopAt(3, PhaseStep.END_TURN); // make sure no land plays on turn 1
        setStrictChooseMode(true);
        execute();

        assertGraveyardCount(playerA, "Lightning Bolt", 1);
        assertPermanentCount(playerB, "Balduvian Bears", 0);
        assertPermanentCount(playerA, "Mountain", 2 + 1); // must play land on second main
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
