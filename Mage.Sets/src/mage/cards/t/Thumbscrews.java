
package mage.cards.t;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.target.common.TargetOpponentOrPlaneswalker;

/**
 *
 * @author fireshoes
 */
public final class Thumbscrews extends CardImpl {

    public Thumbscrews(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of your upkeep, if you have five or more cards in hand, Thumbscrews deals 1 damage to target opponent.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                new DamageTargetEffect(1), TargetController.YOU, false);
        ability.addTarget(new TargetOpponentOrPlaneswalker());
        CardsInHandCondition condition = new CardsInHandCondition(ComparisonType.MORE_THAN, 4);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability, condition,
                "At the beginning of your upkeep, if you have five or more cards in hand, "
                + "{this} deals 1 damage to target opponent or planeswalker."
        ));
    }

    private Thumbscrews(final Thumbscrews card) {
        super(card);
    }

    @Override
    public Thumbscrews copy() {
        return new Thumbscrews(this);
    }
}
