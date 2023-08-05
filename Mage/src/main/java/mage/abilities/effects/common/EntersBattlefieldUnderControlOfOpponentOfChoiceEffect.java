package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 * Use this effect only with EntersBattlefieldAbility like abilities
 *
 * @author LevelX2
 */
public class EntersBattlefieldUnderControlOfOpponentOfChoiceEffect extends OneShotEffect {

    public EntersBattlefieldUnderControlOfOpponentOfChoiceEffect() {
        super(Outcome.Benefit);
        staticText = "under the control of an opponent of your choice";
    }

    private EntersBattlefieldUnderControlOfOpponentOfChoiceEffect(final EntersBattlefieldUnderControlOfOpponentOfChoiceEffect effect) {
        super(effect);
    }

    @Override
    public EntersBattlefieldUnderControlOfOpponentOfChoiceEffect copy() {
        return new EntersBattlefieldUnderControlOfOpponentOfChoiceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Target target = new TargetOpponent();
        target.setNotTarget(true);
        if (!controller.choose(Outcome.Benefit, target, source, game)) {
            return false;
        }
        Player opponent = game.getPlayer(target.getFirstTarget());
        if (opponent == null) {
            return false;
        }
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            permanent.setOriginalControllerId(opponent.getId()); // permanent was controlled by this player since the existance of this object so original controller has to be set to the first controller
            permanent.setControllerId(opponent.getId()); // neccessary to set already here because spell caster never controlled the permanent (important for rule 800.4a)
            game.informPlayers(permanent.getLogName() + " enters the battlefield under the control of " + opponent.getLogName());
        }
        ContinuousEffect continuousEffect = new GainControlTargetEffect(
                Duration.Custom, true, opponent.getId()
        );
        continuousEffect.setTargetPointer(new FixedTarget(
                source.getSourceId(), source.getSourceObjectZoneChangeCounter()
        ));
        game.addEffect(continuousEffect, source);
        return true;
    }
}
