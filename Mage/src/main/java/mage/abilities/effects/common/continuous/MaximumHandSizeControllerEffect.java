/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
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

    public static enum HandSizeModification {
        SET, INCREASE, REDUCE
    };

    protected DynamicValue handSize;
    protected HandSizeModification handSizeModification;
    protected TargetController targetController;

    /**
     * @param handSize Maximum hand size to set or to reduce by
     * @param duration Effect duration
     * @param handSizeModification SET, INCREASE, REDUCE
     *
     */
    public MaximumHandSizeControllerEffect(int handSize, Duration duration, HandSizeModification handSizeModification) {
        this(handSize, duration, handSizeModification, TargetController.YOU);
    }

    public MaximumHandSizeControllerEffect(int handSize, Duration duration, HandSizeModification handSizeModification, TargetController targetController) {
        this(new StaticValue(handSize), duration, handSizeModification, targetController);
    }

    public MaximumHandSizeControllerEffect(DynamicValue handSize, Duration duration, HandSizeModification handSizeModification, TargetController targetController) {
        super(duration, Layer.PlayerEffects, SubLayer.NA, defineOutcome(handSizeModification, targetController));
        this.handSize = handSize;
        this.handSizeModification = handSizeModification;
        this.targetController = targetController;
        setText();
    }

    public MaximumHandSizeControllerEffect(final MaximumHandSizeControllerEffect effect) {
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
        if ((targetController.equals(TargetController.YOU) || targetController.equals(TargetController.ANY))
                && handSizeModification.equals(HandSizeModification.REDUCE)) {
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
        switch (targetController) {
            case ANY:
                if (handSize instanceof StaticValue && ((StaticValue) handSize).getValue() == Integer.MAX_VALUE) {
                    sb.append("All players have no ");
                } else {
                    sb.append("All players ");
                }
                break;
            case OPPONENT:
                if (handSize instanceof StaticValue && ((StaticValue) handSize).getValue() == Integer.MAX_VALUE) {
                    sb.append("Each opponent has no ");
                } else {
                    sb.append("Each opponent's ");
                }
                break;
            case YOU:
                if (handSize instanceof StaticValue && ((StaticValue) handSize).getValue() == Integer.MAX_VALUE) {
                    sb.append("You have no ");
                } else {
                    sb.append("Your ");
                }
                break;
        }
        sb.append("maximum hand size");
        if (handSizeModification.equals(HandSizeModification.INCREASE)) {
            sb.append(" is increased by ");
        } else if (handSizeModification.equals(HandSizeModification.REDUCE)) {
            sb.append(" is reduced by ");
        } else if ((handSize instanceof StaticValue && ((StaticValue) handSize).getValue() == Integer.MAX_VALUE) || !(handSize instanceof StaticValue)) {
            sb.append(" is ");
        }
        if ((handSize instanceof StaticValue && ((StaticValue) handSize).getValue() != Integer.MAX_VALUE)) {
            sb.append(CardUtil.numberToText(((StaticValue) handSize).getValue()));
        } else if (!(handSize instanceof StaticValue)) {
            sb.append(handSize.getMessage());
        }
        if (duration == Duration.EndOfGame) {
            sb.append(" for the rest of the game");
        }
        staticText = sb.toString();
    }

}
