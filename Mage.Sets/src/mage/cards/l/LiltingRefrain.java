
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public final class LiltingRefrain extends CardImpl {

    public LiltingRefrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{U}");

        // At the beginning of your upkeep, you may put a verse counter on Lilting Refrain.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.VERSE.createInstance()), TargetController.YOU, true));

        // Sacrifice Lilting Refrain: Counter target spell unless its controller pays {X}, where X is the number of verse counters on Lilting Refrain.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CounterUnlessPaysEffect(new CountersSourceCount(CounterType.VERSE)), new SacrificeSourceCost());
        ability.addTarget(new TargetSpell());
        this.addAbility(ability);
    }

    private LiltingRefrain(final LiltingRefrain card) {
        super(card);
    }

    @Override
    public LiltingRefrain copy() {
        return new LiltingRefrain(this);
    }
}
