package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MindspringMerfolk extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent(SubType.MERFOLK, "Merfolk creature you control");

    public MindspringMerfolk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Exhaust -- {X}{U}{U}, {T}: Draw X cards. Put a +1/+1 counter on each Merfolk creature you control.
        Ability ability = new ExhaustAbility(new DrawCardSourceControllerEffect(GetXValue.instance), new ManaCostsImpl<>("{X}{U}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersAllEffect(CounterType.P1P1.createInstance(), filter));
        this.addAbility(ability);
    }

    private MindspringMerfolk(final MindspringMerfolk card) {
        super(card);
    }

    @Override
    public MindspringMerfolk copy() {
        return new MindspringMerfolk(this);
    }
}
