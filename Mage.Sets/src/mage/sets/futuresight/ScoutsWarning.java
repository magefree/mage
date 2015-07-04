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
package mage.sets.futuresight;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class ScoutsWarning extends CardImpl {

    public ScoutsWarning(UUID ownerId) {
        super(ownerId, 16, "Scout's Warning", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "FUT";

        // The next creature card you play this turn can be played as though it had flash.
        this.getSpellAbility().addEffect(new ScoutsWarningAsThoughEffect());
        this.getSpellAbility().addWatcher(new ScoutsWarningWatcher());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
    }

    public ScoutsWarning(final ScoutsWarning card) {
        super(card);
    }

    @Override
    public ScoutsWarning copy() {
        return new ScoutsWarning(this);
    }
}

class ScoutsWarningAsThoughEffect extends AsThoughEffectImpl {

    private ScoutsWarningWatcher watcher;
    private int zoneChangeCounter;

    public ScoutsWarningAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next creature card you play this turn can be played as though it had flash";
    }

    public ScoutsWarningAsThoughEffect(final ScoutsWarningAsThoughEffect effect) {
        super(effect);
        this.watcher = effect.watcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
        watcher = (ScoutsWarningWatcher) game.getState().getWatchers().get("consumeScoutsWarningWatcher", source.getControllerId());
        Card card = game.getCard(source.getSourceId());
        if (watcher != null && card != null) {
            zoneChangeCounter = card.getZoneChangeCounter(game);
            watcher.addScoutsWarningSpell(source.getSourceId(), zoneChangeCounter);
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ScoutsWarningAsThoughEffect copy() {
        return new ScoutsWarningAsThoughEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (watcher.isScoutsWarningSpellActive(source.getSourceId(), zoneChangeCounter)) {
            Card card = game.getCard(sourceId);
            if (card != null && card.getCardType().contains(CardType.CREATURE) && source.getControllerId().equals(affectedControllerId)) {
                return true;
            }
        }
        return false;
    }

}

class ScoutsWarningWatcher extends Watcher {

    public List<String> activeScoutsWarningSpells = new ArrayList<>();

    public ScoutsWarningWatcher() {
        super("consumeScoutsWarningWatcher", WatcherScope.PLAYER);
    }

    public ScoutsWarningWatcher(final ScoutsWarningWatcher watcher) {
        super(watcher);
    }

    @Override
    public ScoutsWarningWatcher copy() {
        return new ScoutsWarningWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (!activeScoutsWarningSpells.isEmpty() && event.getPlayerId().equals(getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.getCardType().contains(CardType.CREATURE)) {
                    activeScoutsWarningSpells.clear();
                }
            }
        }
    }

    public void addScoutsWarningSpell(UUID sourceId, int zoneChangeCounter) {
        String spellKey = new StringBuilder(sourceId.toString()).append("_").append(zoneChangeCounter).toString();
        activeScoutsWarningSpells.add(spellKey);
    }

    public boolean isScoutsWarningSpellActive(UUID sourceId, int zoneChangeCounter) {
        String spellKey = new StringBuilder(sourceId.toString()).append("_").append(zoneChangeCounter).toString();
        return activeScoutsWarningSpells.contains(spellKey);
    }

    @Override
    public void reset() {
        super.reset();
        activeScoutsWarningSpells.clear();
    }

}
