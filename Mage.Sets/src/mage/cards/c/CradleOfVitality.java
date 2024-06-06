package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedGainedLifeValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CradleOfVitality extends CardImpl {

    public CradleOfVitality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // Whenever you gain life, you may pay {1}{W}. If you do, put a +1/+1 counter on target creature for each 1 life you gained.
        Ability ability = new GainLifeControllerTriggeredAbility(new DoIfCostPaid(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(), SavedGainedLifeValue.MANY)
                        .setText("put a +1/+1 counter on target creature for each 1 life you gained"),
                new ManaCostsImpl<>("{1}{W}")
        ), false, true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private CradleOfVitality(final CradleOfVitality card) {
        super(card);
    }

    @Override
    public CradleOfVitality copy() {
        return new CradleOfVitality(this);
    }
}