package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LuxaRiverShrine extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.BRICK, 3);

    public LuxaRiverShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {1}, {T}: You gain 1 life. Put a brick counter on Luxa River Shrine.
        Ability ability = new SimpleActivatedAbility(new GainLifeEffect(1), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addEffect(new AddCountersSourceEffect(CounterType.BRICK.createInstance()));
        this.addAbility(ability);

        // {T}: You gain 2 life. Activate this ability only if there are three or more brick counters on Luxa River Shrine.
        this.addAbility(new ActivateIfConditionActivatedAbility(new GainLifeEffect(2), new TapSourceCost(), condition));
    }

    private LuxaRiverShrine(final LuxaRiverShrine card) {
        super(card);
    }

    @Override
    public LuxaRiverShrine copy() {
        return new LuxaRiverShrine(this);
    }
}
