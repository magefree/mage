package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DiscardCardControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreenGoblinNemesis extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.GOBLIN);

    public GreenGoblinNemesis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you discard a nonland card, put a +1/+1 counter on target Goblin you control.
        Ability ability = new DiscardCardControllerTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
                false, StaticFilters.FILTER_CARD_A_NON_LAND
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Whenever you discard a land card, create a tapped Treasure token.
        this.addAbility(new DiscardCardControllerTriggeredAbility(
                new CreateTokenEffect(new TreasureToken(), 1, true),
                false, StaticFilters.FILTER_CARD_LAND_A
        ));
    }

    private GreenGoblinNemesis(final GreenGoblinNemesis card) {
        super(card);
    }

    @Override
    public GreenGoblinNemesis copy() {
        return new GreenGoblinNemesis(this);
    }
}
