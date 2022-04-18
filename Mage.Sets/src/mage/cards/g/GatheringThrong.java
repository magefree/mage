package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GatheringThrong extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Gathering Throng");

    static {
        filter.add(new NamePredicate("Gathering Throng"));
    }

    public GatheringThrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Gathering Throng enters the battlefield, you may search your library for any number of cards named Gathering Throng, reveal them, put them into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(0, Integer.MAX_VALUE, filter), true, true
        ), true));
    }

    private GatheringThrong(final GatheringThrong card) {
        super(card);
    }

    @Override
    public GatheringThrong copy() {
        return new GatheringThrong(this);
    }
}
// nothing to see here, folks
