package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author L_J
 */
public final class RareBGone extends CardImpl {

    public RareBGone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{R}");

        // Each player sacrifices all permanents that are rare or mythic rare, then each player reveals their hand and discards all cards that are rare or mythic rare.
        this.getSpellAbility().addEffect(new RareBGoneEffect());
    }

    private RareBGone(final RareBGone card) {
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
        filterPermanent.add(RareBGonePredicate.instance);
        filterCard.add(RareBGonePredicate.instance);
    }

    RareBGoneEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player sacrifices all permanents that are rare or mythic rare, " +
                "then each player reveals their hand and discards all cards that are rare or mythic rare";
    }

    private RareBGoneEffect(final RareBGoneEffect effect) {
        super(effect);
    }

    @Override
    public RareBGoneEffect copy() {
        return new RareBGoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
            }
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filterPermanent, playerId, game)) {
                permanent.sacrifice(source, game);
            }
            Cards cards = player.getHand();
            player.revealCards(source, cards, game);
            player.discard(new CardsImpl(cards.getCards(filterCard, game)), false, source, game);
        }
        return true;
    }
}

enum RareBGonePredicate implements Predicate<Card> {
    instance;

    @Override
    public boolean apply(Card input, Game game) {
        return Objects.equals(input.getRarity(), Rarity.RARE) || Objects.equals(input.getRarity(), Rarity.MYTHIC);
    }
}
