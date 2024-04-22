package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
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

    private static final DynamicValue xValueElves = new PermanentsOnBattlefieldCount(filterElves);
    private static final Hint hintElves = new ValueHint("Elves you control", xValueElves);
    private static final DynamicValue xValueWolves = new PermanentsOnBattlefieldCount(filterWolves);
    private static final Hint hintWolves = new ValueHint("Wolves you control", xValueWolves);

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
                        CounterType.P1P1.createInstance(),
                        xValueElves,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("put X +1/+1 counters on each creature you control, where X is the number of Elves you control"));
        ability.addEffect(new DrawCardSourceControllerEffect(xValueWolves));

        this.addAbility(ability.addHint(hintElves).addHint(hintWolves));
    }

    private VojaJawsOfTheConclave(final VojaJawsOfTheConclave card) {
        super(card);
    }

    @Override
    public VojaJawsOfTheConclave copy() {
        return new VojaJawsOfTheConclave(this);
    }
}
