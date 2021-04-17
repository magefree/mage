package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeachingsOfTheArchaics extends CardImpl {

    public TeachingsOfTheArchaics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{U}");

        this.subtype.add(SubType.LESSON);

        // If an opponent has more cards in hand than you, draw two cards. Draw three cards instead if an opponent has at least four more cards in hand than you.
        this.getSpellAbility().addEffect(new TeachingsOfTheArchaicsEffect());
    }

    private TeachingsOfTheArchaics(final TeachingsOfTheArchaics card) {
        super(card);
    }

    @Override
    public TeachingsOfTheArchaics copy() {
        return new TeachingsOfTheArchaics(this);
    }
}

class TeachingsOfTheArchaicsEffect extends OneShotEffect {

    TeachingsOfTheArchaicsEffect() {
        super(Outcome.Benefit);
        staticText = "if an opponent has more cards in hand than you, draw two cards. " +
                "Draw three cards instead if an opponent has at least four more cards in hand than you";
    }

    private TeachingsOfTheArchaicsEffect(final TeachingsOfTheArchaicsEffect effect) {
        super(effect);
    }

    @Override
    public TeachingsOfTheArchaicsEffect copy() {
        return new TeachingsOfTheArchaicsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int diff = game
                .getOpponents(source.getControllerId())
                .stream()
                .map(game::getPlayer)
                .filter(Objects::nonNull)
                .map(Player::getHand)
                .mapToInt(Set::size)
                .max()
                .orElse(0);
        diff -= player.getHand().size();
        if (diff <= 0) {
            return false;
        }
        return player.drawCards(diff >= 4 ? 3 : 2, source, game) > 0;
    }
}
