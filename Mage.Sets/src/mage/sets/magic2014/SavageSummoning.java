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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
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
import mage.game.command.Commander;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

/**
 *
 * @author LevelX2
 */
public class SavageSummoning extends CardImpl {

    public SavageSummoning(UUID ownerId) {
        super(ownerId, 194, "Savage Summoning", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{G}");
        this.expansionSetCode = "M14";

        // Savage Summoning can't be countered.
        Ability ability = new CantBeCounteredAbility();
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // The next creature card you cast this turn can be cast as though it had flash.
        // That spell can't be countered. That creature enters the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new SavageSummoningAsThoughEffect());
        this.getSpellAbility().addEffect(new SavageSummoningCantCounterEffect());
        this.getSpellAbility().addEffect(new SavageSummoningEntersBattlefieldEffect());
        this.getSpellAbility().addWatcher(new SavageSummoningWatcher());

    }

    public SavageSummoning(final SavageSummoning card) {
        super(card);
    }

    @Override
    public SavageSummoning copy() {
        return new SavageSummoning(this);
    }
}

class SavageSummoningAsThoughEffect extends AsThoughEffectImpl {

    private SavageSummoningWatcher watcher;
    private int zoneChangeCounter;

    public SavageSummoningAsThoughEffect() {
        super(AsThoughEffectType.CAST_AS_INSTANT, Duration.EndOfTurn, Outcome.Benefit);
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
            watcher.setSavageSummoningSpellActive(card, game);
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
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (watcher.isSavageSummoningSpellActive()) {
            MageObject mageObject = game.getBaseObject(objectId);
            if (mageObject instanceof Commander) {
                Commander commander = (Commander) mageObject;
                if (commander.getCardType().contains(CardType.CREATURE) && commander.getControllerId().equals(source.getControllerId())) {
                    return true;
                }
            } else if (mageObject != null && mageObject instanceof Card) {
                Card card = (Card) mageObject;
                if (card.getCardType().contains(CardType.CREATURE) && card.getOwnerId().equals(source.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

}

class SavageSummoningWatcher extends Watcher {

    private Set<String> savageSummoningSpells = new HashSet<>();
    ;
    private Map<UUID, Set<String>> spellsCastWithSavageSummoning = new LinkedHashMap<>();
    private Map<String, Set<String>> cardsCastWithSavageSummoning = new LinkedHashMap<>();

    public SavageSummoningWatcher() {
        super("consumeSavageSummoningWatcher", WatcherScope.PLAYER);
    }

    public SavageSummoningWatcher(final SavageSummoningWatcher watcher) {
        super(watcher);
        this.savageSummoningSpells.addAll(watcher.savageSummoningSpells);
        for (Entry<UUID, Set<String>> entry : watcher.spellsCastWithSavageSummoning.entrySet()) {
            this.spellsCastWithSavageSummoning.put(entry.getKey(), entry.getValue());
        }
        for (Entry<String, Set<String>> entry : watcher.cardsCastWithSavageSummoning.entrySet()) {
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
                    spellsCastWithSavageSummoning.put(spell.getId(), new HashSet<>(savageSummoningSpells));
                    String cardKey = new StringBuilder(spell.getCard().getId().toString()).append("_").append(spell.getCard().getZoneChangeCounter(game)).toString();
                    cardsCastWithSavageSummoning.put(cardKey, new HashSet<>(savageSummoningSpells));
                    savageSummoningSpells.clear();
                }
            }
        }
    }

    public void setSavageSummoningSpellActive(Card card, Game game) {
        String cardKey = new StringBuilder(card.getId().toString()).append("_").append(card.getZoneChangeCounter(game)).toString();
        savageSummoningSpells.add(cardKey);
    }

    public boolean isSavageSummoningSpellActive() {
        return !savageSummoningSpells.isEmpty();
    }

    public boolean isSpellCastWithThisSavageSummoning(UUID spellId, UUID cardId, int zoneChangeCounter) {
        String cardKey = new StringBuilder(cardId.toString()).append("_").append(zoneChangeCounter).toString();
        HashSet<String> savageSpells = (HashSet<String>) spellsCastWithSavageSummoning.get(spellId);
        return savageSpells != null && savageSpells.contains(cardKey);
    }

    public boolean isCardCastWithThisSavageSummoning(Card card, UUID cardId, int zoneChangeCounter, Game game) {
        String creatureCardKey = new StringBuilder(card.getId().toString()).append("_").append(card.getZoneChangeCounter(game) - 1).toString();
        // add one because card is now gone to battlefield as creature
        String cardKey = new StringBuilder(cardId.toString()).append("_").append(zoneChangeCounter).toString();
        HashSet<String> savageSpells = (HashSet<String>) cardsCastWithSavageSummoning.get(creatureCardKey);
        return savageSpells != null && savageSpells.contains(cardKey);
    }

    @Override
    public void reset() {
        super.reset();
        savageSummoningSpells.clear();
        spellsCastWithSavageSummoning.clear();
        cardsCastWithSavageSummoning.clear();
    }

}

class SavageSummoningCantCounterEffect extends ContinuousRuleModifyingEffectImpl {

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
        this.zoneChangeCounter = card.getZoneChangeCounter(game);
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
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            return "This creature spell can't be countered (" + sourceObject.getName() + ").";
        }
        return null;
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

class SavageSummoningEntersBattlefieldEffect extends ReplacementEffectImpl {

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
        this.zoneChangeCounter = card.getZoneChangeCounter(game);
    }

    @Override
    public SavageSummoningEntersBattlefieldEffect copy() {
        return new SavageSummoningEntersBattlefieldEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent creature = ((EntersTheBattlefieldEvent) event).getTarget();
        if (creature != null) {
            creature.addCounters(CounterType.P1P1.createInstance(), game);
        }
        discard();
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getTargetId());
        return card != null && watcher.isCardCastWithThisSavageSummoning(card, source.getSourceId(), zoneChangeCounter, game);
    }

}
