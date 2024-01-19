package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class VojaJawsOfTheConclave extends CardImpl {

    private static final FilterControlledPermanent filterElves = new FilterControlledPermanent(SubType.ELF);
    private static final FilterControlledPermanent filterWolves = new FilterControlledPermanent(SubType.WOLF);

    public VojaJawsOfTheConclave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Ward {3}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{3}"), false));

        // Whenever Voja, Jaws of the Conclave attacks, put X +1/+1 counters on each creature you control,
        // where X is the number of Elves you control. Draw a card for each Wolf you control.
        Ability ability = new AttacksTriggeredAbility(
                new AddCountersAllEffect(
                        CounterType.P1P1.createInstance(0), // Set amount to 0, otherwise AddCountersAllEffect.apply() will default to amount = 1
                        new PermanentsOnBattlefieldCount(filterElves),
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("put X +1/+1 counters on each creature you control, where X is the number of Elves you control"));
        ability.addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filterWolves)));

        this.addAbility(ability);
    }

    private VojaJawsOfTheConclave(final VojaJawsOfTheConclave card) {
        super(card);
    }

    @Override
    public VojaJawsOfTheConclave copy() {
        return new VojaJawsOfTheConclave(this);
    }
}
