
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
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

public final class ScreamingSeahawk extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("card named Screaming Seahawk");

    static {
        filter.add(new NamePredicate("Screaming Seahawk"));
    }

    public ScreamingSeahawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Screaming Seahawk enters the battlefield, you may search your library for a card named Screaming Seahawk, reveal it, and put it into your hand. If you do, shuffle your library.
        TargetCardInLibrary target = new TargetCardInLibrary(1, 1, filter);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(target, true), true));
    }

    private ScreamingSeahawk(final ScreamingSeahawk card) {
        super(card);
    }

    @Override
    public ScreamingSeahawk copy() {
        return new ScreamingSeahawk(this);
    }
}
