package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.HeartwoodToken;
import mage.target.common.TargetControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TenuredTethermage extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped artifacts you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public TenuredTethermage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, you may sacrifice a land. If you do, create two tapped Heartwood tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
            new DoIfCostPaid(
                new CreateTokenEffect(new HeartwoodToken(), 2, true),
                new SacrificeTargetCost(StaticFilters.FILTER_LAND)
            )
        ));

        // Tap two untapped artifacts you control: Put two +1/+1 counters on this creature.
        this.addAbility(new SimpleActivatedAbility(
            new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
            new TapTargetCost(new TargetControlledPermanent(2, filter))
        ));
    }

    private TenuredTethermage(final TenuredTethermage card) {
        super(card);
    }

    @Override
    public TenuredTethermage copy() {
        return new TenuredTethermage(this);
    }
}
