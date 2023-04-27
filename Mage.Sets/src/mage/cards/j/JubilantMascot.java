
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class JubilantMascot extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other target creatures");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public JubilantMascot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of combat on your turn, you may pay {3}{W}. If you do, support 2. (Put a +1/+1 counter on each of up to two other target creatures.)
        Ability ability = new BeginningOfCombatTriggeredAbility(
                new DoIfCostPaid(
                        new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                                .setText("support 2"),
                        new ManaCostsImpl<>("{3}{W}")
                ), TargetController.YOU, false);
        ability.addTarget(new TargetCreaturePermanent(0, 2, filter, false));
        this.addAbility(ability);
    }

    private JubilantMascot(final JubilantMascot card) {
        super(card);
    }

    @Override
    public JubilantMascot copy() {
        return new JubilantMascot(this);
    }
}
