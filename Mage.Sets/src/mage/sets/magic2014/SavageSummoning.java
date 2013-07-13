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
package mage.sets.magic2014;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.CantCounterAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.WatcherScope;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.WatcherImpl;

/**
 *
 * @author LevelX2
 */
public class SavageSummoning extends CardImpl<SavageSummoning> {

    public SavageSummoning(UUID ownerId) {
        super(ownerId, 194, "Savage Summoning", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "M14";

        this.color.setGreen(true);

        // Savage Summoning can't be countered.
        Ability ability = new CantCounterAbility();
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // The next creature card you cast this turn can be cast as though it had flash. 
        // That spell can't be countered. That creature enters the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new SavageSummoningAsThoughEffect());
        this.getSpellAbility().addEffect(new SavageSummoningCantCounterEffect());
        this.getSpellAbility().addEffect(new SavageSummoningEntersBattlefieldEffect());
        this.addWatcher(new SavageSummoningWatcher());

    }

    public SavageSummoning(final SavageSummoning card) {
        super(card);
    }

    @Override
    public SavageSummoning copy() {
        return new SavageSummoning(this);
    }
}

class SavageSummoningAsThoughEffect extends AsThoughEffectImpl<SavageSummoningAsThoughEffect> {
    private SavageSummoningWatcher watcher;
    private int zoneChangeCounter;

    public SavageSummoningAsThoughEffect() {
        super(AsThoughEffectType.CAST, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "The next creature card you cast this turn can be cast as though it had flash";
    }

    public SavageSummoningAsThoughEffect(final SavageSummoningAsThoughEffect effect) {
        super(effect);
        this.watcher = effect.watcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
         watcher = (SavageSummoningWatcher) game.getState().getWatchers().get("consumeSavageSummoningWatcher", source.getControllerId());
         Card card = game.getCard(source.getSourceId());
         if (watcher != null && card != null) {
             watcher.setSavageSummoningSpellActive(card);
         } else {
             throw new IllegalArgumentException("Consume Savage watcher could not be found");
         }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public SavageSummoningAsThoughEffect copy() {
        return new SavageSummoningAsThoughEffect(this);
    }


    @Override
    public boolean applies(UUID sourceId, Ability source, Game game) {
        if (watcher.isSavageSummoningSpellActive()) {
            Card card = game.getCard(sourceId);
            if (card != null && card.getCardType().contains(CardType.CREATURE) && card.getOwnerId().equals(source.getControllerId())) {
                    return card.getSpellAbility().isInUseableZone(game, card, false);
            }
        }
        return false;
    }

}


class SavageSummoningWatcher extends WatcherImpl<SavageSummoningWatcher> {

    private Set<String> savageSummoningSpells = new HashSet<String>();;
    private Map<UUID, Set<String>> spellsCastWithSavageSummoning = new LinkedHashMap<UUID, Set<String>>();
    private Map<String, Set<String>> cardsCastWithSavageSummoning = new LinkedHashMap<String, Set<String>>();

    public SavageSummoningWatcher() {
        super("consumeSavageSummoningWatcher", WatcherScope.PLAYER);
    }

    public SavageSummoningWatcher(final SavageSummoningWatcher watcher) {
        super(watcher);
        this.savageSummoningSpells.addAll(watcher.savageSummoningSpells);
        for (Entry<UUID, Set<String>> entry :watcher.spellsCastWithSavageSummoning.entrySet()) {
            this.spellsCastWithSavageSummoning.put(entry.getKey(), entry.getValue());
        }
        for (Entry<String, Set<String>> entry :watcher.cardsCastWithSavageSummoning.entrySet()) {
            this.cardsCastWithSavageSummoning.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public SavageSummoningWatcher copy() {
        return new SavageSummoningWatcher(this);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (isSavageSummoningSpellActive() && event.getPlayerId().equals(getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.getCardType().contains(CardType.CREATURE)) {                    
                    spellsCastWithSavageSummoning.put(spell.getId(), new HashSet<String>(savageSummoningSpells));
                    String cardKey = new StringBuilder(spell.getCard().getId().toString()).append("_").append(spell.getCard().getZoneChangeCounter()).toString();
                    cardsCastWithSavageSummoning.put(cardKey, new HashSet<String>(savageSummoningSpells));
                    savageSummoningSpells.clear();
                }
            }
        }
    }

    public void setSavageSummoningSpellActive(Card card) {
        String cardKey = new StringBuilder(card.getId().toString()).append("_").append(card.getZoneChangeCounter()).toString();
        savageSummoningSpells.add(cardKey);
    }

    public boolean isSavageSummoningSpellActive() {
        return !savageSummoningSpells.isEmpty();
    }

    public boolean isSpellCastWithThisSavageSummoning(UUID spellId, UUID cardId, int zoneChangeCounter) {
        String cardKey = new StringBuilder(cardId.toString()).append("_").append(zoneChangeCounter).toString();
        HashSet<String> savageSpells = (HashSet<String>) spellsCastWithSavageSummoning.get(spellId);
        if (savageSpells != null && savageSpells.contains(cardKey)) {
            return true;
        }
        return false;
    }

    public boolean isCardCastWithThisSavageSummoning(Card card, UUID cardId, int zoneChangeCounter) {
        String creatureCardKey = new StringBuilder(card.getId().toString()).append("_").append(card.getZoneChangeCounter()-1).toString();
        // add one because card is now gone to battlefield as creature
        String cardKey = new StringBuilder(cardId.toString()).append("_").append(zoneChangeCounter).toString();
        HashSet<String> savageSpells = (HashSet<String>) cardsCastWithSavageSummoning.get(creatureCardKey);
        if (savageSpells != null && savageSpells.contains(cardKey)) {
            return true;
        }
        return false;
    }

    @Override
    public void reset() {
        super.reset();
        savageSummoningSpells.clear();
        spellsCastWithSavageSummoning.clear();
        cardsCastWithSavageSummoning.clear();
    }

}

class SavageSummoningCantCounterEffect extends ReplacementEffectImpl<SavageSummoningCantCounterEffect> {
    private SavageSummoningWatcher watcher;
    private int zoneChangeCounter;

    public SavageSummoningCantCounterEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "That spell can't be countered";
    }

    public SavageSummoningCantCounterEffect(final SavageSummoningCantCounterEffect effect) {
        super(effect);
        this.watcher = effect.watcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
         watcher = (SavageSummoningWatcher) game.getState().getWatchers().get("consumeSavageSummoningWatcher", source.getControllerId());
         Card card = game.getCard(source.getSourceId());
         if (watcher == null || card == null) {
             throw new IllegalArgumentException("Consume Savage watcher or card could not be found");
         }
         this.zoneChangeCounter = card.getZoneChangeCounter();
    }

    @Override
    public SavageSummoningCantCounterEffect copy() {
        return new SavageSummoningCantCounterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.COUNTER) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && watcher.isSpellCastWithThisSavageSummoning(spell.getId(), source.getSourceId(), zoneChangeCounter)) {
                return true;
            }
        }
        return false;
    }

}

class SavageSummoningEntersBattlefieldEffect extends ReplacementEffectImpl<SavageSummoningEntersBattlefieldEffect> {
    private SavageSummoningWatcher watcher;
    private int zoneChangeCounter;

    public SavageSummoningEntersBattlefieldEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "That creature enters the battlefield with an additional +1/+1 counter on it";
    }

    public SavageSummoningEntersBattlefieldEffect(final SavageSummoningEntersBattlefieldEffect effect) {
        super(effect);
        this.watcher = effect.watcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
         watcher = (SavageSummoningWatcher) game.getState().getWatchers().get("consumeSavageSummoningWatcher", source.getControllerId());
         Card card = game.getCard(source.getSourceId());
         if (watcher == null || card == null) {
             throw new IllegalArgumentException("Consume Savage watcher or card could not be found");
         }
         this.zoneChangeCounter = card.getZoneChangeCounter();
    }

    @Override
    public SavageSummoningEntersBattlefieldEffect copy() {
        return new SavageSummoningEntersBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = game.getPermanent(event.getTargetId());
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), game);
        }
        discard();
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD)) {
            Card card = game.getCard(event.getTargetId());
            if (card != null && watcher.isCardCastWithThisSavageSummoning(card, source.getSourceId(), zoneChangeCounter)) {
                return true;
            }
        }
        return false;
    }

}
