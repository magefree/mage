package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IntoTheNight extends CardImpl {

    public IntoTheNight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // It becomes night. Discard any number of cards, then draw that many cards plus one.
        this.getSpellAbility().addEffect(new IntoTheNightEffect());
    }

    private IntoTheNight(final IntoTheNight card) {
        super(card);
    }

    @Override
    public IntoTheNight copy() {
        return new IntoTheNight(this);
    }
}

class IntoTheNightEffect extends OneShotEffect {

    IntoTheNightEffect() {
        super(Outcome.Benefit);
        staticText = "it becomes night. Discard any number of cards, then draw that many cards plus one";
    }

    private IntoTheNightEffect(final IntoTheNightEffect effect) {
        super(effect);
    }

    @Override
    public IntoTheNightEffect copy() {
        return new IntoTheNightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.setDaytime(false);
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(player.discard(
                0, Integer.MAX_VALUE, false, source, game
        ).size() + 1, source, game);
        return true;
    }
}
