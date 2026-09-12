package mage.cards.w;

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
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.util.CardUtil;

/**
 *
 * @author muz
 */
public final class WakandaForever extends CardImpl {

    public WakandaForever(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Reveal the top six cards of your library. You may put a permanent card from among them onto the battlefield with an indestructible counter on it. You may put a permanent card from among them into your hand. Put the rest into your graveyard.
        this.getSpellAbility().addEffect(new WakandaForeverEffect());
    }

    private WakandaForever(final WakandaForever card) {
        super(card);
    }

    @Override
    public WakandaForever copy() {
        return new WakandaForever(this);
    }
}

class WakandaForeverEffect extends OneShotEffect {

    WakandaForeverEffect() {
        super(Outcome.Benefit);
        staticText = "reveal the top six cards of your library. " +
            "You may put a permanent card from among them onto the battlefield with an indestructible counter on it. " +
            "You may put a permanent card from among them into your hand. " +
            "Put the rest into your graveyard.";
    }

    private WakandaForeverEffect(final WakandaForeverEffect effect) {
        super(effect);
    }

    @Override
    public WakandaForeverEffect copy() {
        return new WakandaForeverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        // Reveal the top six cards of your library
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        player.revealCards(source, cards, game);

        // You may put a permanent card from among them onto the battlefield with an indestructible counter on it
        TargetCard target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_PERMANENT);
        player.choose(outcome, cards, target, source, game);
        Card permanentCard = cards.get(target.getFirstTarget(), game);
        if (permanentCard != null) {
            player.moveCards(
                permanentCard, Zone.BATTLEFIELD, source, game,
                false, false, false, null
            );
            Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(permanentCard, game);
            if (permanent != null) {
                permanent.addCounters(CounterType.INDESTRUCTIBLE.createInstance(), source, game);
            }
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);

        // You may put a permanent card from among them into your hand
        target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_PERMANENT);
        player.choose(outcome, cards, target, source, game);
        Card handCard = cards.get(target.getFirstTarget(), game);
        if (handCard != null) {
            player.moveCardToHandWithInfo(handCard, source, game, true);
        }
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.LIBRARY);

        // Put the rest into your graveyard
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
