

package mage.cards.s;

import java.util.UUID;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class SunspringExpedition extends CardImpl {

    public SunspringExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");


        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        Costs<Cost> costs = new CostsImpl<>();
        costs.add(new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)));
        costs.add(new SacrificeSourceCost());
        ActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(8), costs);
        this.addAbility(ability);
    }

    public SunspringExpedition(final SunspringExpedition card) {
        super(card);
    }

    @Override
    public SunspringExpedition copy() {
        return new SunspringExpedition(this);
    }

}
