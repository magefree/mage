
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterCreatureCard;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author North
 */
public final class SoulStairExpedition extends CardImpl {

    public SoulStairExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");


        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Soul Stair Expedition.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        // Remove three quest counters from Soul Stair Expedition and sacrifice it: Return up to two target creature cards from your graveyard to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)));
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(0, 2, new FilterCreatureCard("creature cards from your graveyard")));
        this.addAbility(ability);
    }

    public SoulStairExpedition(final SoulStairExpedition card) {
        super(card);
    }

    @Override
    public SoulStairExpedition copy() {
        return new SoulStairExpedition(this);
    }
}
