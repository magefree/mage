
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class RecruiterOfTheGuard extends CardImpl {
    
    private static final FilterCreatureCard filter = new FilterCreatureCard("a creature card with toughness 2 or less");
    static {
        filter.add(new ToughnessPredicate(ComparisonType.FEWER_THAN, 3));
    }

    public RecruiterOfTheGuard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Recruiter of the Guard enters the battlefield, you may search your library for a creature card with toughness 2 or less, 
        // reveal it, put it into your hand, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
                new TargetCardInLibrary(filter), true), true));
    }

    private RecruiterOfTheGuard(final RecruiterOfTheGuard card) {
        super(card);
    }

    @Override
    public RecruiterOfTheGuard copy() {
        return new RecruiterOfTheGuard(this);
    }
}
