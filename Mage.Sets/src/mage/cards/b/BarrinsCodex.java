
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;

/**
 *
 * @author Backfir3
 */
public final class BarrinsCodex extends CardImpl {

    public BarrinsCodex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        //At the beginning of your upkeep, you may put a page counter on Barrin's Codex.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.PAGE.createInstance()), TargetController.YOU, true));

        //{4}, {T}, Sacrifice Barrin's Codex: Draw X cards, where X is the number of page counters on Barrin's Codex.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(new CountersSourceCount(CounterType.PAGE)), new ManaCostsImpl<>("{4}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private BarrinsCodex(final BarrinsCodex card) {
        super(card);
    }

    @Override
    public BarrinsCodex copy() {
        return new BarrinsCodex(this);
    }
}
