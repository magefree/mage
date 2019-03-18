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
 *
 * @author LevelX2
 */
public class SpellCastAllTriggeredAbility extends TriggeredAbilityImpl {

    protected FilterSpell filter;
    protected String rule;
    protected SetTargetPointer setTargetPointer;

    public SpellCastAllTriggeredAbility(Effect effect, boolean optional) {
        this(Zone.BATTLEFIELD, effect, StaticFilters.FILTER_SPELL_A, optional, SetTargetPointer.NONE);
    }

    public SpellCastAllTriggeredAbility(Effect effect, FilterSpell filter, boolean optional) {
        this(effect, filter, optional, SetTargetPointer.NONE);
    }

    public SpellCastAllTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, String rule) {
        this(effect, filter, optional, SetTargetPointer.NONE);
        this.rule = rule;
    }

    public SpellCastAllTriggeredAbility(Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer);
    }

    public SpellCastAllTriggeredAbility(Zone zone, Effect effect, FilterSpell filter, boolean optional, SetTargetPointer setTargetPointer) {
        super(zone, effect, optional);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public SpellCastAllTriggeredAbility(final SpellCastAllTriggeredAbility ability) {
        super(ability);
        filter = ability.filter;
        this.setTargetPointer = ability.setTargetPointer;
        this.rule = ability.rule;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell != null && filter.match(spell, getSourceId(), getControllerId(), game)) {
            if (setTargetPointer != SetTargetPointer.NONE) {
                for (Effect effect : this.getEffects()) {
                    switch (setTargetPointer) {
                        case SPELL:
                            effect.setTargetPointer(new FixedTarget(spell.getId()));
                            break;
                        case PLAYER:
                            effect.setTargetPointer(new FixedTarget(spell.getControllerId()));
                            break;
                    }

                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        return "Whenever a player casts " + filter.getMessage() + ", " + super.getRule();
    }

    @Override
    public SpellCastAllTriggeredAbility copy() {
        return new SpellCastAllTriggeredAbility(this);
    }
}
