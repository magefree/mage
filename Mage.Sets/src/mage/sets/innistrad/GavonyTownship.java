package mage.sets.innistrad;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

public class GavonyTownship extends CardImpl<GavonyTownship> {
    public GavonyTownship(UUID ownerId) {
        super(ownerId, 239, "Gavony Township", Constants.Rarity.RARE, new Constants.CardType[]{Constants.CardType.LAND}, null);
        this.expansionSetCode = "ISD";

        this.addAbility(new ColorlessManaAbility());
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(), new FilterControlledCreaturePermanent("creature you control")), new ManaCostsImpl("{2}{G}{W}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    public GavonyTownship(final GavonyTownship card) {
        super(card);
    }

    @Override
    public GavonyTownship copy() {
        return new GavonyTownship(this);
    }
}
