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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.MageObjectReference;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public class OtherworldlyJourney extends CardImpl {

    public OtherworldlyJourney(UUID ownerId) {
        super(ownerId, 37, "Otherworldly Journey", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "CHK";
        this.subtype.add("Arcane");
        
        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new OtherworldlyJourneyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public OtherworldlyJourney(final OtherworldlyJourney card) {
        super(card);
    }

    @Override
    public OtherworldlyJourney copy() {
        return new OtherworldlyJourney(this);
    }

}

class OtherworldlyJourneyEffect extends OneShotEffect {

    private static final String effectText = "Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it";

    OtherworldlyJourneyEffect ( ) {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    OtherworldlyJourneyEffect(OtherworldlyJourneyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Otherworldly Journey", source.getSourceId(), game)) {
                ExileZone exile = game.getExile().getExileZone(source.getSourceId());
                // only if permanent is in exile (tokens would be stop to exist)
                if (exile != null && !exile.isEmpty()) {
                    Card card = game.getCard(permanent.getId());
                    if (card != null) {
                        //create delayed triggered ability
                        DelayedTriggeredAbility delayedAbility = 
                                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new OtherworldlyJourneyReturnFromExileEffect(new MageObjectReference(card, game)));
                        delayedAbility.setSourceId(source.getSourceId());
                        delayedAbility.setControllerId(source.getControllerId());
                        delayedAbility.setSourceObject(source.getSourceObject(game), game);
                        game.addDelayedTriggeredAbility(delayedAbility);                    
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public OtherworldlyJourneyEffect copy() {
        return new OtherworldlyJourneyEffect(this);
    }

}

class OtherworldlyJourneyReturnFromExileEffect extends OneShotEffect {

    MageObjectReference objectToReturn;
    public OtherworldlyJourneyReturnFromExileEffect(MageObjectReference objectToReturn) {
        super(Outcome.PutCardInPlay);
        this.objectToReturn = objectToReturn;
        staticText = "return that card to the battlefield under its owner's control with a +1/+1 counter on it";
    }

    public OtherworldlyJourneyReturnFromExileEffect(final OtherworldlyJourneyReturnFromExileEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
    }

    @Override
    public OtherworldlyJourneyReturnFromExileEffect copy() {
        return new OtherworldlyJourneyReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(objectToReturn.getSourceId());
        if (card != null && objectToReturn.refersTo(card, game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                game.addEffect(new OtherworldlyJourneyEntersBattlefieldEffect(objectToReturn), source);                
                owner.putOntoBattlefieldWithInfo(card, game, Zone.EXILED, source.getSourceId());
            }
        }
        return true;
    }
}


class OtherworldlyJourneyEntersBattlefieldEffect extends ReplacementEffectImpl {
    
    MageObjectReference objectToReturn;
    
    public OtherworldlyJourneyEntersBattlefieldEffect(MageObjectReference objectToReturn) {
        super(Duration.Custom, Outcome.BoostCreature);
        this.objectToReturn = objectToReturn;
        staticText = "that card returns to the battlefield with a +1/+1 counter on it";
    }

    public OtherworldlyJourneyEntersBattlefieldEffect(OtherworldlyJourneyEntersBattlefieldEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.ENTERS_THE_BATTLEFIELD.equals(event.getType());
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD) {
            return event.getTargetId().equals(objectToReturn.getSourceId());
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(), game);
            discard(); // use only once
        }
        return false;
    }

    @Override
    public OtherworldlyJourneyEntersBattlefieldEffect copy() {
        return new OtherworldlyJourneyEntersBattlefieldEffect(this);
    }

}
