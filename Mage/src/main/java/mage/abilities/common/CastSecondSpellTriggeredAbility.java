package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author TheElk801
 */
public class CastSecondSpellTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint("Spells you cast this turn", SpellCastValue.instance);

    public CastSecondSpellTriggeredAbility(Effect effect) {
        this(Zone.BATTLEFIELD, effect, false);
    }

    public CastSecondSpellTriggeredAbility(Zone zone, Effect effect, boolean optional) {
        super(zone, effect, optional);
        this.addWatcher(new CastSpellLastTurnWatcher());
        this.addHint(hint);
    }

    private CastSecondSpellTriggeredAbility(final CastSecondSpellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!isControlledBy(event.getPlayerId())) {
            return false;
        }
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever you cast your second spell each turn, " ;
    }

    @Override
    public CastSecondSpellTriggeredAbility copy() {
        return new CastSecondSpellTriggeredAbility(this);
    }
}

enum SpellCastValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null ? watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(sourceAbility.getControllerId()) : 0;
    }

    @Override
    public SpellCastValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}