package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WindingWay extends CardImpl {

    public WindingWay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{G}");

        // Choose creature or land. Reveal the top four cards of your library. Put all cards of the chosen type revealed this way into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new WindingWayEffect());
    }

    private WindingWay(final WindingWay card) {
        super(card);
    }

    @Override
    public WindingWay copy() {
        return new WindingWay(this);
    }
}

class WindingWayEffect extends OneShotEffect {

    WindingWayEffect() {
        super(Outcome.Benefit);
        staticText = "Choose creature or land. Reveal the top four cards of your library. " +
                "Put all cards of the chosen type revealed this way into your hand and the rest into your graveyard.";
    }

    private WindingWayEffect(final WindingWayEffect effect) {
        super(effect);
    }

    @Override
    public WindingWayEffect copy() {
        return new WindingWayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean isCreature = player.chooseUse(
                outcome, "Creature or Land?", "",
                "Creature", "Land", source, game
        );
        FilterCard filter = (isCreature ? StaticFilters.FILTER_CARD_CREATURE_A : StaticFilters.FILTER_CARD_LAND_A);
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 4));
        player.revealCards(source, cards, game);
        Cards cardsToKeep = new CardsImpl(cards.getCards(filter, game));
        cards.removeAll(cardsToKeep);
        player.moveCards(cardsToKeep, Zone.HAND, source, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}