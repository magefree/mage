package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnmarkedGrave extends CardImpl {

    public UnmarkedGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Search your library for a nonlegendary card, put that card into your graveyard, then shuffle.
        this.getSpellAbility().addEffect(new UnmarkedGraveEffect());
    }

    private UnmarkedGrave(final UnmarkedGrave card) {
        super(card);
    }

    @Override
    public UnmarkedGrave copy() {
        return new UnmarkedGrave(this);
    }
}

class UnmarkedGraveEffect extends SearchEffect {

    private static final FilterCard filter = new FilterCard("nonlegendary card");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public UnmarkedGraveEffect() {
        super(new TargetCardInLibrary(filter), Outcome.Neutral);
        staticText = "search your library for a nonlegendary card, put that card into your graveyard, then shuffle";
    }

    private UnmarkedGraveEffect(final UnmarkedGraveEffect effect) {
        super(effect);
    }

    @Override
    public UnmarkedGraveEffect copy() {
        return new UnmarkedGraveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.GRAVEYARD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
