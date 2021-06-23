
package mage.abilities.keyword;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class ExaltedAbility extends TriggeredAbilityImpl {

    public ExaltedAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Duration.EndOfTurn));
    }

    public ExaltedAbility(final ExaltedAbility ability) {
        super(ability);
    }

    @Override
    public ExaltedAbility copy() {
        return new ExaltedAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DECLARED_ATTACKERS;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (game.isActivePlayer(this.controllerId)) {
            if (game.getCombat().attacksAlone()) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(game.getCombat().getAttackers().get(0)));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "exalted <i>(Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)</i>";
    }

}
