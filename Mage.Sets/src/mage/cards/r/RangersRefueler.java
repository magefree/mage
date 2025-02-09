package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.AddCardTypeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.CrewAbility;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.other.ExhaustAbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RangersRefueler extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("an exhaust ability");

    static {
        filter.add(ExhaustAbilityPredicate.instance);
    }

    public RangersRefueler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you activate an exhaust ability, draw a card.
        this.addAbility(new ActivateAbilityTriggeredAbility(
                new DrawCardSourceControllerEffect(1), filter, SetTargetPointer.NONE
        ));

        // Exhaust -- {4}: This Vehicle becomes an artifact creature. Put a +1/+1 counter on it.
        Ability ability = new ExhaustAbility(new AddCardTypeSourceEffect(
                Duration.Custom, CardType.ARTIFACT, CardType.CREATURE
        ), new GenericManaCost(4));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
                .setText("put a +1/+1 counter on it"));
        this.addAbility(ability);

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private RangersRefueler(final RangersRefueler card) {
        super(card);
    }

    @Override
    public RangersRefueler copy() {
        return new RangersRefueler(this);
    }
}
