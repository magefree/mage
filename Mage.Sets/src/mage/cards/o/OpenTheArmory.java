package mage.cards.o;

import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class OpenTheArmory extends CardImpl {

    private static final FilterCard auraOrEquipmentTarget = new FilterCard("Aura or Equipment card");

    static {
        auraOrEquipmentTarget.add(Predicates.or(
                SubType.EQUIPMENT.getPredicate(),
                SubType.AURA.getPredicate()));
    }

    public OpenTheArmory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Search your library for an Aura or Equipment card, reveal it, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(1, 1, auraOrEquipmentTarget), true));
    }

    private OpenTheArmory(final OpenTheArmory card) {
        super(card);
    }

    @Override
    public OpenTheArmory copy() {
        return new OpenTheArmory(this);
    }
}
