package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.StaticAbility;
import mage.abilities.dynamicvalue.common.ControllerSpeedCount;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class StartYourEnginesAbility extends StaticAbility {

    private static final Hint hint = new ValueHint("Your current speed", ControllerSpeedCount.instance);

    public StartYourEnginesAbility() {
        super(Zone.BATTLEFIELD, new StartYourEnginesEffect());
        this.addHint(hint);
    }

    private StartYourEnginesAbility(final StartYourEnginesAbility ability) {
        super(ability);
    }

    @Override
    public StartYourEnginesAbility copy() {
        return new StartYourEnginesAbility(this);
    }

    @Override
    public String getRule() {
        return "start your engines!";
    }
}

class StartYourEnginesEffect extends ContinuousEffectImpl {

    StartYourEnginesEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
    }

    private StartYourEnginesEffect(final StartYourEnginesEffect effect) {
        super(effect);
    }

    @Override
    public StartYourEnginesEffect copy() {
        return new StartYourEnginesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || !player.initSpeed()) {
            return false;
        }
        game.informPlayers(player.getLogName() + "'s speed is now 1.");
        return true;
    }
}
