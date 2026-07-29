package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 * @author muz
 */
public final class WhiteWidowYelenaBelova extends CardImpl {

    private static final FilterPermanent filter
        = new FilterControlledCreaturePermanent("a creature you control with deathtouch");

    static {
        filter.add(new AbilityPredicate(DeathtouchAbility.class));
    }

    public WhiteWidowYelenaBelova(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever a creature you control with deathtouch deals combat damage to a player, put a +1/+1 counter on it.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
            new AddCountersTargetEffect(CounterType.P1P1.createInstance()),
            filter,
            false, SetTargetPointer.PERMANENT, true
        ));
    }

    private WhiteWidowYelenaBelova(final WhiteWidowYelenaBelova card) {
        super(card);
    }

    @Override
    public WhiteWidowYelenaBelova copy() {
        return new WhiteWidowYelenaBelova(this);
    }
}
