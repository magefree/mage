
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class DealsDamageToAPlayerAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;
    private final boolean onlyCombat;
    private final boolean affectsDefendingPlayer;

    public DealsDamageToAPlayerAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat) {
        this(effect, filter, optional, setTargetPointer, onlyCombat, false);
    }

    public DealsDamageToAPlayerAllTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat, boolean affectsDefendingPlayer) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer, onlyCombat, affectsDefendingPlayer);
    }

    public DealsDamageToAPlayerAllTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyCombat, boolean affectsDefendingPlayer) {
        super(zone, effect, optional);
        this.setTargetPointer = setTargetPointer;
        this.filter = filter;
        this.onlyCombat = onlyCombat;
        this.affectsDefendingPlayer = affectsDefendingPlayer;
    }

    public DealsDamageToAPlayerAllTriggeredAbility(final DealsDamageToAPlayerAllTriggeredAbility ability) {
        super(ability);
        this.setTargetPointer = ability.setTargetPointer;
        this.filter = ability.filter;
        this.onlyCombat = ability.onlyCombat;
        this.affectsDefendingPlayer = ability.affectsDefendingPlayer;
    }

    @Override
    public DealsDamageToAPlayerAllTriggeredAbility copy() {
        return new DealsDamageToAPlayerAllTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!onlyCombat || ((DamagedPlayerEvent) event).isCombatDamage()) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
                for (Effect effect : this.getEffects()) {
                    effect.setValue("damage", event.getAmount());
                    effect.setValue("sourceId", event.getSourceId());
                    if (affectsDefendingPlayer) {
                        effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                        continue;
                    }
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
        return "Whenever " + filter.getMessage() + " deals " + (onlyCombat ? "combat " : "") + "damage to a player, " + super.getRule();
    }

}
