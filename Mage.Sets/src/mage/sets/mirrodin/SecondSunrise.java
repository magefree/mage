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
package mage.sets.mirrodin;

import java.util.ArrayList;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class SecondSunrise extends CardImpl<SecondSunrise> {

    public SecondSunrise(UUID ownerId) {
        super(ownerId, 20, "Second Sunrise", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");
        this.expansionSetCode = "MRD";

        this.color.setWhite(true);

        // Each player returns to the battlefield all artifact, creature, enchantment, and land cards in his or her graveyard that were put there from the battlefield this turn.
        this.getSpellAbility().addEffect(new SecondSunriseEffect());
        this.addWatcher(new SecondSunriseWatcher());
    }

    public SecondSunrise(final SecondSunrise card) {
        super(card);
    }

    @Override
    public SecondSunrise copy() {
        return new SecondSunrise(this);
    }
}

class SecondSunriseEffect extends OneShotEffect<SecondSunriseEffect> {

    SecondSunriseEffect() {
        super(Constants.Outcome.PutCardInPlay);
        staticText = "Each player returns to the battlefield all artifact, creature, enchantment, and land cards in his or her graveyard that were put there from the battlefield this turn";
    }

    SecondSunriseEffect(final SecondSunriseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SecondSunriseWatcher watcher = (SecondSunriseWatcher) game.getState().getWatchers().get("SecondSunriseWatcher");
        if (watcher != null) {
            for (UUID id : watcher.cards) {
                Card c = game.getCard(id);
                if (c != null && game.getState().getZone(id) == Constants.Zone.GRAVEYARD) {
                    if (c.getCardType().contains(CardType.ARTIFACT) || c.getCardType().contains(CardType.CREATURE) ||
                        c.getCardType().contains(CardType.ENCHANTMENT) || c.getCardType().contains(CardType.LAND))
                    c.moveToZone(Constants.Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public SecondSunriseEffect copy() {
        return new SecondSunriseEffect(this);
    }
}

class SecondSunriseWatcher extends WatcherImpl<SecondSunriseWatcher> {
    ArrayList<UUID> cards = new ArrayList<UUID>();

    public SecondSunriseWatcher() {
        super("SecondSunriseWatcher", Constants.WatcherScope.GAME);
    }

    public SecondSunriseWatcher(final SecondSunriseWatcher watcher) {
        super(watcher);
        this.cards.addAll(watcher.cards);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            cards.add(event.getTargetId());
        }
    }

    @Override
    public SecondSunriseWatcher copy() {
        return new SecondSunriseWatcher(this);
    }

    @Override
    public void reset() {
        super.reset();
        cards.clear();
    }
}
