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
package mage.cards.a;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

/**
 *
 * @author jeffwadsworth
 */
public class AbandonedSarcophagus extends CardImpl {

    public AbandonedSarcophagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // You may cast nonland cards with cycling from your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbandonedSarcophagusCastFromGraveyardEffect()));

        // If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AbandonedSarcophagusReplacementEffect()), new AbandonedSarcophagusWatcher());

    }

    public AbandonedSarcophagus(final AbandonedSarcophagus card) {
        super(card);
    }

    @Override
    public AbandonedSarcophagus copy() {
        return new AbandonedSarcophagus(this);
    }
}

class AbandonedSarcophagusCastFromGraveyardEffect extends AsThoughEffectImpl {

    private static final FilterCard filter = new FilterCard("nonland cards with cycling");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
        filter.add(new AbilityPredicate(CyclingAbility.class));
    }

    AbandonedSarcophagusCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast nonland cards with cycling from your graveyard";
    }

    AbandonedSarcophagusCastFromGraveyardEffect(final AbandonedSarcophagusCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public AbandonedSarcophagusCastFromGraveyardEffect copy() {
        return new AbandonedSarcophagusCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (card != null) {
            return (affectedControllerId.equals(source.getControllerId())
                    && filter.match(card, game)
                    && game.getState().getZone(card.getId()) == Zone.GRAVEYARD);
        }
        return false;
    }
}

class AbandonedSarcophagusReplacementEffect extends ReplacementEffectImpl {

    boolean cardHasCycling;
    boolean cardWasCycledThisTurn;

    public AbandonedSarcophagusReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a card with cycling would be put into your graveyard from anywhere and it wasn't cycled, exile it instead";
    }

    public AbandonedSarcophagusReplacementEffect(final AbandonedSarcophagusReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AbandonedSarcophagusReplacementEffect copy() {
        return new AbandonedSarcophagusReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                return controller.moveCards(card, Zone.EXILED, source, game);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        cardWasCycledThisTurn = false;
        cardHasCycling = false;
        if (((ZoneChangeEvent) event).getToZone() == Zone.GRAVEYARD) {
            Player controller = game.getPlayer(source.getControllerId());
            AbandonedSarcophagusWatcher watcher = (AbandonedSarcophagusWatcher) game.getState().getWatchers().get(AbandonedSarcophagusWatcher.class.getSimpleName());
            Card card = game.getCard(event.getTargetId());
            if (card != null
                    && watcher != null) {
                for (Ability ability : card.getAbilities()) {
                    if (ability instanceof CyclingAbility) {
                        cardHasCycling = true;
                    }
                }
                Cards cards = watcher.getCardsCycledThisTurn(controller.getId());
                for (Card c : cards.getCards(game)) {
                    if (c == card) {
                        cardWasCycledThisTurn = true;
                        watcher.getCardsCycledThisTurn(controller.getId()).remove(card); //remove reference to the card as it is no longer needed
                    }
                }
                return (!cardWasCycledThisTurn
                        && cardHasCycling);
            }
        }
        return false;
    }
}

class AbandonedSarcophagusWatcher extends Watcher {

    private final Map<UUID, Cards> cycledCardsThisTurn = new HashMap<>();

    public AbandonedSarcophagusWatcher() {
        super(AbandonedSarcophagusWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public AbandonedSarcophagusWatcher(final AbandonedSarcophagusWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Cards> entry : watcher.cycledCardsThisTurn.entrySet()) {
            cycledCardsThisTurn.put(entry.getKey(), entry.getValue().copy());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.CYCLE_CARD) {
            Card card = game.getCard(event.getSourceId());
            if (card != null) {
                Cards c = getCardsCycledThisTurn(event.getPlayerId());
                c.add(card);
                cycledCardsThisTurn.put(event.getPlayerId(), c);
            }
        }
    }

    public Cards getCardsCycledThisTurn(UUID playerId) {
        return cycledCardsThisTurn.getOrDefault(playerId, new CardsImpl());
    }

    @Override
    public void reset() {
        super.reset();
        cycledCardsThisTurn.clear();
    }

    @Override
    public AbandonedSarcophagusWatcher copy() {
        return new AbandonedSarcophagusWatcher(this);
    }
}
