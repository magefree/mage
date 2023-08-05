
package mage.abilities.effects.common.continuous;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author nantuko, LevelX2
 */
public class MaximumHandSizeControllerEffect extends ContinuousEffectImpl {

    public enum HandSizeModification {
        SET, INCREASE, REDUCE
    }

    protected DynamicValue handSize;
    protected HandSizeModification handSizeModification;
    protected TargetController targetController;

    /**
     * @param handSize             Maximum hand size to set or to reduce by
     * @param duration             Effect duration
     * @param handSizeModification SET, INCREASE, REDUCE
     */
    public MaximumHandSizeControllerEffect(int handSize, Duration duration, HandSizeModification handSizeModification) {
        this(handSize, duration, handSizeModification, TargetController.YOU);
    }

    public MaximumHandSizeControllerEffect(int handSize, Duration duration, HandSizeModification handSizeModification, TargetController targetController) {
        this(StaticValue.get(handSize), duration, handSizeModification, targetController);
    }

    public MaximumHandSizeControllerEffect(DynamicValue handSize, Duration duration, HandSizeModification handSizeModification, TargetController targetController) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, defineOutcome(handSizeModification, targetController));
        this.handSize = handSize;
        this.handSizeModification = handSizeModification;
        this.targetController = targetController;
        setText();
    }

    protected MaximumHandSizeControllerEffect(final MaximumHandSizeControllerEffect effect) {
        super(effect);
        this.handSize = effect.handSize;
        this.handSizeModification = effect.handSizeModification;
        this.targetController = effect.targetController;
    }

    @Override
    public MaximumHandSizeControllerEffect copy() {
        return new MaximumHandSizeControllerEffect(this);
    }

    protected static Outcome defineOutcome(HandSizeModification handSizeModification, TargetController targetController) {
        Outcome newOutcome = Outcome.Benefit;
        if ((targetController == TargetController.YOU || targetController == TargetController.ANY)
                && handSizeModification == HandSizeModification.REDUCE) {
            newOutcome = Outcome.Detriment;
        }
        return newOutcome;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            switch (targetController) {
                case ANY:
                    for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                        setHandSize(game, source, playerId);
                    }
                    break;
                case OPPONENT:
                    for (UUID playerId : game.getOpponents(source.getControllerId())) {
                        setHandSize(game, source, playerId);
                    }
                    break;
                case YOU:
                    setHandSize(game, source, source.getControllerId());
                    break;
                default:
                    throw new UnsupportedOperationException("Not supported yet.");
            }
            return true;
        }
        return false;
    }

    private void setHandSize(Game game, Ability source, UUID playerId) {
        Player player = game.getPlayer(playerId);
        if (player != null) {
            switch (handSizeModification) {
                case SET:
                    player.setMaxHandSize(handSize.calculate(game, source, this));
                    break;
                case INCREASE:
                    player.setMaxHandSize(player.getMaxHandSize() + handSize.calculate(game, source, this));
                    break;
                case REDUCE:
                    player.setMaxHandSize(player.getMaxHandSize() - handSize.calculate(game, source, this));
                    break;
            }
        }
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        if (handSize instanceof StaticValue && ((StaticValue) handSize).getValue() == Integer.MAX_VALUE) {
            switch (targetController) {
                case ANY:
                    sb.append("Players have no maximum hand size");
                    break;
                case OPPONENT:
                    sb.append("Each opponent has no maximum hand size");
                    break;
                case YOU:
                    sb.append("You have no maximum hand size");
                    break;
            }
        } else {
            switch (targetController) {
                case ANY:
                    sb.append("All players maximum hand size");
                    break;
                case OPPONENT:
                    sb.append("Each opponent's maximum hand size");
                    break;
                case YOU:
                    sb.append("Your maximum hand size");
                    break;
            }

            switch (handSizeModification) {
                case SET:
                    sb.append(" is ");
                    break;
                case INCREASE:
                    sb.append(" is increased by ");
                    break;
                case REDUCE:
                    sb.append(" is reduced by ");
                    break;
            }

            if (handSize instanceof StaticValue) {
                sb.append(CardUtil.numberToText(((StaticValue) handSize).getValue()));
            } else if (!(handSize instanceof StaticValue)) {
                sb.append(handSize.getMessage());
            }
        }

        if (duration == Duration.EndOfGame) {
            sb.append(" for the rest of the game");
        }
        staticText = sb.toString();
    }

}
