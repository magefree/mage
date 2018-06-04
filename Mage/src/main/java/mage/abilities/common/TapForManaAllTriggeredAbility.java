
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class TapForManaAllTriggeredAbility extends TriggeredAbilityImpl {

    private final FilterPermanent filter;
    private final SetTargetPointer setTargetPointer;

    public TapForManaAllTriggeredAbility(Effect effect, FilterPermanent filter, SetTargetPointer setTargetPointer) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        this.setTargetPointer = setTargetPointer;
    }

    public TapForManaAllTriggeredAbility(TapForManaAllTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter.copy();
        this.setTargetPointer = ability.setTargetPointer;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent != null && filter.match(permanent, getSourceId(), getControllerId(), game)) {
            ManaEvent mEvent = (ManaEvent) event;
            for(Effect effect:getEffects()) {
                effect.setValue("mana", mEvent.getMana());
            }
            switch(setTargetPointer) {
                case PERMANENT:
                    getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getId()));
                    break;
                case PLAYER:
                    getEffects().get(0).setTargetPointer(new FixedTarget(permanent.getControllerId()));
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public TapForManaAllTriggeredAbility copy() {
        return new TapForManaAllTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever " + filter.getMessage() + " for mana, " + super.getRule();
    }
}