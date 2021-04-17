
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author LoneFox
 */
public final class ElvishVanguard extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another Elf");

    static {
        filter.add(SubType.ELF.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public ElvishVanguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever another Elf enters the battlefield, put a +1/+1 counter on Elvish Vanguard.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter));
    }

    private ElvishVanguard(final ElvishVanguard card) {
        super(card);
    }

    @Override
    public ElvishVanguard copy() {
        return new ElvishVanguard(this);
    }
}
