
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DestroySourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author L_J
 */
public final class VoodooDoll extends CardImpl {

    public VoodooDoll(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{6}");

        // At the beginning of your upkeep, put a pin counter on Voodoo Doll.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.PIN.createInstance()), TargetController.YOU, false));
        // At the beginning of your end step, if Voodoo Doll is untapped, destroy Voodoo Doll and it deals damage to you equal to the number of pin counters on it.
        Ability ability = new BeginningOfEndStepTriggeredAbility(Zone.BATTLEFIELD, new DestroySourceEffect(), TargetController.YOU, 
                new InvertCondition(SourceTappedCondition.instance), false);
        ability.addEffect(new DamageControllerEffect(new CountersSourceCount(CounterType.PIN)));
        this.addAbility(ability);
        // {X}{X}, {T}: Voodoo Doll deals damage equal to the number of pin counters on it to any target. X is the number of pin counters on Voodoo Doll.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(new CountersSourceCount(CounterType.PIN)), new ManaCostsImpl("{X}{X}"));
        ability2.addCost(new TapSourceCost());
        ability2.addTarget(new TargetAnyTarget());
        for (VariableCost cost : ability2.getManaCosts().getVariableCosts()) {
            if (cost instanceof VariableManaCost) {
                ((VariableManaCost) cost).setMaxX(0);
                break;
            }
        }
        this.addAbility(ability2);
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            Permanent sourcePermanent = game.getPermanent(ability.getSourceId());
            if (sourcePermanent != null) {
                int pin = sourcePermanent.getCounters(game).getCount(CounterType.PIN);
                ability.getManaCostsToPay().clear();
                ability.getManaCostsToPay().add(0, new GenericManaCost(pin * 2));
            }
        }
    }

    public VoodooDoll(final VoodooDoll card) {
        super(card);
    }

    @Override
    public VoodooDoll copy() {
        return new VoodooDoll(this);
    }
}
