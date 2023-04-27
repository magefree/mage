
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.MeditateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class JediInstructor extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another target creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public JediInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.TWILEK);
        this.subtype.add(SubType.JEDI);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Jedi Instructor enters the battlefield, you may put a +1/+1 counter on another target creature.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), true);
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);

        // Meditate {1}{W}
        this.addAbility(new MeditateAbility(new ManaCostsImpl<>("{1}{W}")));
    }

    private JediInstructor(final JediInstructor card) {
        super(card);
    }

    @Override
    public JediInstructor copy() {
        return new JediInstructor(this);
    }
}
