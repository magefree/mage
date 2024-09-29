package org.mage.test.cards.watchers;

import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestPlayerBase;

/**
 * @author JayDi85
 */
public class TempleOfPowerTest extends CardTestPlayerBase {

    private void checkGameView() {
        // game view uses rules text generating, so TempleOfPowerWatcher will be called too
        // original watcher code don't raise game error on miss watcher, but test must fail - so it uses direct key search here
        String needWatcherKey = "TempleOfPowerWatcher";
        GameView gameView = getGameView(playerA);
        Assert.assertNotNull(gameView);
        Assert.assertNotNull("Watchers must be init with game card all the time, miss " + needWatcherKey, currentGame.getState().getWatcher(needWatcherKey));
    }

    @Test
    public void test_TransformRevert() {
        // Ojer Axonil, Deepest Might
        // Temple of Power
        // When Ojer Axonil dies, return it to the battlefield tapped and transformed under its owner’s control.
        addCard(Zone.BATTLEFIELD, playerA, "Ojer Axonil, Deepest Might", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Mountain", 4 + 3);
        addCard(Zone.HAND, playerA, "Lightning Bolt", 4);

        // turn 1

        // kill and transform to back side
        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkGameView());
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ojer Axonil, Deepest Might");
        castSpell(1, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", "Ojer Axonil, Deepest Might");
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after transform to back", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Power", 1);
        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkGameView());

        // turn 3

        // damage and prepare condition for transform
        runCode("check", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkGameView());
        checkPermanentCount("before condition", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ojer Axonil, Deepest Might", 0);
        checkPlayableAbility("before condition", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}, {T}: Transform", false);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        castSpell(3, PhaseStep.PRECOMBAT_MAIN, playerA, "Lightning Bolt", playerA);
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after condition", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ojer Axonil, Deepest Might", 0);
        checkPlayableAbility("after condition", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}, {T}: Transform", true);
        runCode("check", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkGameView());

        // transform to front side
        activateAbility(3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}, {T}: Transform");
        waitStackResolved(3, PhaseStep.PRECOMBAT_MAIN);
        checkPermanentCount("after transform", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Ojer Axonil, Deepest Might", 1);
        checkPermanentCount("after transform", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "Temple of Power ", 0);
        checkPlayableAbility("after transform", 3, PhaseStep.PRECOMBAT_MAIN, playerA, "{2}{R}, {T}: Transform", false);
        runCode("check", 3, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> checkGameView());

        setStrictChooseMode(true);
        setStopAt(3, PhaseStep.END_TURN);
        execute();

        assertPermanentCount(playerA, "Ojer Axonil, Deepest Might", 1);
    }
}
