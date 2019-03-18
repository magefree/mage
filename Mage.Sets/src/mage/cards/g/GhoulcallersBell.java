
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class GhoulcallersBell extends CardImpl {

    public GhoulcallersBell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {T}: Each player puts the top card of their library into their graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GhoulcallersBellEffect(), new TapSourceCost()));
    }

    public GhoulcallersBell(final GhoulcallersBell card) {
        super(card);
    }

    @Override
    public GhoulcallersBell copy() {
        return new GhoulcallersBell(this);
    }
}

class GhoulcallersBellEffect extends OneShotEffect {

    public GhoulcallersBellEffect() {
        super(Outcome.Discard);
        this.staticText = "Each player puts the top card of their library into their graveyard";
    }

    public GhoulcallersBellEffect(final GhoulcallersBellEffect effect) {
        super(effect);
    }

    @Override
    public GhoulcallersBellEffect copy() {
        return new GhoulcallersBellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                Card card = player.getLibrary().getFromTop(game);
                if (card != null) {
                    player.moveCards(card, Zone.GRAVEYARD, source, game);
                }
            }
        }
        return true;
    }
}
