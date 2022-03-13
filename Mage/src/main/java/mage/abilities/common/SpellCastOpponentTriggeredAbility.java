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

    public SpellCastOpponentTriggeredAbility(Effect effect, boolean optional) {
        this(effect, StaticFilters.FILTER_SPELL_A, optional);
    }

    public SpellCastOpponentTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(Zone.BATTLEFIELD, effect, filter, optional);
    }

    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE);
    }

    /**
     * @param zone
     * @param effect
     * @param filter
     * @param optional
     * @param setTargetPointer Supported: SPELL, PLAYER
     */
    public SpellCastOpponentTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public SpellCastOpponentTriggeredAbility(final SpellCastOpponentTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
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
    public String getTriggerPhrase() {
        return "Whenever an opponent casts " + filter.getMessage() + ", " ;
    }

    @Override
    public SpellCastOpponentTriggeredAbility copy() {
        return new SpellCastOpponentTriggeredAbility(this);
    }
}
