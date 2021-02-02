
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AdvocateOfTheBeast extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Beast creature you control");
    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(SubType.BEAST.getPredicate());
    }

    public AdvocateOfTheBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, put a +1/+1 counter on target Beast creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), TargetController.YOU, false);
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private AdvocateOfTheBeast(final AdvocateOfTheBeast card) {
        super(card);
    }

    @Override
    public AdvocateOfTheBeast copy() {
        return new AdvocateOfTheBeast(this);
    }
}
