package mage.cards.c;

import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CouncilsDeliberation extends CardImpl {

    public CouncilsDeliberation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Whenever you scry, if you control an Island, you may exile Council's Deliberation from your graveyard. If you do, draw a card.
        this.addAbility(new CouncilsDeliberationTriggeredAbility());
    }

    private CouncilsDeliberation(final CouncilsDeliberation card) {
        super(card);
    }

    @Override
    public CouncilsDeliberation copy() {
        return new CouncilsDeliberation(this);
    }
}

class CouncilsDeliberationTriggeredAbility extends TriggeredAbilityImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ISLAND);

    CouncilsDeliberationTriggeredAbility() {
        super(Zone.GRAVEYARD, new DoIfCostPaid(new DrawCardSourceControllerEffect(1), new ExileSourceFromGraveCost()));
    }

    private CouncilsDeliberationTriggeredAbility(final CouncilsDeliberationTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CouncilsDeliberationTriggeredAbility copy() {
        return new CouncilsDeliberationTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SCRIED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean checkInterveningIfClause(Game game) {
        return game.getBattlefield().contains(filter, this, game, 1);
    }

    @Override
    public String getRule() {
        return "Whenever you scry, if you control an Island, you may exile {this} from your graveyard. If you do, draw a card.";
    }
}
