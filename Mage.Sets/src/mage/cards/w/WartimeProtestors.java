package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WartimeProtestors extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.ALLY, "another Ally");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WartimeProtestors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever another Ally you control enters, put a +1/+1 counter on that creature and it gains haste until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD,
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()).setText("put a +1/+1 counter on that creature"),
            filter, false, SetTargetPointer.PERMANENT);
        ability.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
            .setText("and it gains haste until end of turn"));
        this.addAbility(ability);
    }

    private WartimeProtestors(final WartimeProtestors card) {
        super(card);
    }

    @Override
    public WartimeProtestors copy() {
        return new WartimeProtestors(this);
    }
}
