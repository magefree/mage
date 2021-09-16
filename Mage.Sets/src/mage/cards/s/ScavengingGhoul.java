
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.CreaturesDiedThisTurnCount;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.hint.common.CreaturesDiedThisTurnHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.watchers.common.CreaturesDiedWatcher;

/**
 *
 * @author LoneFox
 */
public final class ScavengingGhoul extends CardImpl {

    public ScavengingGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of each end step, put a corpse counter on Scavenging Ghoul for each creature that died this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new AddCountersSourceEffect(CounterType.CORPSE.createInstance(),
            CreaturesDiedThisTurnCount.instance, true), TargetController.ANY, false).addHint(CreaturesDiedThisTurnHint.instance), new CreaturesDiedWatcher());
        // Remove a corpse counter from Scavenging Ghoul: Regenerate Scavenging Ghoul.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(),
            new RemoveCountersSourceCost(CounterType.CORPSE.createInstance())));
    }

    private ScavengingGhoul(final ScavengingGhoul card) {
        super(card);
    }

    @Override
    public ScavengingGhoul copy() {
        return new ScavengingGhoul(this);
    }
}
