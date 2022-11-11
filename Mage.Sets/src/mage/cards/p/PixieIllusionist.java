package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.continuous.BecomesBasicLandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PixieIllusionist extends CardImpl {

    public PixieIllusionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Kicker {3}{G}
        this.addAbility(new KickerAbility("{3}{G}"));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If Pixie Illusionist was kicked, it enters the battlefield with two +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)),
                KickedCondition.ONCE, "If {this} was kicked, " +
                "it enters the battlefield with two +1/+1 counters on it.", ""
        ));

        // {T}: Target land you control becomes the basic land type of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new BecomesBasicLandTargetEffect(Duration.EndOfTurn), new TapSourceCost()
        );
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private PixieIllusionist(final PixieIllusionist card) {
        super(card);
    }

    @Override
    public PixieIllusionist copy() {
        return new PixieIllusionist(this);
    }
}
