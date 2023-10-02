package mage.abilities.effects.common.ruleModifying;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 * @author xenohedron
 */
public class DontCauseTriggerEffect extends ContinuousRuleModifyingEffectImpl {

    private final FilterPermanent filterEntering; // (or dying, if applicable)
    private final boolean orDying;
    private final FilterPermanent filterTriggering; // can be null if any abilities

    /**
     * "Creatures entering the battlefield don't cause abilities to trigger."
     */
    public DontCauseTriggerEffect() {
        this(StaticFilters.FILTER_PERMANENT_CREATURES, false, null);
    }

    /**
     *
     * @param filterEntering which permanents don't cause triggers on entering (or dying if applicable)
     * @param orDying false = only ETB, true = also applies to dying
     * @param filterTriggering if not all abilities e.g. "of permanents your opponents control", or null for all
     */
    public DontCauseTriggerEffect(FilterPermanent filterEntering, boolean orDying, FilterPermanent filterTriggering) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.filterEntering = filterEntering;
        this.orDying = orDying;
        this.filterTriggering = filterTriggering;
        staticText = filterEntering.getMessage() + " entering the battlefield"
                + (orDying ? " or dying" : "") + " don't cause abilities"
                + (filterTriggering == null ? "" : " of " + filterTriggering.getMessage())
                + " to trigger";
    }

    protected DontCauseTriggerEffect(final DontCauseTriggerEffect effect) {
        super(effect);
        this.filterEntering = effect.filterEntering;
        this.orDying = effect.orDying;
        this.filterTriggering = effect.filterTriggering;
    }

    @Override
    public DontCauseTriggerEffect copy() {
        return new DontCauseTriggerEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (filterTriggering != null) {
            Ability ability = (Ability) getValue("targetAbility");
            if (ability == null) {
                return false;
            }
            Permanent triggeringPermanent = ability.getSourcePermanentOrLKI(game);
            if (triggeringPermanent == null || !filterTriggering.match(triggeringPermanent, source.getControllerId(), source, game)) {
                return false;
            }
        }
        GameEvent sourceEvent = ((NumberOfTriggersEvent) event).getSourceEvent();
        if (sourceEvent == null) {
            return false;
        }
        switch (sourceEvent.getType()) {
            case ENTERS_THE_BATTLEFIELD:
                Permanent enteringPermanent = ((EntersTheBattlefieldEvent) sourceEvent).getTarget();
                if (enteringPermanent != null && filterEntering.match(enteringPermanent, source.getControllerId(), source, game)) {
                    break; // Effect applies as permanent matches filter
                }
                return false;
            case ZONE_CHANGE:
                if (orDying && ((ZoneChangeEvent) sourceEvent).isDiesEvent()) {
                    Permanent dyingPermanent = ((ZoneChangeEvent) sourceEvent).getTarget();
                    if (dyingPermanent != null && filterEntering.match(dyingPermanent, source.getControllerId(), source, game)) {
                        break; // Effect applies as permanent matches filter
                    }
                }
                return false;
            default:
                return false;
        }
        return true; // NumberOfTriggers event fully replaced, no further replacement effects, no triggers
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        Ability ability = (Ability) getValue("targetAbility");
        if (ability == null) {
            return null;
        }
        MageObject abilityObject = game.getObject(ability.getSourceId());
        if (abilityObject == null) {
            return null;
        }
        return "Prevented ability of " + abilityObject.getLogName() + " from triggering.";
    }
}
