package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class MutatesSourceTriggeredAbility extends TriggeredAbilityImpl {

    public MutatesSourceTriggeredAbility(Effect effect) {
        this(effect, false);
    }

    public MutatesSourceTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        setTriggerPhrase("Whenever this creature mutates, ");
    }

    private MutatesSourceTriggeredAbility(final MutatesSourceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MutatesSourceTriggeredAbility copy() {
        return new MutatesSourceTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        // TODO: implement this
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // TODO: implement this
        return false;
    }
}
