package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildMagicSurge extends CardImpl {

    public WildMagicSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{R}");

        // Destroy target permanent an opponent controls. Its controller reveals cards from the top of their library until they reveal a permanent card that shares a card type with that permanent. They put that card onto the battlefield and the rest on the bottom of their library in a random order.
        this.getSpellAbility().addEffect(new WildMagicSurgeEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT));
    }

    private WildMagicSurge(final WildMagicSurge card) {
        super(card);
    }

    @Override
    public WildMagicSurge copy() {
        return new WildMagicSurge(this);
    }
}

class WildMagicSurgeEffect extends OneShotEffect {

    WildMagicSurgeEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target permanent an opponent controls. Its controller reveals cards " +
                "from the top of their library until they reveal a permanent card that shares a " +
                "card type with that permanent. They put that card onto the battlefield " +
                "and the rest on the bottom of their library in a random order";
    }

    private WildMagicSurgeEffect(final WildMagicSurgeEffect effect) {
        super(effect);
    }

    @Override
    public WildMagicSurgeEffect copy() {
        return new WildMagicSurgeEffect(this);
    }

    private static final Card loopCards(List<CardType> cardTypes, Player player, Cards cards, Ability source, Game game) {
        for (Card card : player.getLibrary().getCards(game)) {
            cards.add(card);
            if (card.isPermanent(game)
                    && card
                    .getCardType(game)
                    .stream()
                    .anyMatch(cardTypes::contains)) {
                return card;
            }
        }
        return null;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game);
        Player player = game.getPlayer(permanent.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        Card card = loopCards(permanent.getCardType(game), player, cards, source, game);
        player.revealCards(source, cards, game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        cards.retainZone(Zone.LIBRARY, game);
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
