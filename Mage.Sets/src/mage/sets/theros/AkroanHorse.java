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
package mage.sets.theros;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AkroanHorse extends CardImpl<AkroanHorse> {

    public AkroanHorse(UUID ownerId) {
        super(ownerId, 210, "Akroan Horse", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");
        this.expansionSetCode = "THS";
        this.subtype.add("Horse");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // When Akroan Horse enters the battlefield, an opponent gains control of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AkroanHorseChangeControlEffect(), false));
        // At the beginning of your upkeep, each opponent puts a 1/1 white Soldier creature token onto the battlefield.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, new AkroanHorseCreateTokenEffect(), TargetController.YOU, false));
    }

    public AkroanHorse(final AkroanHorse card) {
        super(card);
    }

    @Override
    public AkroanHorse copy() {
        return new AkroanHorse(this);
    }
}

class AkroanHorseChangeControlEffect extends OneShotEffect<AkroanHorseChangeControlEffect> {

    public AkroanHorseChangeControlEffect() {
        super(Outcome.Benefit);
        this.staticText = "an opponent gains control of it";
    }

    public AkroanHorseChangeControlEffect(final AkroanHorseChangeControlEffect effect) {
        super(effect);
    }

    @Override
    public AkroanHorseChangeControlEffect copy() {
        return new AkroanHorseChangeControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = new TargetOpponent(true);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.chooseTarget(outcome, target, source, game)) {
                ContinuousEffect effect = new AkroanHorseGainControlEffect(Duration.Custom, target.getFirstTarget());
                effect.setTargetPointer(new FixedTarget(source.getSourceId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }
}

class AkroanHorseGainControlEffect extends ContinuousEffectImpl<AkroanHorseGainControlEffect> {

    UUID controller;

    public AkroanHorseGainControlEffect(Duration duration, UUID controller) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controller = controller;
    }

    public AkroanHorseGainControlEffect(final AkroanHorseGainControlEffect effect) {
        super(effect);
        this.controller = effect.controller;
    }

    @Override
    public AkroanHorseGainControlEffect copy() {
        return new AkroanHorseGainControlEffect(this);
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
        return "Gain control of Akroan Horse";
    }
}

class AkroanHorseCreateTokenEffect extends OneShotEffect<AkroanHorseCreateTokenEffect> {

    public AkroanHorseCreateTokenEffect() {
        super(Outcome.Detriment);
        this.staticText = "each opponent puts a 1/1 white Soldier creature token onto the battlefield";
    }

    public AkroanHorseCreateTokenEffect(final AkroanHorseCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public AkroanHorseCreateTokenEffect copy() {
        return new AkroanHorseCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID opponentId: game.getOpponents(source.getControllerId())) {
            Token token = new SoldierToken();
            token.putOntoBattlefield(1, game, source.getSourceId(), opponentId);
        }
        return true;
    }
}
