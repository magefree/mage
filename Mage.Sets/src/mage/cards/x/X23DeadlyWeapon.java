package mage.cards.x;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 *
 * @author muz
 */
public final class X23DeadlyWeapon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.MUTANT, "another Mutant");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public X23DeadlyWeapon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.BERSERKER);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever another Mutant you control enters, put a +1/+1 counter on that creature and a +1/+1 counter on X-23.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()), filter, false, SetTargetPointer.PERMANENT);
        ability.addEffect(new AddCountersSourceEffect(CounterType.P1P1.createInstance())
            .setText("and a +1/+1 counter on {this}"));
        this.addAbility(ability);

    }

    private X23DeadlyWeapon(final X23DeadlyWeapon card) {
        super(card);
    }

    @Override
    public X23DeadlyWeapon copy() {
        return new X23DeadlyWeapon(this);
    }
}
