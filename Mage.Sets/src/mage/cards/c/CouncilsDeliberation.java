package mage.cards.c;

import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CouncilsDeliberation extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.ISLAND));

    public CouncilsDeliberation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));

        // Whenever you scry, if you control an Island, you may exile Council's Deliberation from your graveyard. If you do, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new ScryTriggeredAbility(
                        Zone.GRAVEYARD,
                        new DoIfCostPaid(
                                new DrawCardSourceControllerEffect(1), new ExileSourceFromGraveCost()
                        ), false
                ), condition, "Whenever you scry, if you control an Island, " +
                "you may exile {this} from your graveyard. If you do, draw a card."
        ));
    }

    private CouncilsDeliberation(final CouncilsDeliberation card) {
        super(card);
    }

    @Override
    public CouncilsDeliberation copy() {
        return new CouncilsDeliberation(this);
    }
}
