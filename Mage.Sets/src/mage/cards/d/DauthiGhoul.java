
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LoneFox
 */
public final class DauthiGhoul extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature with shadow");

    static {
        filter.add(new AbilityPredicate(ShadowAbility.class));
    }

    public DauthiGhoul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.DAUTHI);
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever a creature with shadow dies, put a +1/+1 counter on Dauthi Ghoul.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, filter));
    }

    private DauthiGhoul(final DauthiGhoul card) {
        super(card);
    }

    @Override
    public DauthiGhoul copy() {
        return new DauthiGhoul(this);
    }
}
