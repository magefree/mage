
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author fireshoes
 */
public final class WelkinHawk extends CardImpl {

    private static final FilterCard filter = new FilterCard("card named Welkin Hawk");

    static {
        filter.add(new NamePredicate("Welkin Hawk"));
    }

    public WelkinHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Welkin Hawk dies, you may search your library for a card named Welkin Hawk, reveal that card, put it into your hand, then shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new DiesSourceTriggeredAbility(new SearchLibraryPutInHandEffect(target, true, true), true));
    }

    private WelkinHawk(final WelkinHawk card) {
        super(card);
    }

    @Override
    public WelkinHawk copy() {
        return new WelkinHawk(this);
    }
}
