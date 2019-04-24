
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.RemovedCountersForCostValue;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class InfusedArrows extends CardImpl {

    public InfusedArrows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Sunburst
        this.addAbility(new SunburstAbility(this));
        // {tap}, Remove X charge counters from Infused Arrows: Target creature gets -X/-X until end of turn.
        DynamicValue value = new SignInversionDynamicValue(new RemovedCountersForCostValue());
        Effect effect = new BoostTargetEffect(value, value, Duration.EndOfTurn);
        effect.setText("Target creature gets -X/-X until end of turn");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.CHARGE.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public InfusedArrows(final InfusedArrows card) {
        super(card);
    }

    @Override
    public InfusedArrows copy() {
        return new InfusedArrows(this);
    }
}
