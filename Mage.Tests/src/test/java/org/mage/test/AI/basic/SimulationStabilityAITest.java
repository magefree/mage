package org.mage.test.AI.basic;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.player.ai.ComputerPlayer;
import mage.player.ai.ComputerPlayer7;
import mage.util.ThreadUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBaseWithAIHelps;

/**
 * Tests for AI simulation stability (AI must process simulations with freezes or errors)
 *
 * @author JayDi85
 */
public class SimulationStabilityAITest extends CardTestPlayerBaseWithAIHelps {

    @Before
    public void prepare() {
        // WARNING, for some reason java 8 sometime can't compile and run test with updated AI settings, so it's ok to freeze on it

        // comment it to enable AI code debug
        Assert.assertFalse("AI stability tests must be run under release config", ComputerPlayer.COMPUTER_DISABLE_TIMEOUT_IN_GAME_SIMULATIONS);
    }

    @Test
    public void test_GameFreeze_OnlyGoodAbilities() {
        removeAllCardsFromLibrary(playerA);

        // possible combinations: from 1 to 3 abilities - all fine
        addFreezeAbility("good 1", false);
        addFreezeAbility("good 2", false);
        addFreezeAbility("good 3", false);

        // AI must activate all +3 life
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_GameFreeze_OnlyFreezeAbilities() {
        removeAllCardsFromLibrary(playerA);

        // possible combinations: from 1 to 3 bad abilities - all bad
        addFreezeAbility("freeze 1", true);
        addFreezeAbility("freeze 2", true);
        addFreezeAbility("freeze 3", true);

        // AI can't finish any simulation and do not choose to activate in real game
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
    }

    @Test
    @Ignore
    // TODO: AI actions simulation do not support multithreading, so whole next action search
    //   will fail on any problem (enable after new simulation implement)
    public void test_GameFreeze_GoodAndFreezeAbilities() {
        removeAllCardsFromLibrary(playerA);

        // possible combinations: some good chains, some bad chains
        addFreezeAbility("good 1", false);
        addFreezeAbility("good 2", false);
        addFreezeAbility("good 3", false);
        addFreezeAbility("freeze 1", true);

        // AI must see and filter bad combinations with freeze ability in the chain
        // so only 1 + 2 + 3 will give best score and will be chosen for real game
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_GameError_OnlyGoodAbilities() {
        removeAllCardsFromLibrary(playerA);

        // possible combinations: from 1 to 3 abilities - all fine
        addErrorAbility("good 1", false);
        addErrorAbility("good 2", false);
        addErrorAbility("good 3", false);

        // AI must activate all +3 life
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
    }

    @Test
    public void test_GameError_OnlyErrorAbilities() {
        removeAllCardsFromLibrary(playerA);

        // it's ok to have error logs in output

        // possible combinations: from 1 to 3 bad abilities - all bad
        addErrorAbility("error 1", true);
        addErrorAbility("error 2", true);
        addErrorAbility("error 3", true);

        // AI can't finish any simulation and do not choose to activate in real game
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20);
    }

    @Test
    @Ignore
    // TODO: AI actions simulation do not support multithreading, so whole next action search
    //   will fail on any problem (enable after new simulation implement)
    public void test_GameError_GoodAndFreezeAbilities() {
        removeAllCardsFromLibrary(playerA);

        // it's ok to have error logs in output

        // possible combinations: some good chains, some bad chains
        addErrorAbility("good 1", false);
        addErrorAbility("good 2", false);
        addErrorAbility("good 3", false);
        addErrorAbility("error 1", true);

        // AI must see and filter bad combinations with error ability in the chain
        // so only 1 + 2 + 3 will give best score and will be chosen for real game
        aiPlayStep(1, PhaseStep.PRECOMBAT_MAIN, playerA);

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();

        assertLife(playerA, 20 + 3);
    }

    private void addFreezeAbility(String name, boolean isFreeze) {
        // change max think timeout to lower value, so test can work faster
        ComputerPlayer7 aiPlayer = (ComputerPlayer7) playerA.getRealPlayer();
        aiPlayer.setMaxThinkTimeSecs(1);

        Effect effect;
        if (isFreeze) {
            effect = new GameFreezeEffect();
        } else {
            effect = new GainLifeEffect(1);
        }
        effect.setText(name);
        addCustomCardWithAbility(name, playerA, new LimitedTimesPerTurnActivatedAbility(effect, new ManaCostsImpl<>("{G}")));
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
    }

    private void addErrorAbility(String name, boolean isError) {
        // change error processing, so test can continue simulations after catch error - like a real game
        playerA.setFastFailInTestMode(false);

        Effect effect;
        if (isError) {
            effect = new GameErrorEffect();
        } else {
            effect = new GainLifeEffect(1);
        }
        effect.setText(name);
        addCustomCardWithAbility(name, playerA, new LimitedTimesPerTurnActivatedAbility(effect, new ManaCostsImpl<>("{G}")));
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 1);
    }
}

class GameFreezeEffect extends OneShotEffect {

    GameFreezeEffect() {
        super(Outcome.Benefit);
    }

    private GameFreezeEffect(final GameFreezeEffect effect) {
        super(effect);
    }

    public GameFreezeEffect copy() {
        return new GameFreezeEffect(this);
    }

    public boolean apply(Game game, Ability source) {
        // freeze simulation, AI must close sim thread by timeout
        System.out.println("apply freeze effect on " + game); // for debug only, show logs from any sim thread
        while (true) {
            ThreadUtils.sleep(1000);
        }
    }
}

class GameErrorEffect extends OneShotEffect {

    GameErrorEffect() {
        super(Outcome.Benefit);
    }

    private GameErrorEffect(final GameErrorEffect effect) {
        super(effect);
    }

    public GameErrorEffect copy() {
        return new GameErrorEffect(this);
    }

    public boolean apply(Game game, Ability source) {
        // error simulation, AI must close error thread, do not use rollback
        System.out.println("apply error effect on " + game); // for debug only, show logs from any sim thread
        throw new IllegalStateException("Test error");
    }
}
