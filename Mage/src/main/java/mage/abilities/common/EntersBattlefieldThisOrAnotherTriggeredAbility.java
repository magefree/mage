package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class EntersBattlefieldThisOrAnotherTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    private final boolean onlyControlled;

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(effect, filter, false, false);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, boolean onlyControlled) {
        this(effect, filter, optional, SetTargetPointer.NONE, onlyControlled);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyControlled) {
        this(Zone.BATTLEFIELD, effect, filter, optional, setTargetPointer, onlyControlled);
    }

    public EntersBattlefieldThisOrAnotherTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, boolean onlyControlled) {
        super(zone, effect, filter, optional, setTargetPointer, null, onlyControlled);
        this.onlyControlled = onlyControlled;
    }

    private EntersBattlefieldThisOrAnotherTriggeredAbility(final EntersBattlefieldThisOrAnotherTriggeredAbility ability) {
        super(ability);
        this.onlyControlled = ability.onlyControlled;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // We're calling super.checkTrigger here for the side effects:
        // - Set the target pointer
        // - PermanentEnteringBattlefield
        // - PermanentEnteringControllerId
        super.checkTrigger(event, game);

        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null) {
            return false;
        }

        // If the permanent entering is 'this' permanent, it applies:
        if (permanent.getId().equals(getSourceId())) {
            return true;
        }
        if (onlyControlled && !permanent.isControlledBy(this.getControllerId())) {
            return false;
        }
        return filter.match(permanent, getControllerId(), this, game);
    }

    @Override
    public EntersBattlefieldThisOrAnotherTriggeredAbility copy() {
        return new EntersBattlefieldThisOrAnotherTriggeredAbility(this);
    }
}
