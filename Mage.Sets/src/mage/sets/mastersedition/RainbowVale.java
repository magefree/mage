/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.mastersedition;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author andyfries
 */
public class RainbowVale extends CardImpl {

    public RainbowVale(UUID ownerId) {
        super(ownerId, 179, "Rainbow Vale", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "MED";

        // {tap}: Add one mana of any color to your mana pool. An opponent gains control of Rainbow Vale at the beginning of the next end step.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new RainbowValeEffect());
        this.addAbility(ability);
    }

    public RainbowVale(final RainbowVale card) {
        super(card);
    }

    @Override
    public RainbowVale copy() {
        return new RainbowVale(this);
    }

    class RainbowValeEffect extends OneShotEffect {

        public RainbowValeEffect() {
            super(Outcome.PutManaInPool);
            staticText = "An opponent gains control of {this} at the beginning of the next end step.";
        }

        public RainbowValeEffect(final RainbowValeEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new OpponentGainControlEffect());
                delayedAbility.setSourceId(source.getSourceId());
                delayedAbility.setControllerId(source.getControllerId());
                delayedAbility.setSourceObject(source.getSourceObject(game), game);
                game.addDelayedTriggeredAbility(delayedAbility);
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

    public OpponentGainControlEffect(final OpponentGainControlEffect effect) {
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
            permanent.changeControllerId(opponentId, game);
        } else {
            // no valid target exists, effect can be discarded
            discard();
        }
        return true;
    }
}
