package mage.cards.w;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Wiretapping extends CardImpl {

    public Wiretapping(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{U}");

        // Hideaway 5
        this.addAbility(new HideawayAbility(5));

        // Whenever you draw your first card during each of your draw steps, draw a card. Then if you have nine or more cards in hand, you may play the exiled card without paying its mana cost.
        this.addAbility(new WiretappingTriggeredAbility());
    }

    private Wiretapping(final Wiretapping card) {
        super(card);
    }

    @Override
    public Wiretapping copy() {
        return new Wiretapping(this);
    }
}

class WiretappingTriggeredAbility extends TriggeredAbilityImpl {

    private static final Condition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 8);

    WiretappingTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1));
        this.addEffect(new ConditionalOneShotEffect(new HideawayPlayEffect(), condition));
        this.addWatcher(new CardsDrawnDuringDrawStepWatcher());
    }

    private WiretappingTriggeredAbility(final WiretappingTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WiretappingTriggeredAbility copy() {
        return new WiretappingTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DREW_CARD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.isActivePlayer(event.getPlayerId())
                && game.getStep().getType() == PhaseStep.DRAW
                && isControlledBy(event.getPlayerId())
                && game
                .getState()
                .getWatcher(CardsDrawnDuringDrawStepWatcher.class)
                .getAmountCardsDrawn(event.getPlayerId()) == 1;
    }

    @Override
    public String getRule() {
        return "Whenever you draw your first card during each of your draw steps, draw a card. " +
                "Then if you have nine or more cards in hand, you may play the exiled card without paying its mana cost.";
    }
}
