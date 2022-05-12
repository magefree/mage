package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.command.emblems.EllywickTumblestrumEmblem;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EllywickTumblestrum extends CardImpl {

    public EllywickTumblestrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{2}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELLYWICK);
        this.setStartingLoyalty(4);

        // +1: Venture into the dungeon.
        this.addAbility(new LoyaltyAbility(new VentureIntoTheDungeonEffect(), 1));

        // −2: Look at the top six cards of your library. You may reveal a creature card from among them and put it into your hand. If it's legendary, you gain 3 life. Put the rest on the bottom of your library in a random order.
        this.addAbility(new LoyaltyAbility(new EllywickTumblestrumEffect(), -2));

        // −7: You get an emblem with "Creatures you control have trample and haste and get +2/+2 for each differently named dungeon you've completed."
        this.addAbility(new LoyaltyAbility(
                new GetEmblemEffect(new EllywickTumblestrumEmblem()), -7
        ), new CompletedDungeonWatcher());
    }

    private EllywickTumblestrum(final EllywickTumblestrum card) {
        super(card);
    }

    @Override
    public EllywickTumblestrum copy() {
        return new EllywickTumblestrum(this);
    }
}

class EllywickTumblestrumEffect extends OneShotEffect {

    EllywickTumblestrumEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top six cards of your library. You may reveal a creature card " +
                "from among them and put it into your hand. If it's legendary, you gain 3 life. " +
                "Put the rest on the bottom of your library in a random order";
    }

    private EllywickTumblestrumEffect(final EllywickTumblestrumEffect effect) {
        super(effect);
    }

    @Override
    public EllywickTumblestrumEffect copy() {
        return new EllywickTumblestrumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 6));
        TargetCardInLibrary target = new TargetCardInLibrary(0, 1, StaticFilters.FILTER_CARD_CREATURE);
        player.choose(outcome, cards, target, game);
        Card card = cards.get(target.getFirstTarget(), game);
        if (card != null) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
            cards.remove(card);
            if (card.isLegendary()) {
                player.gainLife(3, game, source);
            }
        }
        player.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
