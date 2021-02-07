package mage.abilities.common;

import mage.MageObject;
import mage.abilities.MageSingleton;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.game.command.Plane;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author North
 */
public class SpellCastControllerTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterSpell filter;
    protected String rule;

    // The source SPELL that triggered the ability will be set as target to effect
    protected boolean rememberSource = false;
    // Use it if you want remember CARD instead spell
    protected boolean rememberSourceAsCard = false;

    public SpellCastControllerTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_SPELL_A, optional, false);
    }

    public SpellCastControllerTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(effect, filter, optional, false);
    }

    public SpellCastControllerTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, String rule) {
        this(effect, filter, optional, false);
        this.rule = rule;
    }

    public SpellCastControllerTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, boolean rememberSource) {
        this(Zone.BATTLEFIELD, effect, filter, optional, rememberSource);
    }

    public SpellCastControllerTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, boolean rememberSource) {
        this(zone, effect, filter, optional, rememberSource, false);
    }

    public SpellCastControllerTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, boolean rememberSource, boolean rememberSourceAsCard) {
        super(zone, effect, optional);
        this.filter = filter;
        this.rememberSource = rememberSource;
        this.rememberSourceAsCard = rememberSourceAsCard;
    }

    public SpellCastControllerTriggeredAbility(final SpellCastControllerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.rememberSource = ability.rememberSource;
        this.rememberSourceAsCard = ability.rememberSourceAsCard;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && filter.match(spell, getSourceId(), getControllerId(), game)) {
                if (rememberSource) {
                    this.getEffects().setValue("spellCast", spell);
                    if (rememberSourceAsCard) {
                        this.getEffects().setTargetPointer(new FixedTarget(spell.getCard().getId(), game));
                    } else {
                        this.getEffects().setTargetPointer(new FixedTarget(spell.getId(), game));
                    }

                }
                return true;
            }
        }
        return false;
    }

    // https://github.com/magefree/mage/issues/7095
    // Code copied from AbilityImpl with check for getShortLivingLKI removed
    // Ability must not trigger if source was sacrificed as a cost of casting the spell
    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        if (!this.hasSourceObjectAbility(game, source, event)) {
            return false;
        }
        if (zone == Zone.COMMAND) {
            if (this.getSourceId() == null) { // commander effects
                return true;
            }
            MageObject object = game.getObject(this.getSourceId());
            // emblem/planes are always actual
            if (object instanceof Emblem || object instanceof Plane) {
                return true;
            }
        }

        UUID parameterSourceId;
        // for singleton abilities like Flying we can't rely on abilities' source because it's only once in continuous effects
        // so will use the sourceId of the object itself that came as a parameter if it is not null
        if (this instanceof MageSingleton && source != null) {
            parameterSourceId = source.getId();
        } else {
            parameterSourceId = getSourceId();
        }
        // check against current state
        Zone test = game.getState().getZone(parameterSourceId);
        return test != null && zone.match(test);
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        return "Whenever you cast " + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public SpellCastControllerTriggeredAbility copy() {
        return new SpellCastControllerTriggeredAbility(this);
    }
}
