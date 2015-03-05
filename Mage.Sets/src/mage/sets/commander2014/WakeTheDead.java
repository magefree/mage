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
package mage.sets.commander2014;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class WakeTheDead extends CardImpl {

    public WakeTheDead(UUID ownerId) {
        super(ownerId, 31, "Wake the Dead", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{X}{B}{B}");
        this.expansionSetCode = "C14";

        this.color.setBlack(true);

        // Cast Wake the Dead only during combat on an opponent's turn.
        Ability ability = new SimpleStaticAbility(Zone.ALL, new WakeTheDeadEffect());
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the beginning of the next end step.
        this.getSpellAbility().addEffect(new WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect());
        this.getSpellAbility().addTarget(new TargetCardInYourGraveyard(0,Integer.MAX_VALUE, new FilterCreatureCard("creature cards from your graveyard")));
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            int xValue = ability.getManaCostsToPay().getX();
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInYourGraveyard(xValue,xValue, new FilterCreatureCard("creature cards from your graveyard")));
        }
    }

    public WakeTheDead(final WakeTheDead card) {
        super(card);
    }

    @Override
    public WakeTheDead copy() {
        return new WakeTheDead(this);
    }
}

class WakeTheDeadEffect extends ContinuousRuleModifyingEffectImpl {

    WakeTheDeadEffect() {
        super(Duration.EndOfGame, Outcome.Detriment);
        staticText = "Cast {this} only during combat on an opponent's turn";
    }

    WakeTheDeadEffect(final WakeTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            if (game.getPhase().getType().equals(TurnPhase.COMBAT)) {
                return !game.getOpponents(source.getControllerId()).contains(game.getActivePlayerId());
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public WakeTheDeadEffect copy() {
        return new WakeTheDeadEffect(this);
    }
}

class WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect extends OneShotEffect {

    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Return X target creature cards from your graveyard to the battlefield. Sacrifice those creatures at the beginning of the next end step";
    }

    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect(final WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect effect) {
        super(effect);
    }

    @Override
    public WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect copy() {
        return new WakeTheDeadReturnFromGraveyardToBattlefieldTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null) {
                    if (player.putOntoBattlefieldWithInfo(card, game, Zone.GRAVEYARD, source.getSourceId(), false)) {
                        Permanent permanent = game.getPermanent(source.getSourceId());
                        if (permanent != null) {
                            permanent.changeControllerId(source.getControllerId(), game);
                            Effect effect = new SacrificeTargetEffect("Sacrifice those creatures at the beginning of the next end step");
                            effect.setTargetPointer(new FixedTarget(permanent.getId()));
                            DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                            delayedAbility.setSourceId(source.getSourceId());
                            delayedAbility.setControllerId(source.getControllerId());
                            delayedAbility.setSourceObject(source.getSourceObject(game));
                            game.addDelayedTriggeredAbility(delayedAbility);
                        }

                    }
                }
            }
            return true;
        }
        return false;
    }

}
