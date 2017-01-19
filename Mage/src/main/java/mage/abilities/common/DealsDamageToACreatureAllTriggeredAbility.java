/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Ludwig.Hirth
 */
public class DealsDamageToACreatureAllTriggeredAbility extends TriggeredAbilityImpl {

    private final boolean combatDamageOnly;
    private final FilterPermanent filterPermanent;
    private final SetTargetPointer setTargetPointer;
    
    public DealsDamageToACreatureAllTriggeredAbility(Effect effect, boolean optional, FilterPermanent filterPermanent, SetTargetPointer setTargetPointer, boolean combatDamageOnly) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.combatDamageOnly = combatDamageOnly;
        this.setTargetPointer = setTargetPointer;
        this.filterPermanent = filterPermanent;
    }

    public DealsDamageToACreatureAllTriggeredAbility(final DealsDamageToACreatureAllTriggeredAbility ability) {
        super(ability);
        this.combatDamageOnly = ability.combatDamageOnly;
        this.filterPermanent = ability.filterPermanent;
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public DealsDamageToACreatureAllTriggeredAbility copy() {
        return new DealsDamageToACreatureAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!combatDamageOnly || ((DamagedCreatureEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
            if (permanent != null && filterPermanent.match(permanent, getSourceId(), getControllerId(), game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                    effect.setValue("sourceId", event.getSourceId());
                    switch (setTargetPointer) {
                        case PLAYER:
                            effect.setTargetPointer(new FixedTarget(permanent.getControllerId()));
                            break;
                        case PERMANENT:
                            effect.setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                            break;
                    }

                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever " + filterPermanent.getMessage() + " deals " 
                + (combatDamageOnly ? "combat ":"") + "damage to a creature, " + super.getRule();
    }
}