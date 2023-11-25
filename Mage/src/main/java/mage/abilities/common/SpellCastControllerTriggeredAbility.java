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
 * @author North, Susucr
 */
public class SpellCastControllerTriggeredAbility extends TriggeredAbilityImpl {

    protected final FilterSpell filter;

    // If either the cast spell or the card must be set as TargetPointer of effects.
    protected final SetTargetPointer setTargetPointer;

    // Trigger only for spells cast from this zone. Default is from any zone.
    private final Zone fromZone;

    public SpellCastControllerTriggeredAbility(Effect effect, boolean optional) {
        this(effect, null, optional);
    }

    public SpellCastControllerTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(effect, filter, optional, SetTargetPointer.NONE);
    }

    public SpellCastControllerTriggeredAbility(Effect effect, FilterSpell filter,
                                               boolean optional, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer);
    }

    public SpellCastControllerTriggeredAbility(Zone zone, Effect effect, FilterSpell filter,
                                               boolean optional, SetTargetPointer setTargetPointer) {
        this(zone, effect, filter, optional, setTargetPointer, null);
    }


    public SpellCastControllerTriggeredAbility(Zone zone, Effect effect, FilterSpell filter,
                                               boolean optional, SetTargetPointer setTargetPointer,
                                               Zone fromZone) {
        super(zone, effect, optional);
        this.filter = filter == null ? StaticFilters.FILTER_SPELL_A : filter;
        this.setTargetPointer = setTargetPointer;
        this.fromZone = fromZone == null ? Zone.ALL : fromZone;
        makeTriggerPhrase();
    }

    public static SpellCastControllerTriggeredAbility createWithFromZone(Effect effect, FilterSpell filter, boolean optional, Zone fromZone) {
        return new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, effect, filter, optional, SetTargetPointer.NONE, fromZone);
    }

    protected SpellCastControllerTriggeredAbility(final SpellCastControllerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.fromZone = ability.fromZone;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(this.getControllerId())) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null
                || !filter.match(spell, getControllerId(), this, game)
                || !(fromZone == Zone.ALL || fromZone == spell.getFromZone())) {
            return false;
        }
        this.getEffects().setValue("spellCast", spell);
        switch (setTargetPointer) {
            case NONE:
            case PLAYER: // for subclasses only, needs to be handled there
                break;
            case SPELL:
                getAllEffects().setTargetPointer(new FixedTarget(spell.getId(), game));
                break;
            case CARD:
                getAllEffects().setTargetPointer(new FixedTarget(spell.getCard().getId()));
                break;
            default:
                throw new UnsupportedOperationException("Unexpected setTargetPointer in SpellCastControllerTriggeredAbility: " + setTargetPointer);
        }
        return true;
    }

    @Override
    public SpellCastControllerTriggeredAbility copy() {
        return new SpellCastControllerTriggeredAbility(this);
    }

    private void makeTriggerPhrase() {
        String text = getWhen() + "you cast " + filter.getMessage();

        switch (fromZone) {
            case ALL:
                break;
            case EXILED:
                text += " from exile";
                break;
            default:
                text += " from your " + fromZone.toString().toLowerCase();
                break;
        }
        setTriggerPhrase(text + ", ");
    }
}
