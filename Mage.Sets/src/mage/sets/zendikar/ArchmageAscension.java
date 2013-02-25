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
package mage.sets.zendikar;

import mage.Constants;
import mage.Constants.*;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.WatcherImpl;

import java.util.UUID;

/**
 *
 * @author jeffwadsworth
 */
public class ArchmageAscension extends CardImpl<ArchmageAscension> {

    public ArchmageAscension(UUID ownerId) {
        super(ownerId, 42, "Archmage Ascension", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");
        this.expansionSetCode = "ZEN";

        this.color.setBlue(true);

        // At the beginning of each end step, if you drew two or more cards this turn, you may put a quest counter on Archmage Ascension.
        this.addAbility(new ArchmageAscensionTriggeredAbility());
        this.addWatcher(new CardsDrawnControllerWatcher());

        // As long as Archmage Ascension has six or more quest counters on it, if you would draw a card, you may instead search your library for a card, put that card into your hand, then shuffle your library.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ArchmageAscensionReplacementEffect()));
        
    }

    public ArchmageAscension(final ArchmageAscension card) {
        super(card);
    }

    @Override
    public ArchmageAscension copy() {
        return new ArchmageAscension(this);
    }
}

class ArchmageAscensionTriggeredAbility extends TriggeredAbilityImpl<ArchmageAscensionTriggeredAbility> {

    public ArchmageAscensionTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance(1)), true);
    }

    public ArchmageAscensionTriggeredAbility(final ArchmageAscensionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArchmageAscensionTriggeredAbility copy() {
        return new ArchmageAscensionTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent archmage = game.getPermanent(super.getSourceId());
        CardsDrawnControllerWatcher watcher = (CardsDrawnControllerWatcher) game.getState().getWatchers().get("CardsControllerDrawn");
        if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE && archmage != null && watcher != null && watcher.conditionMet()) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, if you drew two or more cards this turn, you may put a quest counter on Archmage Ascension.";
    }
}

class CardsDrawnControllerWatcher extends WatcherImpl<CardsDrawnControllerWatcher> {

    int cardsDrawn;

    public CardsDrawnControllerWatcher() {
        super("CardsControllerDrawn", WatcherScope.GAME);
    }

    public CardsDrawnControllerWatcher(final CardsDrawnControllerWatcher watcher) {
        super(watcher);
        this.cardsDrawn = watcher.cardsDrawn;
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DREW_CARD
                && event.getPlayerId().equals(controllerId)) {
            cardsDrawn += 1;
            if (cardsDrawn >= 2) {
                condition = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        cardsDrawn = 0;
    }

    @Override
    public CardsDrawnControllerWatcher copy() {
        return new CardsDrawnControllerWatcher(this);
    }
}

class ArchmageAscensionReplacementEffect extends ReplacementEffectImpl<ArchmageAscensionReplacementEffect> {

    public ArchmageAscensionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as Archmage Ascension has six or more quest counters on it, if you would draw a card, you may instead search your library for a card, put that card into your hand, then shuffle your library";
    }

    public ArchmageAscensionReplacementEffect(final ArchmageAscensionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ArchmageAscensionReplacementEffect copy() {
        return new ArchmageAscensionReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (player.searchLibrary(target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    card.moveToZone(Constants.Zone.HAND, id, game, false);
                    player.shuffleLibrary(game);
                }
            }
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent archmage = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        if (event.getType() == EventType.DRAW_CARD
                && event.getPlayerId().equals(source.getControllerId())
                && archmage != null
                && archmage.getCounters().getCount(CounterType.QUEST) >= 6
                && you != null
                && you.chooseUse(Constants.Outcome.Benefit, "Would you like to search you library instead of drawing a card?", game)) {
            return true;
        }
        return false;
    }
}
