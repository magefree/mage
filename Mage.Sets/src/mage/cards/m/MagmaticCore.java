
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author TheElk801
 */
public final class MagmaticCore extends CardImpl {

    public MagmaticCore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // At the beginning of your end step, Magmatic Core deals X damage divided as you choose among any number of target creatures, where X is the number of age counters on it.
        DynamicValue value = new CountersSourceCount(CounterType.AGE);
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new DamageMultiEffect(value)
                        .setText("{this} deals X damage divided as you choose "
                                + "among any number of target creatures,"
                                + " where X is the number of age counters on it."),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanentAmount(value));
        this.addAbility(ability);
    }

    private MagmaticCore(final MagmaticCore card) {
        super(card);
    }

    @Override
    public MagmaticCore copy() {
        return new MagmaticCore(this);
    }
}
