
package mage.cards.r;

import java.util.*;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public final class RareBGone extends CardImpl {

    public RareBGone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Each player sacrifices all permanents that are rare or mythic rare, then each player reveals their hand and discards all cards that are rare or mythic rare.
        this.getSpellAbility().addEffect(new RareBGoneEffect());

    }

    public RareBGone(final RareBGone card) {
        super(card);
    }

    @Override
    public RareBGone copy() {
        return new RareBGone(this);
    }
}

class RareBGoneEffect extends OneShotEffect {

    private static final FilterPermanent filterPermanent = new FilterPermanent();
    private static final FilterCard filterCard = new FilterCard();

    static {
        filterPermanent.add(Predicates.or(
                new RarityPredicate(Rarity.RARE),
                new RarityPredicate(Rarity.MYTHIC)
        ));
        filterCard.add(Predicates.or(
                new RarityPredicate(Rarity.RARE),
                new RarityPredicate(Rarity.MYTHIC)
        ));
    }

    public RareBGoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player sacrifices all permanents that are rare or mythic rare, then each player reveals their hand and discards all cards that are rare or mythic rare";
    }

    public RareBGoneEffect(final RareBGoneEffect effect) {
        super(effect);
    }

    @Override
    public RareBGoneEffect copy() {
        return new RareBGoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterPermanent, playerId, game)) {
                        permanent.sacrifice(source.getSourceId(), game);
                    }
                    Cards hand = player.getHand();
                    player.revealCards("Rare-B-Gone", hand, game);
                    Set<Card> cards = hand.getCards(game);
                    for (Card card : cards) {
                        if (card != null && filterCard.match(card, game)) {
                            player.discard(card, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}

class RarityPredicate implements Predicate<Card> {

    private final Rarity rarity;

    public RarityPredicate(Rarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public boolean apply(Card input, Game game) {
        return input.getRarity().equals(rarity);
    }

    @Override
    public String toString() {
        return "Rarity(" + rarity + ')';
    }
}
