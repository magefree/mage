package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TashasHideousLaughter extends CardImpl {

    public TashasHideousLaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}{U}");

        // Each opponent exiles cards from the top of their library until that player has exiled cards with total mana value 20 or more.
        this.getSpellAbility().addEffect(new TashasHideousLaughterEffect());
    }

    private TashasHideousLaughter(final TashasHideousLaughter card) {
        super(card);
    }

    @Override
    public TashasHideousLaughter copy() {
        return new TashasHideousLaughter(this);
    }
}

class TashasHideousLaughterEffect extends OneShotEffect {

    TashasHideousLaughterEffect() {
        super(Outcome.Benefit);
        staticText = "each opponent exiles cards from the top of their library " +
                "until that player has exiled cards with total mana value 20 or more";
    }

    private TashasHideousLaughterEffect(final TashasHideousLaughterEffect effect) {
        super(effect);
    }

    @Override
    public TashasHideousLaughterEffect copy() {
        return new TashasHideousLaughterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }
            int totalMV = 0;
            for (Card card : player.getLibrary().getCards(game)) {
                cards.add(card);
                totalMV += card.getManaValue();
                if (totalMV >= 20) {
                    break;
                }
            }
        }
        return controller.moveCards(cards, Zone.EXILED, source, game);
    }
}
