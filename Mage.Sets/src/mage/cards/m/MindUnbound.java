
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

/**
 *
 * @author Loki
 */
public final class MindUnbound extends CardImpl {

    public MindUnbound(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}{U}");


        // At the beginning of your upkeep, put a lore counter on Mind Unbound, then draw a card for each lore counter on Mind Unbound.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.LORE.createInstance()), TargetController.YOU, false);
        ability.addEffect(new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.LORE)).concatBy(", then"));
        this.addAbility(ability);
    }

    private MindUnbound(final MindUnbound card) {
        super(card);
    }

    @Override
    public MindUnbound copy() {
        return new MindUnbound(this);
    }
}
