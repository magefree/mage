package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.target.common.TargetCardInGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author North
 */
public final class SurgicalExtraction extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    public SurgicalExtraction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B/P}");

        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard,
        // hand, and library for any number of cards with the same name as that card and exile them.
        // Then that player shuffles their library.
        this.getSpellAbility().addEffect(new SurgicalExtractionEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private SurgicalExtraction(final SurgicalExtraction card) {
        super(card);
    }

    @Override
    public SurgicalExtraction copy() {
        return new SurgicalExtraction(this);
    }
}

class SurgicalExtractionEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    SurgicalExtractionEffect() {
        super(true, "its owner's", "any number of cards with the same name as that card");
        this.staticText = "Choose target card in a graveyard other than a basic land card. "
                + "Search its owner's graveyard, hand, and library for any number of cards "
                + "with the same name as that card and exile them. Then that player shuffles";
    }

    private SurgicalExtractionEffect(final SurgicalExtractionEffect effect) {
        super(effect);
    }

    @Override
    public SurgicalExtractionEffect copy() {
        return new SurgicalExtractionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card chosenCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (chosenCard != null) {
            return applySearchAndExile(game, source, CardUtil.getCardNameForSameNameSearch(chosenCard), chosenCard.getOwnerId());
        }
        return false;
    }
}
