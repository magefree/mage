
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.condition.common.OnOpponentsTurnCondition;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.PhaseStep;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author L_J
 */
public final class TradeCaravan extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("basic land");

    static {
        filter.add(SuperType.BASIC.getPredicate());
    }

    public TradeCaravan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your upkeep, put a currency counter on Trade Caravan.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.CURRENCY.createInstance()), TargetController.YOU, false));
        // Remove two currency counters from Trade Caravan: Untap target basic land. Activate this ability only during an opponent's upkeep.
        Ability ability = new ConditionalActivatedAbility(Zone.BATTLEFIELD, new UntapTargetEffect(), new RemoveCountersSourceCost(CounterType.CURRENCY.createInstance(2)), 
                new CompoundCondition(OnOpponentsTurnCondition.instance, new IsStepCondition(PhaseStep.UPKEEP, false)), 
                "Remove two currency counters from {this}: Untap target basic land. Activate only during an opponent's upkeep.");
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
