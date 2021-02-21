
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
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author LevelX2
 */
public final class SanguineGlorifier extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("another target Vampire you control");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(SubType.VAMPIRE.getPredicate());
    }

    public SanguineGlorifier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Sanguine Glorifier enters the battlefield, put a +1/+1 counter on another target Vampire you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()), false);
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private SanguineGlorifier(final SanguineGlorifier card) {
        super(card);
    }

    @Override
    public SanguineGlorifier copy() {
        return new SanguineGlorifier(this);
    }
}
