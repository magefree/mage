

package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class EntersBattlefieldControlledTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    /**
     * zone     = BATTLEFIELD
     * optional = false
     * rule     = null
     *
     * @param effect
     * @param filter
     */
    public EntersBattlefieldControlledTriggeredAbility(Effect effect, FilterPermanent filter) {
        this(Zone.BATTLEFIELD, effect, filter, false);
    }

    public EntersBattlefieldControlledTriggeredAbility(Effect effect, FilterPermanent filter, String rule) {
        this(Zone.BATTLEFIELD, effect, filter, false, rule);
    }

    public EntersBattlefieldControlledTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional) {
        this(zone, effect, filter, optional, null);
        this.filter = filter;
    }

    public EntersBattlefieldControlledTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, String rule) {
        this(zone, effect, filter, optional, SetTargetPointer.NONE, rule);
    }

    public EntersBattlefieldControlledTriggeredAbility(Zone zone, Effect effect, FilterPermanent filter, boolean optional, SetTargetPointer setTargetPointer, String rule) {
        super(zone, effect, filter, optional, setTargetPointer, rule, true);
    }

    public EntersBattlefieldControlledTriggeredAbility(final EntersBattlefieldControlledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }
        return super.checkTrigger(event, game);
    }

    @Override
    public EntersBattlefieldControlledTriggeredAbility copy() {
        return new EntersBattlefieldControlledTriggeredAbility(this);
    }
}
