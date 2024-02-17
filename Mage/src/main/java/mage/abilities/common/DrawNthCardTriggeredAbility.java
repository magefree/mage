package mage.abilities.common;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.CardsDrawnThisTurnDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.watchers.common.CardsDrawnThisTurnWatcher;

/**
 * @author TheElk801
 */
public class DrawNthCardTriggeredAbility extends TriggeredAbilityImpl {

    private static final Hint hint = new ValueHint(
            "Cards drawn this turn", CardsDrawnThisTurnDynamicValue.instance
    );
    private final TargetController targetController;
    private final int cardNumber;

    public DrawNthCardTriggeredAbility(Effect effect, boolean optional, int cardNumber) {
        this(effect, optional, TargetController.YOU, cardNumber);
    }

    public DrawNthCardTriggeredAbility(Effect effect, boolean optional, TargetController targetController, int cardNumber) {
        this(Zone.BATTLEFIELD, effect, optional, targetController, cardNumber);
    }

    public DrawNthCardTriggeredAbility(Zone zone, Effect effect, boolean optional, TargetController targetController, int cardNumber) {
        super(zone, effect, optional);
        this.targetController = targetController;
        this.cardNumber = cardNumber;
        this.addHint(hint);
        setTriggerPhrase(generateTriggerPhrase());
    }

    private DrawNthCardTriggeredAbility(final DrawNthCardTriggeredAbility ability) {
        super(ability);
        this.targetController = ability.targetController;
        this.cardNumber = ability.cardNumber;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch (targetController) {
            case YOU:
                if (!isControlledBy(event.getPlayerId())) {
                    return false;
                }
                break;
            case ACTIVE:
                if (!game.isActivePlayer(event.getPlayerId())) {
                    return false;
                }
                break;
            case OPPONENT:
                Player controller = game.getPlayer(controllerId);
                if (controller == null || !controller.hasOpponent(event.getPlayerId(), game)) {
                    return false;
                }
                break;
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
        CardsDrawnThisTurnWatcher watcher = game.getState().getWatcher(CardsDrawnThisTurnWatcher.class);
        return watcher != null && watcher.getCardsDrawnThisTurn(event.getPlayerId()) == cardNumber;
    }

    public String generateTriggerPhrase() {
        switch (targetController) {
            case YOU:
                return "Whenever you draw your " + CardUtil.numberToOrdinalText(cardNumber) + " card each turn, ";
            case ACTIVE:
                return "Whenever a player draws their " + CardUtil.numberToOrdinalText(cardNumber) + " card during their turn, ";
            case OPPONENT:
                return "Whenever an opponent draws their " + CardUtil.numberToOrdinalText(cardNumber) + " card each turn, ";
            default:
                throw new IllegalArgumentException("TargetController " + targetController + " not supported");
        }
    }

    @Override
    public DrawNthCardTriggeredAbility copy() {
        return new DrawNthCardTriggeredAbility(this);
    }
}
