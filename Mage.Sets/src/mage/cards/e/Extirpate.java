package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.common.search.SearchTargetGraveyardHandLibraryForCardNameAndExileEffect;
import mage.abilities.keyword.SplitSecondAbility;
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
 * @author jonubuu
 */
public final class Extirpate extends CardImpl {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(Predicates.and(CardType.LAND.getPredicate(), SuperType.BASIC.getPredicate())));
    }

    public Extirpate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}");

        // Split second
        this.addAbility(new SplitSecondAbility());
        // Choose target card in a graveyard other than a basic land card. 
        // Search its owner's graveyard, hand, and library for all cards with 
        // the same name as that card and exile them. Then that player shuffles their library.
        this.getSpellAbility().addEffect(new ExtirpateEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    private Extirpate(final Extirpate card) {
        super(card);
    }

    @Override
    public Extirpate copy() {
        return new Extirpate(this);
    }
}

class ExtirpateEffect extends SearchTargetGraveyardHandLibraryForCardNameAndExileEffect {

    ExtirpateEffect() {
        super(false, "its owner's", "all cards with the same name as that card");
        this.staticText = "Choose target card in a graveyard other than "
                + "a basic land card. Search its owner's graveyard, hand, "
                + "and library for all cards with the same name "
                + "as that card and exile them. Then that player shuffles";
    }

    private ExtirpateEffect(final ExtirpateEffect effect) {
        super(effect);
    }

    @Override
    public ExtirpateEffect copy() {
        return new ExtirpateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card chosenCard = game.getCard(getTargetPointer().getFirst(game, source));
        if (chosenCard != null) {
            return super.applySearchAndExile(game, source, CardUtil.getCardNameForSameNameSearch(chosenCard), chosenCard.getOwnerId());
        }
        return false;
    }
}
