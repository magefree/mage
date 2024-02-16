package mage.cards.r;

import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevelInRiches extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("Treasures");

    static {
        filter.add(SubType.TREASURE.getPredicate());
    }

    public RevelInRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // Whenever a creature an opponent controls dies, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DiesCreatureTriggeredAbility(new CreateTokenEffect(new TreasureToken()), false, StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE));
        // At the beginning of your upkeep, if you control ten or more Treasures, you win the game.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect(), TargetController.YOU, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                ability,
                new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 9),
                "At the beginning of your upkeep, if you control ten or more Treasures, you win the game.")
                .addHint(new ValueHint("Treasures you control", new PermanentsOnBattlefieldCount(filter))));
    }

    private RevelInRiches(final RevelInRiches card) {
        super(card);
    }

    @Override
    public RevelInRiches copy() {
        return new RevelInRiches(this);
    }
}
