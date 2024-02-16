
package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.AbilityWord;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;

/**
 * @author North
 */
public class AllyEntersBattlefieldTriggeredAbility extends TriggeredAbilityImpl {

    public AllyEntersBattlefieldTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
        this.setAbilityWord(AbilityWord.RALLY);
        setTriggerPhrase("Whenever {this} or another Ally enters the battlefield under your control, ");
    }

    public AllyEntersBattlefieldTriggeredAbility(AllyEntersBattlefieldTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        EntersTheBattlefieldEvent ebe = (EntersTheBattlefieldEvent) event;
        return ebe.getTarget().isControlledBy(this.controllerId)
                && (event.getTargetId().equals(this.getSourceId())
                || (ebe.getTarget().hasSubtype(SubType.ALLY, game) && !event.getTargetId().equals(this.getSourceId())));
    }

    @Override
    public AllyEntersBattlefieldTriggeredAbility copy() {
        return new AllyEntersBattlefieldTriggeredAbility(this);
    }
}
