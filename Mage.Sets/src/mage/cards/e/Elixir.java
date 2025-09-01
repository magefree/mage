package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Elixir extends CardImpl {

    public Elixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // This artifact enters tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {5}, {T}, Exile this artifact: Shuffle all nonland cards from your graveyard into your library. You gain life equal to the number of cards shuffled into your library this way.
        Ability ability = new SimpleActivatedAbility(new ElixirEffect(), new GenericManaCost(5));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private Elixir(final Elixir card) {
        super(card);
    }

    @Override
    public Elixir copy() {
        return new Elixir(this);
    }
}

class ElixirEffect extends OneShotEffect {

    ElixirEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle all nonland cards from your graveyard into your library. " +
                "You gain life equal to the number of cards shuffled into your library this way";
    }

    private ElixirEffect(final ElixirEffect effect) {
        super(effect);
    }

    @Override
    public ElixirEffect copy() {
        return new ElixirEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_NON_LAND, game));
        player.shuffleCardsToLibrary(cards, game, source);
        cards.retainZone(Zone.LIBRARY, game);
        player.gainLife(cards.size(), game, source);
        return true;
    }
}
