
package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.command.Commander;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.*;

/**
 *
 * @author LevelX2
 */
public final class SavageSummoning extends CardImpl {

    public SavageSummoning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Savage Summoning can't be countered.
        Ability ability = new CantBeCounteredSourceAbility();
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // The next creature card you cast this turn can be cast as though it had flash.
        // That spell can't be countered. That creature enters the battlefield with an additional +1/+1 counter on it.
        this.getSpellAbility().addEffect(new SavageSummoningAsThoughEffect());
        this.getSpellAbility().addEffect(new SavageSummoningCantCounterEffect());
        this.getSpellAbility().addEffect(new SavageSummoningEntersBattlefieldEffect());
        this.getSpellAbility().addWatcher(new SavageSummoningWatcher());

    }

    private SavageSummoning(final SavageSummoning card) {
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
        staticText = "The next creature spell you cast this turn can be cast as though it had flash";
    }

    public SavageSummoningAsThoughEffect(final SavageSummoningAsThoughEffect effect) {
        super(effect);
        this.watcher = effect.watcher;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public void init(Ability source, Game game) {
        watcher = game.getState().getWatcher(SavageSummoningWatcher.class, source.getControllerId());
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
                if (commander.isCreature(game) && commander.isControlledBy(source.getControllerId())) {
                    return true;
                }
            } else if (mageObject instanceof Card) {
                Card card = (Card) mageObject;
                if (card.isCreature(game) && card.isOwnedBy(source.getControllerId())) {
                    return true;
                }
            }
        }
        return false;
    }

}

class SavageSummoningWatcher extends Watcher {

    private Set<String> savageSummoningSpells = new HashSet<>();
    private Map<UUID, Set<String>> spellsCastWithSavageSummoning = new LinkedHashMap<>();
    private Map<String, Set<String>> cardsCastWithSavageSummoning = new LinkedHashMap<>();

    public SavageSummoningWatcher() {
        super(WatcherScope.PLAYER);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            if (isSavageSummoningSpellActive() && event.getPlayerId().equals(getControllerId())) {
                Spell spell = game.getStack().getSpell(event.getTargetId());
                if (spell != null && spell.isCreature(game)) {
                    spellsCastWithSavageSummoning.put(spell.getId(), new HashSet<>(savageSummoningSpells));
                    String cardKey = spell.getCard().getId().toString() + '_' + spell.getCard().getZoneChangeCounter(game);
                    cardsCastWithSavageSummoning.put(cardKey, new HashSet<>(savageSummoningSpells));
                    savageSummoningSpells.clear();
                }
            }
        }
    }

    public void setSavageSummoningSpellActive(Card card, Game game) {
        String cardKey = card.getId().toString() + '_' + card.getZoneChangeCounter(game);
        savageSummoningSpells.add(cardKey);
    }

    public boolean isSavageSummoningSpellActive() {
        return !savageSummoningSpells.isEmpty();
    }

    public boolean isSpellCastWithThisSavageSummoning(UUID spellId, UUID cardId, int zoneChangeCounter) {
        String cardKey = cardId.toString() + '_' + zoneChangeCounter;
        Set<String> savageSpells = spellsCastWithSavageSummoning.get(spellId);
        return savageSpells != null && savageSpells.contains(cardKey);
    }

    public boolean isCardCastWithThisSavageSummoning(Card card, UUID cardId, int zoneChangeCounter, Game game) {
        String creatureCardKey = card.getId().toString() + '_' + (card.getZoneChangeCounter(game));
        // add one because card is now gone to battlefield as creature
        String cardKey = cardId.toString() + '_' + zoneChangeCounter;
        Set<String> savageSpells =  cardsCastWithSavageSummoning.get(creatureCardKey);
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
        watcher = game.getState().getWatcher(SavageSummoningWatcher.class, source.getControllerId());
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
        MageObject sourceObject = game.getObject(source);
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
        watcher = game.getState().getWatcher(SavageSummoningWatcher.class, source.getControllerId());
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
            creature.addCounters(CounterType.P1P1.createInstance(), source.getControllerId(), source, game, event.getAppliedEffects());
        }
        discard();
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getTargetId());
        return card != null && watcher.isCardCastWithThisSavageSummoning(card, source.getSourceId(), zoneChangeCounter, game);
    }

}
