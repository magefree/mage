
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SerratedArrows extends CardImpl {

    public SerratedArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Serrated Arrows enters the battlefield with three arrowhead counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.ARROWHEAD.createInstance(3));
        effect.setText("with three arrowhead counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));
        // At the beginning of your upkeep, if there are no arrowhead counters on Serrated Arrows, sacrifice it.
        effect = new ConditionalOneShotEffect(new SacrificeSourceEffect(), new SourceHasCounterCondition(CounterType.ARROWHEAD, 0, 0),
                "if there are no arrowhead counters on {this}, sacrifice it");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, effect, TargetController.YOU, false, false));
        // {T}, Remove an arrowhead counter from Serrated Arrows: Put a -1/-1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()),
                new TapSourceCost());
        ability.addCost(new RemoveCountersSourceCost(CounterType.ARROWHEAD.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SerratedArrows(final SerratedArrows card) {
        super(card);
    }

    @Override
    public SerratedArrows copy() {
        return new SerratedArrows(this);
    }
}
