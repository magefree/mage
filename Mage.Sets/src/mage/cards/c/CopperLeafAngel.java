package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeXTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledLandPermanent;

import java.util.UUID;

/**
 * @author Rene - bugisemail at gmail.com
 */
public final class CopperLeafAngel extends CardImpl {

    public CopperLeafAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {tap}, Sacrifice X lands: Put X +1/+1 counters on Copper-Leaf Angel.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(), GetXValue.instance, false), new TapSourceCost());
        ability.addCost(new SacrificeXTargetCost(new FilterControlledLandPermanent("lands")));
        this.addAbility(ability);
    }

    private CopperLeafAngel(final CopperLeafAngel card) {
        super(card);
    }

    @Override
    public CopperLeafAngel copy() {
        return new CopperLeafAngel(this);
    }
}
