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
package mage.sets.guildpact;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.Constants.AsThoughEffectType;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.WatcherScope;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.DrawCardControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 *
 */
public class Quicken extends CardImpl<Quicken> {

    public Quicken(UUID ownerId) {
        super(ownerId, 31, "Quicken", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "GPT";

        this.color.setBlue(true);

        // The next sorcery card you cast this turn can be cast as though it had flash.
        this.getSpellAbility().addEffect(new QuickenAsThoughEffect());
        this.addWatcher(new QuickenWatcher());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardControllerEffect(1));
    }

    public Quicken(final Quicken card) {
        super(card);
    }

    @Override
    public Quicken copy() {
        return new Quicken(this);
    }
}

class QuickenAsThoughEffect extends AsThoughEffectImpl<QuickenAsThoughEffect> {
    private QuickenWatcher quickenWatcher;
    private int zoneChangeCounter;

    public QuickenAsThoughEffect() {
        super(AsThoughEffectType.CAST, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next sorcery card you cast this turn can be cast as though it had flash";
    }

    public QuickenAsThoughEffect(final QuickenAsThoughEffect effect) {
        super(effect);
        this.quickenWatcher = effect.quickenWatcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
         quickenWatcher = (QuickenWatcher) game.getState().getWatchers().get("consumeQuickenWatcher", source.getControllerId());
         Card card = game.getCard(source.getSourceId());
         if (quickenWatcher != null && card != null) {
             zoneChangeCounter = card.getZoneChangeCounter();
             quickenWatcher.addQuickenSpell(source.getSourceId(), zoneChangeCounter);
         }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public QuickenAsThoughEffect copy() {
        return new QuickenAsThoughEffect(this);
    }


    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (quickenWatcher.isQuickenSpellActive(source.getSourceId(), zoneChangeCounter)) {
            Card card = game.getCard(sourceId);
            if (card != null && card.getCardType().contains(CardType.SORCERY) && card.getOwnerId().equals(source.getControllerId())) {
                    return card.getSpellAbility().isInUseableZone(game, card, false);
            }
        }
        return false;
    }

}

class QuickenWatcher extends WatcherImpl<QuickenWatcher> {

    public List<String> activeQuickenSpells = new ArrayList<String>();

    public QuickenWatcher() {
        super("consumeQuickenWatcher", WatcherScope.PLAYER);
    }

    public QuickenWatcher(final QuickenWatcher watcher) {
        super(watcher);
    }

    @Override
    public QuickenWatcher copy() {
        return new QuickenWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (!activeQuickenSpells.isEmpty() && event.getPlayerId().equals(getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.getCardType().contains(CardType.SORCERY)) {
                    activeQuickenSpells.clear();
                }
            }
        }
    }

    public void addQuickenSpell(UUID sourceId, int zoneChangeCounter) {
        String spellKey = new StringBuilder(sourceId.toString()).append("_").append(zoneChangeCounter).toString();
        activeQuickenSpells.add(spellKey);
    }

    public boolean isQuickenSpellActive(UUID sourceId, int zoneChangeCounter) {
        String spellKey = new StringBuilder(sourceId.toString()).append("_").append(zoneChangeCounter).toString();
        return activeQuickenSpells.contains(spellKey);
    }

    @Override
    public void reset() {
        super.reset();
        activeQuickenSpells.clear();
    }

}
