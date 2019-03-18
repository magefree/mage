
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Backfir3
 */
public final class Whetstone extends CardImpl {

    public Whetstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        //{3}: Each player puts the top two cards of their library into their graveyard.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new WhetstoneEffect(), new ManaCostsImpl("{3}"));
        this.addAbility(ability);
    }

    public Whetstone(final Whetstone card) {
        super(card);
    }

    @Override
    public Whetstone copy() {
        return new Whetstone(this);
    }
}

class WhetstoneEffect extends OneShotEffect {

    WhetstoneEffect() {
        super(Outcome.Detriment);
        staticText = "Each player puts the top two cards of their library into their graveyard";
    }

    WhetstoneEffect(final WhetstoneEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.moveCards(player.getLibrary().getTopCards(game, 2), Zone.GRAVEYARD, source, game);
            }
        }
        return true;
    }

    @Override
    public WhetstoneEffect copy() {
        return new WhetstoneEffect(this);
    }
}
