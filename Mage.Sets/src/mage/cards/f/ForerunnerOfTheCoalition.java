package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.search.SearchLibraryPutOnLibraryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class ForerunnerOfTheCoalition extends CardImpl {

    private static final FilterCard filter = new FilterCard(SubType.PIRATE);
    private static final FilterPermanent filter2
            = new FilterControlledPermanent(SubType.PIRATE, "another Pirate you control");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public ForerunnerOfTheCoalition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Forerunner of the Coalition enters the battlefield, you may search your library for a Pirate card, reveal it, then shuffle your library and put that card on top of it.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutOnLibraryEffect(new TargetCardInLibrary(filter), true), true
        ));

        // Whenever another Pirate you control enters, each opponent loses 1 life.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new LoseLifeOpponentsEffect(1), filter2
        ));
    }

    private ForerunnerOfTheCoalition(final ForerunnerOfTheCoalition card) {
        super(card);
    }

    @Override
    public ForerunnerOfTheCoalition copy() {
        return new ForerunnerOfTheCoalition(this);
    }
}
