package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class InfusedArrows extends CardImpl {

    private static final DynamicValue xValue = new SignInversionDynamicValue(RemovedCountersForCostValue.instance);

    public InfusedArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // {tap}, Remove X charge counters from Infused Arrows: Target creature gets -X/-X until end of turn.
        Ability ability = new SimpleActivatedAbility(new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private InfusedArrows(final InfusedArrows card) {
        super(card);
    }

    @Override
    public InfusedArrows copy() {
        return new InfusedArrows(this);
    }
}
