package org.mage.test.cards.single.gnt;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.AttackedThisTurnOpponentsCount;
import mage.abilities.effects.Effect;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayersAttackedThisTurnWatcher;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

/**
 * @author JayDi85
 */
public class MilitantAngelTest extends CardTestCommander4Players {

    @Test
    public void test_AttackedThisTurnOpponentsCount() {
        // Player order: A -> D -> C -> B

        // it's testing counter only (no need to test card -- it's same)
        // When Militant Angel enters the battlefield, create a number of 2/2 white Knight creature tokens
        // with vigilance equal to the number of opponents you attacked this turn.
        //addCard(Zone.BATTLEFIELD, playerA, "Militant Angel", 2);
        addCard(Zone.BATTLEFIELD, playerA, "Balduvian Bears", 1);
        addCard(Zone.BATTLEFIELD, playerA, "Kitesail Corsair", 1);

        // turn 1: 2x attack -> 1 player = 1 value
        mustHaveValue("2x->1 = 1", 1, PhaseStep.PRECOMBAT_MAIN, 0);
        attack(1, playerA, "Balduvian Bears", playerB);
        attack(1, playerA, "Kitesail Corsair", playerB);
        mustHaveValue("2x->1 = 1", 1, PhaseStep.POSTCOMBAT_MAIN, 1);

        // between attacks - no value
        mustHaveValue("no attacks = 0", 2, PhaseStep.PRECOMBAT_MAIN, 0);

        // turn 5: 2x attack -> 2 players = 2 value
        mustHaveValue("2x->2 = 2", 5, PhaseStep.PRECOMBAT_MAIN, 0);
        attack(5, playerA, "Balduvian Bears", playerB);
        attack(5, playerA, "Kitesail Corsair", playerC);
        mustHaveValue("2x->2 = 2", 5, PhaseStep.POSTCOMBAT_MAIN, 2);
        runCode("watcher must be copyable", 5, PhaseStep.POSTCOMBAT_MAIN, playerA, (info, player, game) -> {
            PlayersAttackedThisTurnWatcher watcher = game.getState().getWatcher(PlayersAttackedThisTurnWatcher.class);
            PlayersAttackedThisTurnWatcher newWatcher = watcher.copy();
            Assert.assertEquals("old watcher", 2, watcher.getAttackedOpponentsCount(player.getId()));
            Assert.assertEquals("new watcher", 2, newWatcher.getAttackedOpponentsCount(player.getId()));
        });

        // between attacks - no value
        mustHaveValue("no attacks = 0", 6, PhaseStep.PRECOMBAT_MAIN, 0);

        setStrictChooseMode(true);
        setStopAt(6, PhaseStep.END_TURN);
        execute();
    }

    private void mustHaveValue(String needInfo, int turnNum, PhaseStep step, int needValue) {
        runCode(needInfo, turnNum, step, playerA, (info, player, game) -> {
            assertCounterValue(info, player, game, needValue);
        });
    }

    private void assertCounterValue(String checkName, Player player, Game game, int needValue) {
        Ability fakeAbility = new SimpleStaticAbility((Effect) null); // dynamic value need ability's controllerId
        fakeAbility.setControllerId(player.getId());
        Assert.assertEquals(checkName, needValue, AttackedThisTurnOpponentsCount.instance.calculate(game, fakeAbility, null));
    }
}
