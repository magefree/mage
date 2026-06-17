package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SheHulkJenniferWalters extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledLandPermanent("a land");

    public SheHulkJenniferWalters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GAMMA);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // {2}{R}, Sacrifice a land: Draw a card and put a +1/+1 counter on She-Hulk.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new SacrificeTargetCost(filter));
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance()).concatBy("and"));
        this.addAbility(ability);
    }

    private SheHulkJenniferWalters(final SheHulkJenniferWalters card) {
        super(card);
    }

    @Override
    public SheHulkJenniferWalters copy() {
        return new SheHulkJenniferWalters(this);
    }
}
