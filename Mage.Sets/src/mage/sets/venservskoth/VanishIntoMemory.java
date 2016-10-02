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
package mage.sets.venservskoth;

import java.util.UUID;
import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class VanishIntoMemory extends CardImpl {

    public VanishIntoMemory(UUID ownerId) {
        super(ownerId, 31, "Vanish into Memory", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}{U}");
        this.expansionSetCode = "DDI";

        // Exile target creature. You draw cards equal to that creature's power.
        // At the beginning of your next upkeep, return that card to the battlefield under its owner's control. If you do, discard cards equal to that creature's toughness.
        this.getSpellAbility().addEffect(new VanishIntoMemoryEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public VanishIntoMemory(final VanishIntoMemory card) {
        super(card);
    }

    @Override
    public VanishIntoMemory copy() {
        return new VanishIntoMemory(this);
    }
}

class VanishIntoMemoryEffect extends OneShotEffect {

    public VanishIntoMemoryEffect() {
        super(Outcome.Detriment);
        staticText = "Exile target creature. You draw cards equal to that creature's power. At the beginning of your next upkeep, return that card to the battlefield under its owner's control. If you do, discard cards equal to that creature's toughness.";
    }

    public VanishIntoMemoryEffect(final VanishIntoMemoryEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (permanent != null && sourceObject != null) {
            if (permanent.moveToExile(source.getSourceId(), sourceObject.getIdName(), source.getSourceId(), game)) {
                you.drawCards(permanent.getPower().getValue(), game);
                ExileZone exile = game.getExile().getExileZone(source.getSourceId());
                // only if permanent is in exile (tokens would be stop to exist)
                if (exile != null && !exile.isEmpty()) {
                    Card card = game.getCard(permanent.getId());
                    if (card != null) {
                        //create delayed triggered ability
                        game.addDelayedTriggeredAbility(new AtTheBeginOfYourNextUpkeepDelayedTriggeredAbility(
                                new VanishIntoMemoryReturnFromExileEffect(new MageObjectReference(card, game))), source);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public VanishIntoMemoryEffect copy() {
        return new VanishIntoMemoryEffect(this);
    }
}

class VanishIntoMemoryReturnFromExileEffect extends OneShotEffect {

    MageObjectReference objectToReturn;

    public VanishIntoMemoryReturnFromExileEffect(MageObjectReference objectToReturn) {
        super(Outcome.PutCardInPlay);
        this.objectToReturn = objectToReturn;
        staticText = "return that card to the battlefield under its owner's control";
    }

    public VanishIntoMemoryReturnFromExileEffect(final VanishIntoMemoryReturnFromExileEffect effect) {
        super(effect);
        this.objectToReturn = effect.objectToReturn;
    }

    @Override
    public VanishIntoMemoryReturnFromExileEffect copy() {
        return new VanishIntoMemoryReturnFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(objectToReturn.getSourceId());
        if (card != null && objectToReturn.refersTo(card, game)) {
            Player owner = game.getPlayer(card.getOwnerId());
            if (owner != null) {
                game.addEffect(new VanishIntoMemoryEntersBattlefieldEffect(objectToReturn), source);
                owner.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
        }
        return true;
    }
}

class VanishIntoMemoryEntersBattlefieldEffect extends ReplacementEffectImpl {

    MageObjectReference objectToReturn;

    public VanishIntoMemoryEntersBattlefieldEffect(MageObjectReference objectToReturn) {
        super(Duration.Custom, Outcome.BoostCreature);
        this.objectToReturn = objectToReturn;
        staticText = "discard cards equal to that creature's toughness.";
    }

    public VanishIntoMemoryEntersBattlefieldEffect(VanishIntoMemoryEntersBattlefieldEffect effect) {
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
        Permanent permanent = ((EntersTheBattlefieldEvent) event).getTarget();
        if (permanent != null) {
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                you.discard(permanent.getToughness().getValue(), false, source, game);
            }
            discard(); // use only once
        }
        return false;
    }

    @Override
    public VanishIntoMemoryEntersBattlefieldEffect copy() {
        return new VanishIntoMemoryEntersBattlefieldEffect(this);
    }
}
