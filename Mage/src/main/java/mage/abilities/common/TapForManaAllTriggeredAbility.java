
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.TappedForManaEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 * Non mana triggered ability (use case: you must apply non mana effects on mana taps like gain life)
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
        // it's non mana triggered ability, so ignore it on checking, see TAPPED_FOR_MANA
        if (game.inCheckPlayableState()) {
            return false;
        }
        TappedForManaEvent manaEvent = ((TappedForManaEvent) event);
        Permanent permanent = manaEvent.getPermanent();
        if (permanent == null || !filter.match(permanent, getControllerId(), this, game)) {
            return false;
        }
        getEffects().setValue("mana", manaEvent.getMana());
        getEffects().setValue("tappedPermanent", permanent);
        switch (setTargetPointer) {
            case PERMANENT:
                getEffects().setTargetPointer(new FixedTarget(permanent.getId(), permanent.getZoneChangeCounter(game)));
                break;
            case PLAYER:
                getEffects().setTargetPointer(new FixedTarget(permanent.getControllerId()));
                break;
        }
        return true;
    }

    @Override
    public TapForManaAllTriggeredAbility copy() {
        return new TapForManaAllTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever " + filter.getMessage() + " for mana, " ;
    }
}