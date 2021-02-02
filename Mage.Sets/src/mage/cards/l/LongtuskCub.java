
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public final class LongtuskCub extends CardImpl {

    public LongtuskCub(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Longtusk Cub deals combat damage to a player, you get {E}{E}.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new GetEnergyCountersControllerEffect(2), false));

        // Pay {E}{E}: Put a +1/+1 counter on Longtusk Cub.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()), new PayEnergyCost(2)));
    }

    private LongtuskCub(final LongtuskCub card) {
        super(card);
    }

    @Override
    public LongtuskCub copy() {
        return new LongtuskCub(this);
    }
}
