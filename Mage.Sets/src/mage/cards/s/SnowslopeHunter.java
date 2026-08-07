package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author muz
 */
public final class SnowslopeHunter extends CardImpl {

    public SnowslopeHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.RANGER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Sacrifice another creature or artifact: Exile the top card of your library. You may play it until the end of your next turn. Activate only during your turn and only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(
            Zone.BATTLEFIELD,
            new ExileTopXMayPlayUntilEffect(1, Duration.UntilEndOfYourNextTurn)
                .withTextOptions("it", true),
            new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT),
            1,
            MyTurnCondition.instance
        ));
    }

    private SnowslopeHunter(final SnowslopeHunter card) {
        super(card);
    }

    @Override
    public SnowslopeHunter copy() {
        return new SnowslopeHunter(this);
    }
}
