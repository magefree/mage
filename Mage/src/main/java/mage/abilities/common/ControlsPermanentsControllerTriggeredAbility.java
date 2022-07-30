
package mage.abilities.common;

import mage.constants.ComparisonType;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * State triggered ability that triggers if the ability controller controlls the
 * defined number of permanents.
 *
 * @author LevelX2
 */
public class ControlsPermanentsControllerTriggeredAbility extends StateTriggeredAbility {

    protected final FilterPermanent filter;
    protected final ComparisonType type;
    protected final int value;

    public ControlsPermanentsControllerTriggeredAbility(FilterPermanent filter, Effect effect){
        this(filter, ComparisonType.MORE_THAN, 0, effect);
    }

    public ControlsPermanentsControllerTriggeredAbility(FilterPermanent filter, ComparisonType type, int value, Effect effect) {
        super(Zone.BATTLEFIELD, effect);
        this.filter = filter;
        this.value = value;
        this.type = type;
        setTriggerPhrase("When you control " + filter.getMessage() + ", " );
    }

    public ControlsPermanentsControllerTriggeredAbility(final ControlsPermanentsControllerTriggeredAbility ability) {
        super(ability);
        this.filter = ability.filter;
        this.type = ability.type;
        this.value = ability.value;
    }

    @Override
    public ControlsPermanentsControllerTriggeredAbility copy() {
        return new ControlsPermanentsControllerTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        int inputValue = game.getBattlefield().countAll(filter, getControllerId(), game);
        return ComparisonType.compare(inputValue, type, value);
    }
}
