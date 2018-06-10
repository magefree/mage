
package mage.cards.g;

import java.util.UUID;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class GiftOfEstates extends CardImpl {
    
    private static final FilterCard filter = new FilterCard("Plains cards");
    
    static {
        filter.add(new SubtypePredicate(SubType.PLAINS));
    }
    

    public GiftOfEstates(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");


        // If an opponent controls more lands than you, search your library for up to three Plains cards, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(0, 3, filter), true),
                new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS)));
    }

    public GiftOfEstates(final GiftOfEstates card) {
        super(card);
    }

    @Override
    public GiftOfEstates copy() {
        return new GiftOfEstates(this);
    }
}
