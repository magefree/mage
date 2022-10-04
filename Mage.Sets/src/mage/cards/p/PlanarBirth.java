
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
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

/**
 *
 * @author emerald000
 */
public final class PlanarBirth extends CardImpl {

    public PlanarBirth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{W}");

        // Return all basic land cards from all graveyards to the battlefield tapped under their owners' control.
        this.getSpellAbility().addEffect(new PlanarBirthEffect());
    }

    private PlanarBirth(final PlanarBirth card) {
        super(card);
    }

    @Override
    public PlanarBirth copy() {
        return new PlanarBirth(this);
    }
}

class PlanarBirthEffect extends OneShotEffect {

    PlanarBirthEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = "Return all basic land cards from all graveyards to the battlefield tapped under their owners' control";
    }

    PlanarBirthEffect(final PlanarBirthEffect effect) {
        super(effect);
    }

    @Override
    public PlanarBirthEffect copy() {
        return new PlanarBirthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards toBattlefield = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    toBattlefield.addAllCards(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_BASIC_LAND, controller.getId(), source, game));
                }
            }
            controller.moveCards(toBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, true, false, true, null);
            return true;
        }
        return false;
    }
}
