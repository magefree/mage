package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BaylenTheHaymaker extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("untapped tokens you control");

    static {
        filter.add(TappedPredicate.UNTAPPED);
        filter.add(TokenPredicate.TRUE);
    }

    public BaylenTheHaymaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Tap two untapped tokens you control: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility(new TapTargetCost(new TargetControlledPermanent(2, filter))));

        // Tap three untapped tokens you control: Draw a card.
        this.addAbility(new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1),
                new TapTargetCost(new TargetControlledPermanent(3, filter))
        ));

        // Tap four untapped tokens you control: Put three +1/+1 counters on Baylen, the Haymaker. It gains trample until end of turn.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)),
                new TapTargetCost(new TargetControlledPermanent(4, filter))
        );
        ability.addEffect(new GainAbilitySourceEffect(TrampleAbility.getInstance(), Duration.EndOfTurn)
                .setText("it gains trample until end of turn"));
        this.addAbility(ability);
    }

    private BaylenTheHaymaker(final BaylenTheHaymaker card) {
        super(card);
    }

    @Override
    public BaylenTheHaymaker copy() {
        return new BaylenTheHaymaker(this);
    }
}
