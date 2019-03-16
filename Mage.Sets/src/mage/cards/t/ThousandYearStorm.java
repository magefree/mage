package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThousandYearStorm extends CardImpl {

    public ThousandYearStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{R}");

        // Whenever you cast an instant or sorcery spell, copy it for each other instant and sorcery spell you've cast before it this turn. You may choose new targets for the copies.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ThousandYearStormEffect(), new FilterInstantOrSorcerySpell(), false, true
        ), new ThousandYearWatcher());
    }

    public ThousandYearStorm(final ThousandYearStorm card) {
        super(card);
    }

    @Override
    public ThousandYearStorm copy() {
        return new ThousandYearStorm(this);
    }
}

class ThousandYearStormEffect extends OneShotEffect {

    public ThousandYearStormEffect() {
        super(Outcome.Benefit);
        this.staticText = "copy it for each other instant and sorcery spell you've cast before it this turn. You may choose new targets for the copies";
    }

    public ThousandYearStormEffect(final ThousandYearStormEffect effect) {
        super(effect);
    }

    @Override
    public ThousandYearStormEffect copy() {
        return new ThousandYearStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            ThousandYearWatcher watcher = game.getState().getWatcher(ThousandYearWatcher.class);
            if (watcher != null) {
                String stateSearchId = spell.getId().toString() + source.getSourceId().toString();
                // recall only the spells cast before it
                int numberOfCopies = 0;
                if (game.getState().getValue(stateSearchId) != null) {
                    numberOfCopies = (int) game.getState().getValue(stateSearchId);
                }
                if (numberOfCopies > 0) {
                    for (int i = 0; i < numberOfCopies; i++) {
                        spell.createCopyOnStack(game, source, source.getControllerId(), true);
                    }
                }
                return true;
            }
        }
        return false;
    }
}

class ThousandYearWatcher extends Watcher {

    private final Map<UUID, Integer> amountOfInstantSorcerySpellsCastOnCurrentTurn = new HashMap<>();

    public ThousandYearWatcher() {
        super(WatcherScope.GAME);
    }

    public ThousandYearWatcher(final ThousandYearWatcher watcher) {
        super(watcher);
        for (Entry<UUID, Integer> entry : watcher.amountOfInstantSorcerySpellsCastOnCurrentTurn.entrySet()) {
            amountOfInstantSorcerySpellsCastOnCurrentTurn.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && !sourceId.equals(event.getTargetId())) {
            Spell spell = game.getSpellOrLKIStack(event.getTargetId());
            if (spell != null && spell.isInstantOrSorcery()) {
                UUID playerId = event.getPlayerId();
                if (playerId != null) {
                    String stateSearchId = spell.getId().toString() + sourceId.toString();
                    // calc current spell
                    amountOfInstantSorcerySpellsCastOnCurrentTurn.putIfAbsent(playerId, 0);
                    amountOfInstantSorcerySpellsCastOnCurrentTurn.compute(playerId, (k, a) -> a + 1);
                    // remember only the spells cast before it
                    game.getState().setValue(stateSearchId, amountOfInstantSorcerySpellsCastOnCurrentTurn.get(playerId) - 1);
                }
            }
        }
    }

    @Override
    public void reset() {
        amountOfInstantSorcerySpellsCastOnCurrentTurn.clear();
    }

    public int getAmountOfSpellsPlayerCastOnCurrentTurn(UUID playerId) {
        return amountOfInstantSorcerySpellsCastOnCurrentTurn.getOrDefault(playerId, 0);
    }

    @Override
    public ThousandYearWatcher copy() {
        return new ThousandYearWatcher(this);
    }

}
