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
package mage.sets.returntoravnica;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class GraveBetrayal extends CardImpl {

    public GraveBetrayal(UUID ownerId) {
        super(ownerId, 67, "Grave Betrayal", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");
        this.expansionSetCode = "RTR";

        // Whenever a creature you don't control dies, return it to the battlefield under
        // your control with an additional +1/+1 counter on it at the beginning of the
        // next end step. That creature is a black Zombie in addition to its other colors and types.
        this.addAbility(new GraveBetrayalTriggeredAbility());
    }

    public GraveBetrayal(final GraveBetrayal card) {
        super(card);
    }

    @Override
    public GraveBetrayal copy() {
        return new GraveBetrayal(this);
    }
}

class GraveBetrayalTriggeredAbility extends TriggeredAbilityImpl {

    public GraveBetrayalTriggeredAbility() {
        super(Zone.BATTLEFIELD, null);
    }

    public GraveBetrayalTriggeredAbility(final GraveBetrayalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GraveBetrayalTriggeredAbility copy() {
        return new GraveBetrayalTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (permanent != null && !permanent.getControllerId().equals(this.getControllerId()) && permanent.getCardType().contains(CardType.CREATURE)) {
                Card card = (Card) game.getObject(permanent.getId());
                if (card != null) {
                    Effect effect = new GraveBetrayalEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game)));
                    DelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(effect);
                    game.addDelayedTriggeredAbility(delayedAbility, this);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you don't control dies, return it to the battlefield under your control with an additional +1/+1 counter on it at the beginning of the next end step. That creature is a black Zombie in addition to its other colors and types.";
    }
}

class GraveBetrayalEffect extends OneShotEffect {

    public GraveBetrayalEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = " return the creature to the battlefield under your control with an additional +1/+1 counter. That creature is a black Zombie in addition to its other colors and types";
    }

    public GraveBetrayalEffect(final GraveBetrayalEffect effect) {
        super(effect);
    }

    @Override
    public GraveBetrayalEffect copy() {
        return new GraveBetrayalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(targetPointer.getFirst(game, source));
        if (card != null) {
            Zone currentZone = game.getState().getZone(card.getId());
            if (card.putOntoBattlefield(game, currentZone, source.getSourceId(), source.getControllerId())) {
                Permanent creature = game.getPermanent(card.getId());
                creature.addCounters(CounterType.P1P1.createInstance(), game);
                ContinuousEffect effect = new GraveBetrayalContiniousEffect();
                effect.setTargetPointer(new FixedTarget(creature.getId()));
                game.addEffect(effect, source);
                return true;
            }
        }
        return false;
    }

}

class GraveBetrayalContiniousEffect extends ContinuousEffectImpl {

    public GraveBetrayalContiniousEffect() {
        super(Duration.Custom, Outcome.Neutral);
        staticText = "That creature is a black Zombie in addition to its other colors and types";
    }

    public GraveBetrayalContiniousEffect(final GraveBetrayalContiniousEffect effect) {
        super(effect);
    }

    @Override
    public GraveBetrayalContiniousEffect copy() {
        return new GraveBetrayalContiniousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        creature.getSubtype(game).add("Zombie");
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == SubLayer.NA) {
                        creature.getColor(game).setBlack(true);
                    }
                    break;
            }
            return true;
        } else {
            this.used = true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

}
