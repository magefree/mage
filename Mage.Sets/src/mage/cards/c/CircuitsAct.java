package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.ClownRobotToken;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CircuitsAct extends CardImpl {

    public CircuitsAct(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{R}");

        // Roll three six-sided dice. For each different result, create a 1/1 white Clown Robot artifact creature token.
        this.getSpellAbility().addEffect(new CircuitsActEffect());
    }

    private CircuitsAct(final CircuitsAct card) {
        super(card);
    }

    @Override
    public CircuitsAct copy() {
        return new CircuitsAct(this);
    }
}

class CircuitsActEffect extends OneShotEffect {

    CircuitsActEffect() {
        super(Outcome.Benefit);
        staticText = "roll three six-sided dice. For each different result, " +
                "create a 1/1 white Clown Robot artifact creature token";
    }

    private CircuitsActEffect(final CircuitsActEffect effect) {
        super(effect);
    }

    @Override
    public CircuitsActEffect copy() {
        return new CircuitsActEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = player
                .rollDice(outcome, source, game, 6, 3, 0)
                .stream()
                .distinct()
                .mapToInt(x -> 1)
                .sum();
        if (count > 0) {
            new ClownRobotToken().putOntoBattlefield(count, game, source);
        }
        return true;
    }
}
