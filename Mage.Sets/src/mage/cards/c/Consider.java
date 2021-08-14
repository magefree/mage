package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Consider extends CardImpl {

    public Consider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // Look at the top card of your library. You may put that card into your graveyard.
        // Draw a card.
        this.getSpellAbility().addEffect(new ConsiderEffect());
    }

    private Consider(final Consider card) {
        super(card);
    }

    @Override
    public Consider copy() {
        return new Consider(this);
    }
}

class ConsiderEffect extends OneShotEffect {

    ConsiderEffect() {
        super(Outcome.Benefit);
        staticText = "Look at the top card of your library. " +
                "You may put that card into your graveyard.<br>Draw a card";
    }

    private ConsiderEffect(final ConsiderEffect effect) {
        super(effect);
    }

    @Override
    public ConsiderEffect copy() {
        return new ConsiderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        player.lookAtCards("Top card of your library", card, game);
        if (player.chooseUse(Outcome.AIDontUseIt, "Put the top card of your library into your graveyard?", source, game)) {
            player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        player.drawCards(1, source, game);
        return true;
    }
}
