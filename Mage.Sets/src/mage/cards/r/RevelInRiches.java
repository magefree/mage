package mage.cards.r;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.WinGameSourceControllerEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RevelInRiches extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.TREASURE, "you control ten or more Treasures");
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 9);
    private static final Hint hint = new ValueHint("Treasures you control", new PermanentsOnBattlefieldCount(filter));

    public RevelInRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        // Whenever a creature an opponent controls dies, create a colorless Treasure artifact token with "{T}, Sacrifice this artifact: Add one mana of any color."
        this.addAbility(new DiesCreatureTriggeredAbility(
                new CreateTokenEffect(new TreasureToken()), false,
                StaticFilters.FILTER_OPPONENTS_PERMANENT_A_CREATURE
        ));

        // At the beginning of your upkeep, if you control ten or more Treasures, you win the game.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new WinGameSourceControllerEffect())
                .withInterveningIf(condition).addHint(hint));
    }

    private RevelInRiches(final RevelInRiches card) {
        super(card);
    }

    @Override
    public RevelInRiches copy() {
        return new RevelInRiches(this);
    }
}
