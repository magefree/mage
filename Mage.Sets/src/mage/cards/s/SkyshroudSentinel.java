
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class SkyshroudSentinel extends CardImpl {

    private static final FilterCard filter = new FilterCard("cards named Skyshroud Sentinel");

    static {
        filter.add(new NamePredicate("Skyshroud Sentinel"));
    }

    public SkyshroudSentinel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.ELF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Skyshroud Sentinel enters the battlefield, you may search your library for up to three cards named Skyshroud Sentinel, reveal them, and put them into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private SkyshroudSentinel(final SkyshroudSentinel card) {
        super(card);
    }

    @Override
    public SkyshroudSentinel copy() {
        return new SkyshroudSentinel(this);
    }
}
