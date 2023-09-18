
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterBySubtypeCard;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author JayDi85
 */
public final class ForerunnerOfTheHeralds extends CardImpl {

    private static final FilterPermanent filterAnotherMerfolk = new FilterPermanent(SubType.MERFOLK, "another " + SubType.MERFOLK.toString());
    static {
        filterAnotherMerfolk.add(AnotherPredicate.instance);
        filterAnotherMerfolk.add(TargetController.YOU.getControllerPredicate());
    }

    public ForerunnerOfTheHeralds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);


        // When Forerunner of the Heralds enters the battlefield, you may search your library for a Merfolk card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(
                new EntersBattlefieldTriggeredAbility(
                        new SearchLibraryPutOnLibraryEffect(
                                new TargetCardInLibrary(new FilterBySubtypeCard(SubType.MERFOLK)),
                                true
                        ),
                true
                )
        );

        // Whenever another Merfolk enters the battlefield under your control, put a +1/+1 counter on Forerunner of the Heralds.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), filterAnotherMerfolk);
        this.addAbility(ability);
    }

    private ForerunnerOfTheHeralds(final ForerunnerOfTheHeralds card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheHeralds copy() {
        return new ForerunnerOfTheHeralds(this);
    }
}
