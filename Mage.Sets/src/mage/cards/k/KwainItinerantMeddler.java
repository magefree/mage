package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KwainItinerantMeddler extends CardImpl {

    public KwainItinerantMeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.RABBIT);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Each player may draw a card, then each player who drew a card this way gains 1 life.
        this.addAbility(new SimpleActivatedAbility(new KwainItinerantMeddlerEffect(), new TapSourceCost()));
    }

    private KwainItinerantMeddler(final KwainItinerantMeddler card) {
        super(card);
    }

    @Override
    public KwainItinerantMeddler copy() {
        return new KwainItinerantMeddler(this);
    }
}

class KwainItinerantMeddlerEffect extends OneShotEffect {

    KwainItinerantMeddlerEffect() {
        super(Outcome.Benefit);
        staticText = "each player may draw a card, then each player who drew a card this way gains 1 life";
    }

    private KwainItinerantMeddlerEffect(final KwainItinerantMeddlerEffect effect) {
        super(effect);
    }

    @Override
    public KwainItinerantMeddlerEffect copy() {
        return new KwainItinerantMeddlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<Player> players = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null
                    && player.chooseUse(outcome, "Draw a card?", source, game)
                    && player.drawCards(1, source, game) > 0) {
                players.add(player);
            }
        }
        for (Player player : players) {
            player.gainLife(1, game, source);
        }
        return !players.isEmpty();
    }
}
