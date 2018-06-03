
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author JayDi85
 */
public final class JadeBearer extends CardImpl {

    private static final FilterCreaturePermanent filterYourAnotherMerfolk = new FilterCreaturePermanent();
    static {
        filterYourAnotherMerfolk.add(new AnotherPredicate());
        filterYourAnotherMerfolk.add(new SubtypePredicate(SubType.MERFOLK));
        filterYourAnotherMerfolk.add(new ControllerPredicate(TargetController.YOU));
        filterYourAnotherMerfolk.setMessage("another " + SubType.MERFOLK.toString() + " you control");
    }

    public JadeBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Jade Bearer enters the battlefield, put a +1/+1 counter on another target Merfolk you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetCreaturePermanent(filterYourAnotherMerfolk));
        this.addAbility(ability);
    }

    public JadeBearer(final JadeBearer card) {
        super(card);
    }

    @Override
    public JadeBearer copy() {
        return new JadeBearer(this);
    }
}