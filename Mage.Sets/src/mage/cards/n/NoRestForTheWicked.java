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
package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author anonymous
 */
public class NoRestForTheWicked extends CardImpl {

    public NoRestForTheWicked(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}");

        // Sacrifice No Rest for the Wicked: Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new NoRestForTheWickedEffect(), new SacrificeSourceCost());
        Watcher watcher = new NoRestForTheWickedWatcher();
        addAbility(ability, watcher);
    }

    public NoRestForTheWicked(final NoRestForTheWicked card) {
        super(card);
    }

    @Override
    public NoRestForTheWicked copy() {
        return new NoRestForTheWicked(this);
    }
}

class NoRestForTheWickedEffect extends OneShotEffect {

    NoRestForTheWickedEffect() {
        super(Outcome.Sacrifice);
        staticText = "Return to your hand all creature cards in your graveyard that were put there from the battlefield this turn";
    }

    NoRestForTheWickedEffect(final NoRestForTheWickedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        NoRestForTheWickedWatcher watcher = (NoRestForTheWickedWatcher) game.getState().getWatchers().get(NoRestForTheWickedWatcher.class.getSimpleName());
        Player controller = game.getPlayer(source.getControllerId());
        if (watcher != null && controller != null) {
            Cards cardsToHand = new CardsImpl();
            for (UUID cardId : watcher.cards) {
                Card c = game.getCard(cardId);
                if (c != null) {
                    if (game.getState().getZone(cardId) == Zone.GRAVEYARD
                            && c.isCreature()
                            && c.getOwnerId().equals(source.getControllerId())) {
                        cardsToHand.add(c);
                    }
                }
            }
            controller.moveCards(cardsToHand, Zone.HAND, source, game);
            return true;
        }
        return false;
    }

    @Override
    public NoRestForTheWickedEffect copy() {
        return new NoRestForTheWickedEffect(this);

    }
}

class NoRestForTheWickedWatcher extends Watcher {

    List<UUID> cards;

    public NoRestForTheWickedWatcher() {
        super(NoRestForTheWickedWatcher.class.getSimpleName(), WatcherScope.GAME);
        this.cards = new ArrayList<>();
    }

    public NoRestForTheWickedWatcher(final NoRestForTheWickedWatcher watcher) {
        super(watcher);
        this.cards = new ArrayList<>();
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE
                && ((ZoneChangeEvent) event).isDiesEvent()) {
            //400.3 Intercept only the controller's events
            cards.add(event.getTargetId());
        }
    }

    @Override
    public NoRestForTheWickedWatcher copy() {
        return new NoRestForTheWickedWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
