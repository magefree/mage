package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class TradeCaravan extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    private static final Condition condition = new CompoundCondition(
            "during an opponent's upkeep",
            OnOpponentsTurnCondition.instance,
            new IsStepCondition(PhaseStep.UPKEEP, false)
    );

    public TradeCaravan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a currency counter on Trade Caravan.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CURRENCY.createInstance())));

        // Remove two currency counters from Trade Caravan: Untap target basic land. Activate this ability only during an opponent's upkeep.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new UntapTargetEffect(), new RemoveCountersSourceCost(CounterType.CURRENCY.createInstance(2)), condition
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private TradeCaravan(final TradeCaravan card) {
        super(card);
    }

    @Override
    public TradeCaravan copy() {
        return new TradeCaravan(this);
    }
}
