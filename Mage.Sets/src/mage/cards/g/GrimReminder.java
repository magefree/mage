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
package mage.cards.g;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.Watcher;

/**
 *
 * @author TheElk801
 */
public final class GrimReminder extends CardImpl {

    public GrimReminder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Search your library for a nonland card and reveal it. Each opponent who cast a card this turn with the same name as that card loses 6 life. Then shuffle your library.
        this.getSpellAbility().addEffect(new GrimReminderEffect());
        this.getSpellAbility().addWatcher(new GrimReminderWatcher());

        // {B}{B}: Return Grim Reminder from your graveyard to your hand. Activate this ability only during your upkeep.
        this.addAbility(new ConditionalActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnToHandSourceEffect(),
                new ManaCostsImpl("{B}{B}"),
                new IsStepCondition(PhaseStep.UPKEEP),
                null
        ));
    }

    public GrimReminder(final GrimReminder card) {
        super(card);
    }

    @Override
    public GrimReminder copy() {
        return new GrimReminder(this);
    }
}

class GrimReminderEffect extends OneShotEffect {

    GrimReminderEffect() {
        super(Outcome.Benefit);
        this.staticText = "Search your library for a nonland card and reveal it. "
                + "Each opponent who cast a card this turn with the same name as that card loses 6 life. "
                + "Then shuffle your library.";
    }

    GrimReminderEffect(final GrimReminderEffect effect) {
        super(effect);
    }

    @Override
    public GrimReminderEffect copy() {
        return new GrimReminderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(StaticFilters.FILTER_CARD_NON_LAND);
            if (controller.searchLibrary(target, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    Cards cardsToReveal = new CardsImpl(card);
                    controller.revealCards(sourceObject.getIdName(), cardsToReveal, game);
                    String cardName = card.getName();
                    GrimReminderWatcher watcher = (GrimReminderWatcher) game.getState().getWatchers().get(GrimReminderWatcher.class.getSimpleName());
                    if (watcher != null) {
                        for (UUID playerId : watcher.getPlayersCastSpell(cardName)) {
                            Player player = game.getPlayer(playerId);
                            if (player != null) {
                                player.loseLife(6, game, false);
                            }
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }
}

class GrimReminderWatcher extends Watcher {

    private final Map<String, Set<UUID>> playersCastSpell = new HashMap<>();

    public GrimReminderWatcher() {
        super(GrimReminderWatcher.class.getSimpleName(), WatcherScope.GAME);
    }

    public GrimReminderWatcher(final GrimReminderWatcher watcher) {
        super(watcher);
        for (Map.Entry<String, Set<UUID>> entry : watcher.playersCastSpell.entrySet()) {
            playersCastSpell.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            MageObject spell = game.getObject(event.getTargetId());
            UUID playerId = event.getPlayerId();
            if (playerId != null && spell != null) {
                playersCastSpell.putIfAbsent(spell.getName(), new HashSet());
                playersCastSpell.get(spell.getName()).add(playerId);
            }
        }
    }

    @Override
    public void reset() {
        playersCastSpell.clear();
    }

    public Set<UUID> getPlayersCastSpell(String spellName) {
        return playersCastSpell.getOrDefault(spellName, new HashSet());
    }

    @Override
    public GrimReminderWatcher copy() {
        return new GrimReminderWatcher(this);
    }

}
