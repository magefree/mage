
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class ThreeDreams extends CardImpl {

    public ThreeDreams(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{W}");


        // Search your library for up to three Aura cards with different names, reveal them, and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new ThreeDreamsTarget(), true, true));
    }

    public ThreeDreams(final ThreeDreams card) {
        super(card);
    }

    @Override
    public ThreeDreams copy() {
        return new ThreeDreams(this);
    }
}

class ThreeDreamsTarget extends TargetCardInLibrary {

    private static final FilterCard aurafilter = new FilterCard("Aura cards with different names");
    static {
        aurafilter.add(new SubtypePredicate(SubType.AURA));
    }

    public ThreeDreamsTarget() {
        super(0, 3, aurafilter.copy());
    }

    public ThreeDreamsTarget(final ThreeDreamsTarget target) {
        super(target);
    }

    @Override
    public ThreeDreamsTarget copy() {
        return new ThreeDreamsTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            // check if card with that name was selected before
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            return filter.match(card, game);
        }
        return false;
    }
}
