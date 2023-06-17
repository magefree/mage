package org.mage.test.serverside;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GetEmblemTargetPlayerEffect;
import mage.constants.CommanderCardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.command.Emblem;
import mage.game.command.emblems.MomirEmblem;
import mage.target.TargetPlayer;
import mage.view.GameView;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestCommander4Players;

import java.util.UUID;

/**
 * @author JayDi85
 */
public class PlayableEmblemsTest extends CardTestCommander4Players {

    @Test
    public void test_EmblemMustBePlayableInGUI() {
        // possible bug: different emblem's id in commander zone and playable list
        Ability ability = new SimpleActivatedAbility(new GetEmblemTargetPlayerEffect(new MomirEmblem()), new ManaCostsImpl<>(""));
        ability.addTarget(new TargetPlayer());
        addCustomCardWithAbility("test", playerA, ability);

        addCard(Zone.COMMAND, playerA, "Balduvian Bears", 1); // {1}{G}, 2/2, commander
        addCard(Zone.BATTLEFIELD, playerA, "Forest", 2);
        //
        addCard(Zone.COMMAND, playerA, "Goblin Arsonist", 1); // {R}, commander
        //
        addCard(Zone.HAND, playerA, "Mountain", 1); // for emblem's ability

        // prepare emblem
        checkPlayableAbility("before", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, Discard", false);
        activateAbility(1, PhaseStep.PRECOMBAT_MAIN, playerA, "target player gets");
        addTarget(playerA, playerA);
        waitStackResolved(1, PhaseStep.PRECOMBAT_MAIN);
        checkEmblemCount("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Emblem Momir", 1);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "{X}, Discard", true);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Balduvian Bears", true);
        checkPlayableAbility("after", 1, PhaseStep.PRECOMBAT_MAIN, playerA, "Cast Goblin Arsonist", false);

        runCode("check playable", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            // check GUI related code (must run at runtime cause it needs real playable objects)
            GameView gameView = getGameView(playerA);

            // playable commander
            UUID needObjectId = game.getCommandersIds(playerA, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)
                    .stream()
                    .filter(id -> game.getObject(id).getName().equals("Balduvian Bears"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(needObjectId);
            Assert.assertTrue("commander must be playable in GUI", gameView.getCanPlayObjects().containsObject(needObjectId));

            // non playable commander
            needObjectId = game.getCommandersIds(playerA, CommanderCardType.COMMANDER_OR_OATHBREAKER, false)
                    .stream()
                    .filter(id -> game.getObject(id).getName().equals("Goblin Arsonist"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(needObjectId);
            Assert.assertFalse("commander must not be playable in GUI", gameView.getCanPlayObjects().containsObject(needObjectId));

            // playable emblem
            needObjectId = game.getState().getCommand()
                    .stream()
                    .filter(obj -> obj instanceof Emblem)
                    .filter(obj -> obj.isControlledBy(playerA.getId()))
                    .filter(obj -> obj.getName().equals("Emblem Momir"))
                    .map(MageItem::getId)
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(needObjectId);
            Ability currentAbility = playerA.getPlayable(game, true)
                    .stream()
                    .filter(a -> a.toString().startsWith("{X}, Discard"))
                    .findFirst()
                    .orElse(null);
            Assert.assertNotNull(currentAbility);
            Assert.assertEquals("source id must be same", currentAbility.getSourceId(), needObjectId);
            Assert.assertEquals("source object must be same", currentAbility.getSourceObject(game), game.getObject(needObjectId));
            Assert.assertTrue("emblem must be playable in GUI", gameView.getCanPlayObjects().containsObject(needObjectId));
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
