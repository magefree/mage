package mage.cards.s;

import mage.abilities.common.YoureDealtDamageTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SunDroplet extends CardImpl {

    public SunDroplet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Whenever you're dealt damage, put that many charge counters on Sun Droplet.
        this.addAbility(new YoureDealtDamageTriggeredAbility(new AddCountersSourceEffect(
                CounterType.CHARGE.createInstance(), SavedDamageValue.MANY), false));

        // At the beginning of each upkeep, you may remove a charge counter from Sun Droplet. If you do, you gain 1 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.ANY, new DoIfCostPaid(
                        new GainLifeEffect(1), new RemoveCountersSourceCost(CounterType.CHARGE.createInstance())
                ), false
        ));
    }

    private SunDroplet(final SunDroplet card) {
        super(card);
    }

    @Override
    public SunDroplet copy() {
        return new SunDroplet(this);
    }
}
