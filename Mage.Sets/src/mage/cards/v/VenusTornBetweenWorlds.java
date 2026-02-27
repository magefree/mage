package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VenusTornBetweenWorlds extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("creature you control with a counter on it");

    static {
        filter.add(CounterAnyPredicate.instance);
    }

    public VenusTornBetweenWorlds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.FROG);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Whenever Venus is dealt damage, put that many +1/+1 counters on her.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(new AddCountersSourceEffect(
                CounterType.P1P1.createInstance(0), SavedDamageValue.MANY
        ), false));

        // Whenever a creature you control with a counter on it deals combat damage to a player, you may pay {U}. If you do, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{U}")
        ), filter, false, SetTargetPointer.NONE, true));
    }

    private VenusTornBetweenWorlds(final VenusTornBetweenWorlds card) {
        super(card);
    }

    @Override
    public VenusTornBetweenWorlds copy() {
        return new VenusTornBetweenWorlds(this);
    }
}
