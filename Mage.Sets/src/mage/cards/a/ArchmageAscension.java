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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CardsAmountDrawnThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class ArchmageAscension extends CardImpl {

    public ArchmageAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of each end step, if you drew two or more cards this turn, you may put a quest counter on Archmage Ascension.
        this.addAbility(new ArchmageAscensionTriggeredAbility(), new CardsAmountDrawnThisTurnWatcher());

        // As long as Archmage Ascension has six or more quest counters on it, if you would draw a card,
        // you may instead search your library for a card, put that card into your hand, then shuffle your library.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchmageAscensionReplacementEffect()));

    }

    public ArchmageAscension(final ArchmageAscension card) {
        super(card);
    }

    @Override
    public ArchmageAscension copy() {
        return new ArchmageAscension(this);
    }
}

class ArchmageAscensionTriggeredAbility extends TriggeredAbilityImpl {

    public ArchmageAscensionTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance(1)), true);
    }

    public ArchmageAscensionTriggeredAbility(final ArchmageAscensionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArchmageAscensionTriggeredAbility copy() {
        return new ArchmageAscensionTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.END_TURN_STEP_PRE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent archmage = game.getPermanent(super.getSourceId());
        CardsAmountDrawnThisTurnWatcher watcher
                = (CardsAmountDrawnThisTurnWatcher) game.getState().getWatchers().get(CardsAmountDrawnThisTurnWatcher.class.getSimpleName());
        return archmage != null && watcher != null && watcher.getAmountCardsDrawn(this.getControllerId()) >= 2;
    }

    @Override
    public String getRule() {
        return "At the beginning of each end step, if you drew two or more cards this turn, you may put a quest counter on {this}";
    }
}

class ArchmageAscensionReplacementEffect extends ReplacementEffectImpl {

    public ArchmageAscensionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as {this} has six or more quest counters on it, if you would draw a card, "
                + "you may instead search your library for a card, put that card into your hand, then shuffle your library";
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
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                    player.shuffleLibrary(source, game);
                }
            }
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent archmage = game.getPermanent(source.getSourceId());
        Player you = game.getPlayer(source.getControllerId());
        return event.getPlayerId().equals(source.getControllerId())
                && archmage != null
                && archmage.getCounters(game).getCount(CounterType.QUEST) >= 6
                && you != null
                && you.chooseUse(Outcome.Benefit, "Would you like to search your library instead of drawing a card?", source, game);
    }
}
