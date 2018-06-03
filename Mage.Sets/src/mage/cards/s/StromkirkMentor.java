
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class StromkirkMentor extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another target Vampire you control");

    static {
        filter.add(new AnotherPredicate());
        filter.add(new SubtypePredicate(SubType.VAMPIRE));
    }

    public StromkirkMentor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When Stromkirk Mentor enters the battlefield, put a +1/+1 counter on another target Vampire you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    public StromkirkMentor(final StromkirkMentor card) {
        super(card);
    }

    @Override
    public StromkirkMentor copy() {
        return new StromkirkMentor(this);
    }
}
