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

public class EntersBattlefieldOrSpellCastControllerTriggeredAbilty extends TriggeredAbilityImpl {

    protected boolean ignoreRulesGeneration; // use it with custom rules (if you don't want ETB auto-generated text)
    protected String etbFlavorWord = null;
    protected FilterSpell filter;
    protected String rule;

    protected boolean rememberSource; // The source SPELL that triggered the ability will be set as target to effect
    protected boolean rememberSourceAsCard; // Use it if you want remember CARD instead spell

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Effect effect) {
        this(effect, StaticFilters.FILTER_SPELL_A);
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Effect effect, FilterSpell filter) {
        this(effect, filter, false);
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Effect effect, FilterSpell filter, boolean optional) {
        this(effect, filter, optional, false);
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Effect effect, FilterSpell filter, boolean optional, boolean rememberSource) {
        this(effect, filter, optional, rememberSource, false);
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Effect effect, FilterSpell filter, boolean optional, boolean rememberSource, boolean rememberSourceAsCard) {
        this(effect, filter, optional, rememberSource, rememberSourceAsCard, false);
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Effect effect, FilterSpell filter, boolean optional, boolean rememberSource, boolean rememberSourceAsCard, boolean ignoreRulesGeneration) {
        this(Zone.ALL, effect, filter, optional, rememberSource, rememberSourceAsCard, ignoreRulesGeneration);
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(Zone zone, Effect effect, FilterSpell filter, boolean optional, boolean rememberSource, boolean rememberSourceAsCard, boolean ignoreRulesGeneration) {
        super(zone, effect, optional);
        this.ignoreRulesGeneration = ignoreRulesGeneration;
        this.filter = filter;
        this.rememberSource = rememberSource;
        this.rememberSourceAsCard = rememberSourceAsCard;
    }

    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty(final EntersBattlefieldOrSpellCastControllerTriggeredAbilty ability) {
        super(ability);
        this.ignoreRulesGeneration = ability.ignoreRulesGeneration;
        this.etbFlavorWord = ability.etbFlavorWord;
        this.filter = ability.filter;
        this.rule = ability.rule;
        this.rememberSource = ability.rememberSource;
        this.rememberSourceAsCard = ability.rememberSourceAsCard;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD || event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId())) {
            this.getEffects().setValue("permanentEnteredBattlefield", game.getPermanent(event.getTargetId()));
            return true;
        }
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (filter.match(spell, getSourceId(), getControllerId(), game)) {
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

    @Override
    public String getRule() {
        if (rule != null && !rule.isEmpty()) {
            return rule;
        }
        return super.getRule();
    }

    @Override
    public String getTriggerPhrase() {
        return "When {this} enters the battlefield, or whenever you cast " + filter.getMessage() + ", ";
    }

    @Override
    public EntersBattlefieldOrSpellCastControllerTriggeredAbilty copy() {
        return new EntersBattlefieldOrSpellCastControllerTriggeredAbilty(this);
    }
}
