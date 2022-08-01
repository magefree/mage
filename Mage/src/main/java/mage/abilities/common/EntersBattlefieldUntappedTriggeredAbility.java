package mage.abilities.common;

import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class EntersBattlefieldUntappedTriggeredAbility extends EntersBattlefieldTriggeredAbility {

    public EntersBattlefieldUntappedTriggeredAbility(Effect effect, boolean optional) {
        super(effect, optional);
        this.ignoreRulesGeneration = true;
        setTriggerPhrase("When {this} enters the battlefield untapped, ");
    }

    private EntersBattlefieldUntappedTriggeredAbility(final EntersBattlefieldUntappedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldUntappedTriggeredAbility copy() {
        return new EntersBattlefieldUntappedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent != null && !permanent.isTapped();
    }
}