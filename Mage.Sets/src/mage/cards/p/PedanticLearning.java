package mage.cards.p;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;

/**
 *
 * @author LevelX2 & L_J
 */
public final class PedanticLearning extends CardImpl {

    public PedanticLearning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");

        // Whenever a land card is put into your graveyard from your library, you may pay {1}. If you do, draw a card.
        this.addAbility(new PedanticLearningTriggeredAbility());
    }

    private PedanticLearning(final PedanticLearning card) {
        super(card);
    }

    @Override
    public PedanticLearning copy() {
        return new PedanticLearning(this);
    }
}

class PedanticLearningTriggeredAbility extends TriggeredAbilityImpl {

    public PedanticLearningTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}")), false);
    }

    public PedanticLearningTriggeredAbility(final PedanticLearningTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent != null && zEvent.getFromZone() == Zone.LIBRARY && zEvent.getToZone() == Zone.GRAVEYARD) {
            Card card = game.getCard(event.getTargetId());
            if (card != null) {
                UUID cardOwnerId = card.getOwnerId();
                if (cardOwnerId != null
                        && card.isOwnedBy(getControllerId())
                        && card.isLand(game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public PedanticLearningTriggeredAbility copy() {
        return new PedanticLearningTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever a land card is put into your graveyard from your library, you may pay {1}. If you do, draw a card.";
    }
}
