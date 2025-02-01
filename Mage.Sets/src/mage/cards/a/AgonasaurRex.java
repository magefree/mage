package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgonasaurRex extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("creature or Vehicle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                SubType.VEHICLE.getPredicate()
        ));
    }

    public AgonasaurRex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Cycling {2}{G}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}{G}")));

        // When you cycle this card, put two +1/+1 counters on up to one target creature or Vehicle. It gains trample and indestructible until end of turn.
        Ability ability = new CycleTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        ability.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("it gains trample"));
        ability.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()).setText("and indestructible until end of turn"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);
    }

    private AgonasaurRex(final AgonasaurRex card) {
        super(card);
    }

    @Override
    public AgonasaurRex copy() {
        return new AgonasaurRex(this);
    }
}
