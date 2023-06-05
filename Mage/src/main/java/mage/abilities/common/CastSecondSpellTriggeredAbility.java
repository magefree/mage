package mage.abilities.common;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.SetTargetPointer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastSpellLastTurnWatcher;

/**
 * @author TheElk801
 */
public class CastSecondSpellTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint("Spells you cast this turn", SpellCastValue.instance);
    private final TargetController targetController;
    private final SetTargetPointer setTargetPointer;

    public CastSecondSpellTriggeredAbility(Effect effect) {
        this(effect, TargetController.YOU);
    }

    public CastSecondSpellTriggeredAbility(Effect effect, TargetController targetController) {
        this(Zone.BATTLEFIELD, effect, targetController, false);
    }

    public CastSecondSpellTriggeredAbility(Zone zone, Effect effect, TargetController targetController, boolean optional) {
        this(zone, effect, targetController, optional, SetTargetPointer.NONE);
    }

    /**
     * 
     * @param zone             What zone the ability can trigger from (see {@link mage.abilities.Ability#getZone})
     * @param effect           What effect will happen when this ability triggers (see {@link mage.abilities.Ability#getEffects})
     * @param targetController Which player(s) to pay attention to
     * @param optional         Whether the effect is optional (see {@link mage.abilities.TriggeredAbility#isOptional})
     * @param setTargetPointer Who to set the target pointer of the effects to. Only accepts NONE, PLAYER (the player who cast the spell), and SPELL (the spell which was cast)
     */
    public CastSecondSpellTriggeredAbility(Zone zone, Effect effect, TargetController targetController,
            boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.addWatcher(new CastSpellLastTurnWatcher());
        if (targetController == TargetController.YOU) {
            this.addHint(hint);
        }
        this.targetController = targetController;
        this.setTargetPointer = setTargetPointer;
        setTriggerPhrase(generateTriggerPhrase());
    }

    private CastSecondSpellTriggeredAbility(final CastSecondSpellTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.setTargetPointer = ability.setTargetPointer;
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
        if (watcher != null && watcher.getAmountOfSpellsPlayerCastOnCurrentTurn(event.getPlayerId()) == 2) {
            this.getEffects().setValue("spellCast", game.getSpell(event.getTargetId()));
            switch (this.setTargetPointer) {
                case PLAYER:
                    this.getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                    break;
                case SPELL:
                    this.getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                    break;
                case NONE:
                    break;
                default:
                    throw new IllegalArgumentException("SetTargetPointer " + this.setTargetPointer + " not supported");
            }
            return true;
        }
        return false;
    }

    private String generateTriggerPhrase() {
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