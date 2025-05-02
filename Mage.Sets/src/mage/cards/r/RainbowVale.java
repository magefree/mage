
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author andyfries
 */
public final class RainbowVale extends CardImpl {

    public RainbowVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add one mana of any color. An opponent gains control of Rainbow Vale at the beginning of the next end step.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new RainbowValeEffect());
        this.addAbility(ability);
    }

    private RainbowVale(final RainbowVale card) {
        super(card);
    }

    @Override
    public RainbowVale copy() {
        return new RainbowVale(this);
    }

    static class RainbowValeEffect extends OneShotEffect {

        public RainbowValeEffect() {
            super(Outcome.PutManaInPool);
            staticText = "an opponent gains control of {this} at the beginning of the next end step";
        }

        private RainbowValeEffect(final RainbowValeEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new OpponentGainControlEffect()), source);
                return true;
            }
            return false;
        }

        @Override
        public RainbowValeEffect copy() {
            return new RainbowValeEffect(this);
        }
    }
}

class OpponentGainControlEffect extends ContinuousEffectImpl {

    private UUID opponentId;

    public OpponentGainControlEffect() {
        super(Duration.Custom, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.Detriment);
        this.staticText = "an opponent gains control of {this}";
        opponentId = null;
    }

    private OpponentGainControlEffect(final OpponentGainControlEffect effect) {
        super(effect);
        this.opponentId = effect.opponentId;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            if (game.getOpponents(controller.getId()).size() == 1) {
                opponentId = game.getOpponents(controller.getId()).iterator().next();
            } else {
                Target target = new TargetOpponent(true);
                controller.chooseTarget(outcome, target, source, game);
                opponentId = target.getFirstTarget();
            }
        }
        Player targetOpponent = game.getPlayer(opponentId);
        if (targetOpponent != null && permanent != null) {
            game.informPlayers(permanent.getLogName() + " is now controlled by " + targetOpponent.getLogName());
        } else {
            discard();
        }
    }

    @Override
    public OpponentGainControlEffect copy() {
        return new OpponentGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(opponentId);
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && targetOpponent != null) {
            permanent.changeControllerId(opponentId, game, source);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}
