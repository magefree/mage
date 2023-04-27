package mage.cards.t;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.WatcherScope;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

/**
 * @author jasc7636
 */
public final class ThousandYearStorm extends CardImpl {

    public ThousandYearStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}{R}");

        // Whenever you cast an instant or sorcery spell, copy it for each other instant and sorcery spell you've cast before it this turn. You may choose new targets for the copies.
        this.addAbility(new ThousandYearStormAbility(), new ThousandYearStormWatcher());
    }

    private ThousandYearStorm(final ThousandYearStorm card) {
        super(card);
    }

    @Override
    public ThousandYearStorm copy() {
        return new ThousandYearStorm(this);
    }
}

class ThousandYearStormAbility extends SpellCastControllerTriggeredAbility {

    private String stormCountInfo;

    public ThousandYearStormAbility() {
        super(Zone.BATTLEFIELD, new ThousandYearStormEffect(), StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false, true);
        this.addHint(new ValueHint("You've cast instant and sorcery this turn", ThousandYearStormSpellsCastThatTurnValue.instance));
        this.stormCountInfo = null;
    }

    public ThousandYearStormAbility(final ThousandYearStormAbility ability) {
        super(ability);
        this.stormCountInfo = ability.stormCountInfo;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            ThousandYearStormWatcher watcher = game.getState().getWatcher(ThousandYearStormWatcher.class);
            if (watcher == null) {
                return false;
            }
            UUID playerId = event.getPlayerId();
            List<MageObjectReference> spellsCast = watcher.getSpellsThisTurn(playerId);
            MageObject object = game.getObject(event.getTargetId());
            if (object == null || spellsCast == null) {
                return false;
            }
            int stormCount = 0;
            for (MageObjectReference mor : spellsCast) {
                stormCount++;
                if (mor.refersTo(object, game)) {
                    break;
                }
            }
            stormCount = Math.max(0, stormCount - 1);
            stormCountInfo = " (<b>storm count: " + stormCount + "</b>) ";
            for (Effect effect : this.getEffects()) {
                if (effect instanceof ThousandYearStormEffect) {
                    ((ThousandYearStormEffect) effect).setStormCount(stormCount);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an instant or sorcery spell, copy it for each other instant and sorcery spell you've cast before it this turn"
                + (stormCountInfo != null ? stormCountInfo : "") + ". You may choose new targets for the copies.";
    }

    @Override
    public ThousandYearStormAbility copy() {
        return new ThousandYearStormAbility(this);
    }
}

class ThousandYearStormEffect extends OneShotEffect {
    private int stormCount;

    public ThousandYearStormEffect() {
        super(Outcome.Benefit);
        this.stormCount = -1;
        this.staticText = "copy it for each other instant and sorcery spell you've cast before it this turn. You may choose new targets for the copies";
    }

    public ThousandYearStormEffect(final ThousandYearStormEffect effect) {
        super(effect);
        this.stormCount = effect.stormCount;
    }

    @Override
    public ThousandYearStormEffect copy() {
        return new ThousandYearStormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpellOrLKIStack(getTargetPointer().getFirst(game, source));
        if (stormCount >= 0 && spell != null) {
            spell.createCopyOnStack(game, source, source.getControllerId(), true, stormCount);
            return true;
        }
        return false;
    }

    public void setStormCount(int stormCount) {
        this.stormCount = stormCount;
    }
}

class ThousandYearStormWatcher extends Watcher {

    private final Map<UUID, List<MageObjectReference>> spellsThisTurn = new HashMap<>();

    public ThousandYearStormWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            MageObject object = game.getObject(event.getTargetId());
            if (object != null && object.isInstantOrSorcery(game)) {
                UUID playerId = event.getPlayerId();
                List<MageObjectReference> spellsCast = spellsThisTurn.getOrDefault(playerId, new ArrayList<MageObjectReference>());
                spellsCast.add(new MageObjectReference(object, game));
                spellsThisTurn.put(playerId, spellsCast);
            }
        }
    }

    @Override
    public void reset() {
        for (List<MageObjectReference> mor : spellsThisTurn.values()) {
            mor.clear();
        }
        spellsThisTurn.clear();
    }

    public List<MageObjectReference> getSpellsThisTurn(UUID playerId) {
        return spellsThisTurn.getOrDefault(playerId, new ArrayList<MageObjectReference>());
    }
}

enum ThousandYearStormSpellsCastThatTurnValue implements DynamicValue {

    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        ThousandYearStormWatcher watcher = game.getState().getWatcher(ThousandYearStormWatcher.class);
        if (watcher == null) {
            return 0;
        }
        return watcher.getSpellsThisTurn(sourceAbility.getControllerId()).size();
    }

    @Override
    public ThousandYearStormSpellsCastThatTurnValue copy() {
        return instance;
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "You cast an instant or sorcery spell in this turn";
    }
}
