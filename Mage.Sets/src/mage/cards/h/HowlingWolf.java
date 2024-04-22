
package mage.cards.h;

import java.util.UUID;
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

/**
 *
 * @author fireshoes
 */
public final class HowlingWolf extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("cards named Howling Wolf");

    static {
        filter.add(new NamePredicate("Howling Wolf"));
    }

    public HowlingWolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Howling Wolf enters the battlefield, you may search your library for up to three cards named Howling Wolf, reveal them, and put them into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private HowlingWolf(final HowlingWolf card) {
        super(card);
    }

    @Override
    public HowlingWolf copy() {
        return new HowlingWolf(this);
    }
}
