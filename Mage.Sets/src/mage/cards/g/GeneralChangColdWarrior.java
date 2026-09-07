package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class GeneralChangColdWarrior extends CardImpl {

    public GeneralChangColdWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.KLINGON);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.OFFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {1}, Sacrifice a creature: Put a +1/+1 counter on each Klingon you control.
        Ability ability = new SimpleActivatedAbility(
            new AddCountersAllEffect(
                CounterType.P1P1.createInstance(),
                new FilterControlledPermanent(SubType.KLINGON, "each Klingon you control")
            ),
            new GenericManaCost(1)
        );
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private GeneralChangColdWarrior(final GeneralChangColdWarrior card) {
        super(card);
    }

    @Override
    public GeneralChangColdWarrior copy() {
        return new GeneralChangColdWarrior(this);
    }
}
