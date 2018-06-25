
package mage.cards.r;

import java.util.UUID;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.TreasureToken;

/**
 *
 * @author TheElk801
 */
public final class RevelInRiches extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature an opponent controls");
    private static final FilterPermanent filter2 = new FilterPermanent("Treasures");

    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
        filter2.add(new SubtypePredicate(SubType.TREASURE));
    }

    public RevelInRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // Whenever a creature an opponent controls dies, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new TreasureToken("XLN")), false, filter));
        // At the beginning of your upkeep, if you control ten or more Treasures, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                new PermanentsOnTheBattlefieldCondition(filter2, ComparisonType.MORE_THAN, 9),
                "At the beginning of your upkeep, if you control ten or more Treasures, you win the game."));
    }

    public RevelInRiches(final RevelInRiches card) {
        super(card);
    }

    @Override
    public RevelInRiches copy() {
        return new RevelInRiches(this);
    }
}
