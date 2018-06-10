
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class IorRuinExpedition extends CardImpl {

    public IorRuinExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Ior Ruin Expedition.
        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));

        // Remove three quest counters from Ior Ruin Expedition and sacrifice it: Draw two cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public IorRuinExpedition(final IorRuinExpedition card) {
        super(card);
    }

    @Override
    public IorRuinExpedition copy() {
        return new IorRuinExpedition(this);
    }
}
