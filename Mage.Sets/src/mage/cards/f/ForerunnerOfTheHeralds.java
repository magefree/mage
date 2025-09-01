
package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ForerunnerOfTheHeralds extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.MERFOLK);
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.MERFOLK, "another Merfolk you control");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public ForerunnerOfTheHeralds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Forerunner of the Heralds enters the battlefield, you may search your library for a Merfolk card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever another Merfolk you control enters, put a +1/+1 counter on Forerunner of the Heralds.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filter2
        ));
    }

    private ForerunnerOfTheHeralds(final ForerunnerOfTheHeralds card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheHeralds copy() {
        return new ForerunnerOfTheHeralds(this);
    }
}
