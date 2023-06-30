package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;

/**
 * @author TheElk801
 */
public class EntersBattlefieldFromGraveyardTriggeredAbility extends TriggeredAbilityImpl {

    private final TargetController targetController;

    public EntersBattlefieldFromGraveyardTriggeredAbility(Effect effect) {
        this(effect, TargetController.ANY);
    }

    public EntersBattlefieldFromGraveyardTriggeredAbility(Effect effect, TargetController targetController) {
        super(Zone.BATTLEFIELD, effect, false);
        this.targetController = targetController;
        setTriggerPhrase(generateTriggerPhrase());
    }

    public EntersBattlefieldFromGraveyardTriggeredAbility(final EntersBattlefieldFromGraveyardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId())
                && ((EntersTheBattlefieldEvent) event).getFromZone() == Zone.GRAVEYARD
                && (targetController == TargetController.ANY
                || (targetController == TargetController.YOU
                && isControlledBy(game.getOwnerId(event.getTargetId()))));
    }

    @Override
    public EntersBattlefieldFromGraveyardTriggeredAbility copy() {
        return new EntersBattlefieldFromGraveyardTriggeredAbility(this);
    }

    private String generateTriggerPhrase() {
        StringBuilder sb = new StringBuilder("When {this} enters the battlefield from ");
        switch (targetController) {
            case YOU:
                sb.append("your");
                break;
            case ANY:
                sb.append("a");
                break;
            default:
                throw new UnsupportedOperationException("TargetController " + targetController + "not supported");
        }
        sb.append(" graveyard, ");
        return sb.toString();
    }
}
