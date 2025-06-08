package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SerratedArrows extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.ARROWHEAD, ComparisonType.EQUAL_TO, 0);

    public SerratedArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Serrated Arrows enters the battlefield with three arrowhead counters on it.
        Effect effect = new AddCountersSourceEffect(CounterType.ARROWHEAD.createInstance(3));
        effect.setText("with three arrowhead counters on it");
        this.addAbility(new EntersBattlefieldAbility(effect));

        // At the beginning of your upkeep, if there are no arrowhead counters on Serrated Arrows, sacrifice it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect().setText("sacrifice it")).withInterveningIf(condition));

        // {T}, Remove an arrowhead counter from Serrated Arrows: Put a -1/-1 counter on target creature.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.M1M1.createInstance()), new TapSourceCost()
        );
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
