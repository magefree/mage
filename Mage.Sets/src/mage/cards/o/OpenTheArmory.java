
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class OpenTheArmory extends CardImpl {

    public OpenTheArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Search your library for an Aura or Equipment card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new OpenTheArmoryTarget(), true, true));
    }

    public OpenTheArmory(final OpenTheArmory card) {
        super(card);
    }

    @Override
    public OpenTheArmory copy() {
        return new OpenTheArmory(this);
    }
}

class OpenTheArmoryTarget extends TargetCardInLibrary {

    private static final FilterCard auraOrEquipmentTarget = new FilterCard("Aura or Equipment card");
    static {
        auraOrEquipmentTarget.add(Predicates.or(
                new SubtypePredicate(SubType.EQUIPMENT),
                new SubtypePredicate(SubType.AURA)));
    }

    public OpenTheArmoryTarget() {
        super(1, 1, auraOrEquipmentTarget.copy());
    }

    public OpenTheArmoryTarget(final OpenTheArmoryTarget target) {
        super(target);
    }

    @Override
    public OpenTheArmoryTarget copy() {
        return new OpenTheArmoryTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            return auraOrEquipmentTarget.match(card, game);
        }
        return false;
    }
}
