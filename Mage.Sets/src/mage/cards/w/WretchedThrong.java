package mage.cards.w;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
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
public final class WretchedThrong extends CardImpl {

    private static final FilterCard filter = new FilterCard("a card named Wretched Throng");

    static {
        filter.add(new NamePredicate("Wretched Throng"));
    }

    public WretchedThrong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When Wretched Throng dies, you may search your library for a card named Wretched Throng, reveal it, put it into your hand, then shuffle.
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true, true
        ), true));
    }

    private WretchedThrong(final WretchedThrong card) {
        super(card);
    }

    @Override
    public WretchedThrong copy() {
        return new WretchedThrong(this);
    }
}
