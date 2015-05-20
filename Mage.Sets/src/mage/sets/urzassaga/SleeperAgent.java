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
package mage.sets.urzassaga;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TGower
 */
public class SleeperAgent extends CardImpl {

    public SleeperAgent(UUID ownerId) {
        super(ownerId, 159, "Sleeper Agent", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "USG";
        this.subtype.add("Minion");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sleeper Agent enters the battlefield, target opponent gains control of it.
                this.addAbility(new EntersBattlefieldTriggeredAbility(new SleeperAgentChangeControlEffect(), false));
        // At the beginning of your upkeep, Sleeper Agent deals 2 damage to you.
                this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DamageControllerEffect(2), TargetController.YOU, false));
    }

    public SleeperAgent(final SleeperAgent card) {
        super(card);
    }

    @Override
    public SleeperAgent copy() {
        return new SleeperAgent(this);
    }
}

class SleeperAgentChangeControlEffect extends OneShotEffect {

    public SleeperAgentChangeControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "an opponent gains control of it";
    }

    public SleeperAgentChangeControlEffect(final SleeperAgentChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public SleeperAgentChangeControlEffect copy() {
        return new SleeperAgentChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = new TargetOpponent();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseTarget(outcome, target, source, game)) {
                ContinuousEffect effect = new SleeperAgentGainControlEffect(Duration.Custom, target.getFirstTarget());
                effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

class SleeperAgentGainControlEffect extends ContinuousEffectImpl {

    UUID controller;

    public SleeperAgentGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public SleeperAgentGainControlEffect(final SleeperAgentGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public SleeperAgentGainControlEffect copy() {
        return new SleeperAgentGainControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (targetPointer != null) {
            permanent = game.getPermanent(targetPointer.getFirst(game, source));
        }
        if (permanent != null) {
            return permanent.changeControllerId(controller, game);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Gain control of Sleeper Agent";
    }
}

