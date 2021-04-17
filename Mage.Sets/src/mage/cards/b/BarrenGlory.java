
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 * @author fireshoes
 */
public final class BarrenGlory extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BarrenGlory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}{W}");

        // At the beginning of your upkeep, if you control no permanents other than Barren Glory and have no cards in hand, you win the game.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false),
                new CompoundCondition(
                        new CardsInHandCondition(ComparisonType.EQUAL_TO, 0),
                        new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0)
                ),
                "At the beginning of your upkeep, if you control no permanents other than {this} and have no cards in hand, you win the game"));
    }

    private BarrenGlory(final BarrenGlory card) {
        super(card);
    }

    @Override
    public BarrenGlory copy() {
        return new BarrenGlory(this);
    }
}
