
package mage.cards.q;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author magenoxx_at_gmail.com
 */
public final class QuietSpeculation extends CardImpl {

    private static final FilterCard filterCard = new FilterCard("cards with flashback");

    static {
        filterCard.add(new AbilityPredicate(FlashbackAbility.class));
    }

    public QuietSpeculation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Search target player's library for up to three cards with flashback and put them into that player's graveyard. Then the player shuffles their library.
        TargetCardInLibrary target = new TargetCardInLibrary(0, 3, filterCard);
        this.getSpellAbility().addEffect(new SearchLibraryPutInGraveEffect(target));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private QuietSpeculation(final QuietSpeculation card) {
        super(card);
    }

    @Override
    public QuietSpeculation copy() {
        return new QuietSpeculation(this);
    }
}

class SearchLibraryPutInGraveEffect extends SearchEffect {

    public SearchLibraryPutInGraveEffect(TargetCardInLibrary target) {
        super(target, Outcome.Neutral);
        staticText = "Search target player's library for up to three cards with flashback and put them into that player's graveyard. Then the player shuffles.";
    }

    private SearchLibraryPutInGraveEffect(final SearchLibraryPutInGraveEffect effect) {
        super(effect);
    }

    @Override
    public SearchLibraryPutInGraveEffect copy() {
        return new SearchLibraryPutInGraveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        UUID targetPlayerID = source.getFirstTarget();
        if (controller == null) {
            return false;
        }
        if (targetPlayerID != null && controller.searchLibrary(target, source, game, targetPlayerID)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl(target.getTargets());
                controller.revealCards("Quiet Speculation", cards, game);
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}
