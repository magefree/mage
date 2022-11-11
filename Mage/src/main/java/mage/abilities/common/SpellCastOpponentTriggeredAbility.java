package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class SpellCastOpponentTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterSpell filter;
    protected SetTargetPointer setTargetPointer;
    private final boolean onlyFromNonHand;

    public SpellCastOpponentTriggeredAbility(Effect effect, boolean optional) {
        this(effect, optional, false);
    }

    public SpellCastOpponentTriggeredAbility(Effect effect, boolean optional, boolean onlyFromNonHand) {
        this(effect, StaticFilters.FILTER_SPELL_A, optional, onlyFromNonHand);
    }

    public SpellCastOpponentTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(effect, filter, optional, false);
    }

    public SpellCastOpponentTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, boolean onlyFromNonHand) {
        this(Zone.BATTLEFIELD, effect, filter, optional, onlyFromNonHand);
    }

    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional) {
        this(zone, effect, filter, optional, false);
    }

    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, boolean onlyFromNonHand) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, onlyFromNonHand);
    }

    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        this(zone, effect, filter, optional, setTargetPointer, false);
    }

    /**
     * @param zone              The zone in which the source permanent has to be in for the ability to trigger
     * @param effect            The effect to apply if condition is met
     * @param filter            Filter for matching the spell cast
     * @param optional          Whether the player can choose to apply the effect
     * @param onlyFromNonHand   Whether to trigger only when spells are cast from not the hand
     * @param setTargetPointer  Supported: SPELL, PLAYER
     */
    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyFromNonHand) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
        this.onlyFromNonHand = onlyFromNonHand;
        setTriggerPhrase("Whenever an opponent casts "
                + filter.getMessage()
                + (onlyFromNonHand ? " from anywhere other than their hand" : "")
                + ", ");
    }

    public SpellCastOpponentTriggeredAbility(final SpellCastOpponentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.onlyFromNonHand = ability.onlyFromNonHand;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getPlayer(this.getControllerId()).hasOpponent(event.getPlayerId(), game)) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (!filter.match(spell, getControllerId(), this, game)) {
            return false;
        }

        if (onlyFromNonHand && spell.getFromZone() == Zone.HAND) {
            return false;
        }

        getEffects().setValue("spellCast", spell);
        switch (setTargetPointer) {
            case NONE:
                break;
            case SPELL:
                getEffects().setTargetPointer(new FixedTarget(event.getTargetId()));
                break;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(event.getPlayerId()));
                break;
            default:
                throw new UnsupportedOperationException("Value of SetTargetPointer not supported!");
        }
        return true;
    }

    @Override
    public SpellCastOpponentTriggeredAbility copy() {
        return new SpellCastOpponentTriggeredAbility(this);
    }
}
