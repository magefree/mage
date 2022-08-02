package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;

/**
 * @author North
 */
public class SpellCastControllerTriggeredAbility extends TriggeredAbilityImpl {

    protected final FilterSpell filter;
    protected String rule;  // TODO: This sould be final, but is not because of the telescoping contructors

    // The source SPELL that triggered the ability will be set as target to effect
    protected boolean rememberSource;
    // Use it if you want remember CARD instead spell
    protected boolean rememberSourceAsCard;

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
        setTriggerPhrase("Whenever you cast " + filter.getMessage() + ", ");
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
            if (filter.match(spell, getControllerId(), this, game)) {
                this.getEffects().setValue("spellCast", spell);
                if (rememberSource) {
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

    @Override
    public String getRule() {
        return rule != null ? rule : super.getRule();
    }

    @Override
    public SpellCastControllerTriggeredAbility copy() {
        return new SpellCastControllerTriggeredAbility(this);
    }
}
