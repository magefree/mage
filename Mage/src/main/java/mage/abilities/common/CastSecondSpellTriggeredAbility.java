package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author TheElk801
 */
public class CastSecondSpellTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint("Spells you cast this turn", SpellCastValue.instance);
    private final TargetController targetController;

    public CastSecondSpellTriggeredAbility(Effect effect) {
        this(effect, TargetController.YOU);
    }

    public CastSecondSpellTriggeredAbility(Effect effect, TargetController targetController) {
        this(Zone.BATTLEFIELD, effect, targetController, false);
    }

    public CastSecondSpellTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean optional) {
        super(zone, effect, optional);
        this.addWatcher(new CastSpellLastTurnWatcher());
        if (targetController == TargetController.YOU) {
            this.addHint(hint);
        }
        this.targetController = targetController;
    }

    private CastSecondSpellTriggeredAbility(final CastSecondSpellTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                if (!game.getOpponents(getControllerId()).contains(event.getPlayerId())) {
                    return false;
                }
                break;
            case ACTIVE:
                if (!game.isActivePlayer(event.getPlayerId())) {
                    return false;
                }
            case ANY:
                break;
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
        CastSpellLastTurnWatcher watcher = game.getState().getWatcher(CastSpellLastTurnWatcher.class);
        return watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2;
    }

    @Override
    public String getTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you cast your second spell each turn, ";
            case OPPONENT:
                return "Whenever an opponent casts their second spell each turn, ";
            case ACTIVE:
                return "Whenever a player casts their second spell during their turn, ";
            case ANY:
                return "Whenever a player casts their second spell each turn, ";
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
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