
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Loki
 */
public final class ShardConvergence extends CardImpl {

    public ShardConvergence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");


        // Search your library for a Plains card, an Island card, a Swamp card, and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new ShardConvergenceEffect());
    }

    public ShardConvergence(final ShardConvergence card) {
        super(card);
    }

    @Override
    public ShardConvergence copy() {
        return new ShardConvergence(this);
    }
}

class ShardConvergenceEffect extends OneShotEffect {
    ShardConvergenceEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for a Plains card, an Island card, a Swamp card, and a Mountain card. Reveal those cards and put them into your hand. Then shuffle your library";
    }

    ShardConvergenceEffect(final ShardConvergenceEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Cards cards = new CardsImpl();

        searchLand(player, source, game, cards, "Plains");
        searchLand(player, source, game, cards, "Island");
        searchLand(player, source, game, cards, "Swamp");
        searchLand(player, source, game, cards, "Mountain");

        player.revealCards("Shard Convergence", cards, game);
        player.shuffleLibrary(source, game);

        return true;
    }

    private void searchLand(Player player, Ability source, Game game, Cards cards, String subtype) {
        FilterLandCard filter = new FilterLandCard(subtype);
        filter.add(new SubtypePredicate(SubType.byDescription(subtype)));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (player.searchLibrary(target, game)) {
            Card card = player.getLibrary().remove(target.getFirstTarget(), game);
            if (card != null) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                cards.add(card);
            }
        }
    }

    @Override
    public ShardConvergenceEffect copy() {
        return new ShardConvergenceEffect(this);
    }
}