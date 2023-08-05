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
    protected final String rule;

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
        this(zone, effect, filter, optional, setTargetPointer, null, null);
    }


    public SpellCastControllerTriggeredAbility(Zone zone, Effect effect, FilterSpell filter,
                                               boolean optional, SetTargetPointer setTargetPointer,
                                               String rule, Zone fromZone) {
        super(zone, effect, optional);
        this.filter = filter == null ? StaticFilters.FILTER_SPELL_A : filter;
        this.setTargetPointer = setTargetPointer;
        this.rule = rule;
        this.fromZone = fromZone == null ? Zone.ALL : fromZone;
        makeTriggerPhrase();
    }

    public static SpellCastControllerTriggeredAbility createWithFromZone(Effect effect, FilterSpell filter, boolean optional, Zone fromZone) {
        return new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, effect, filter, optional, null, null, fromZone);
    }

    public static SpellCastControllerTriggeredAbility createWithRule(Effect effect, FilterSpell filter, boolean optional, String rule) {
        return new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, effect, filter, optional, null, rule, null);
    }

    protected SpellCastControllerTriggeredAbility(final SpellCastControllerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
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
                break;
            case SPELL:
                getEffects().setTargetPointer(new FixedTarget(spell.getId(), game));
                break;
            case CARD:
                getEffects().setTargetPointer(new FixedTarget(spell.getCard().getId()));
                break;
            default:
                throw new UnsupportedOperationException("Unexpected setTargetPointer " + setTargetPointer);
        }
        return true;
    }

    @Override
    public String getRule() {
        return rule != null ? rule : super.getRule();
    }

    @Override
    public SpellCastControllerTriggeredAbility copy() {
        return new SpellCastControllerTriggeredAbility(this);
    }

    private void makeTriggerPhrase() {
        String text = "Whenever you cast " + filter.getMessage();

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
