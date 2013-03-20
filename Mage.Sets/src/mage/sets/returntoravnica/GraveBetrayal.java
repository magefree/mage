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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class GraveBetrayal extends CardImpl<GraveBetrayal> {

    public GraveBetrayal (UUID ownerId) {
        super(ownerId, 67, "Grave Betrayal", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{5}{B}{B}");
        this.expansionSetCode = "RTR";
        this.color.setBlack(true);

        // Whenever a creature you don't control dies, return it to the battlefield under
        // your control with an additional +1/+1 counter on it at the beginning of the
        // next end step. That creature is a black Zombie in addition to its other colors and types.
        this.addAbility(new GraveBetrayalTriggeredAbility());
    }

    public GraveBetrayal (final GraveBetrayal card) {
        super(card);
    }

    @Override
    public GraveBetrayal copy() {
        return new GraveBetrayal(this);
    }
}

class GraveBetrayalTriggeredAbility extends TriggeredAbilityImpl<GraveBetrayalTriggeredAbility> {

    public GraveBetrayalTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, null);
    }

    public GraveBetrayalTriggeredAbility(final GraveBetrayalTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GraveBetrayalTriggeredAbility copy() {
        return new GraveBetrayalTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).getToZone() == Constants.Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getFromZone() == Constants.Zone.BATTLEFIELD) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (permanent != null && !permanent.getControllerId().equals(this.getControllerId()) && permanent.getCardType().contains(CardType.CREATURE)) {
                Card card = (Card)game.getObject(permanent.getId());
                if (card != null) {
                    Effect effect = new GraveBetrayalEffect();
                    effect.setTargetPointer(new FixedTarget(card.getId()));
                    Integer zoneChanges = new Integer(card.getZoneChangeCounter());
                    effect.setValue("zoneChanges", zoneChanges);

                    DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(effect);
                    delayedAbility.setSourceId(this.getSourceId());
                    delayedAbility.setControllerId(this.getControllerId());
                    game.addDelayedTriggeredAbility(delayedAbility);
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

class GraveBetrayalEffect extends OneShotEffect<GraveBetrayalEffect> {

    public GraveBetrayalEffect() {
        super(Constants.Outcome.PutCreatureInPlay);
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
            Integer zoneChanges = (Integer) getValue("zoneChanges");
            if (card.getZoneChangeCounter() == zoneChanges) {
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
        }
        return false;
    }

}

class GraveBetrayalContiniousEffect extends ContinuousEffectImpl<GraveBetrayalContiniousEffect> {

    public GraveBetrayalContiniousEffect() {
        super(Duration.Custom, Constants.Outcome.Neutral);
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
    public boolean apply(Constants.Layer layer, Constants.SubLayer sublayer, Ability source, Game game) {
        Permanent creature = game.getPermanent(targetPointer.getFirst(game, source));
        if (creature != null) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == Constants.SubLayer.NA) {
                        creature.getSubtype().add("Zombie");
                    }
                    break;
                case ColorChangingEffects_5:
                    if (sublayer == Constants.SubLayer.NA) {
                        creature.getColor().setBlack(true);
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
    public boolean hasLayer(Constants.Layer layer) {
        return layer == Constants.Layer.ColorChangingEffects_5 || layer == Constants.Layer.TypeChangingEffects_4;
    }

}