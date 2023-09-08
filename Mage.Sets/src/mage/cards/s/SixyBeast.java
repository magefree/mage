
package mage.cards.s;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 *
 * @author L_J
 */
public final class SixyBeast extends CardImpl {

    public SixyBeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // As Six-y Beast enters the battlefield, you secretly put six or fewer +1/+1 counters on it, then an opponent guesses the number of counters. If that player guesses right, sacrifice Six-y Beast after it enters the battlefield.
        this.addAbility(new AsEntersBattlefieldAbility(new SixyBeastEffect()));

    }

    private SixyBeast(final SixyBeast card) {
        super(card);
    }

    @Override
    public SixyBeast copy() {
        return new SixyBeast(this);
    }
}

class SixyBeastEffect extends OneShotEffect {

    public SixyBeastEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "you secretly put six or fewer +1/+1 counters on it, then an opponent guesses the number of counters. If that player guesses right, sacrifice {this} after it enters the battlefield";
    }

    private SixyBeastEffect(final SixyBeastEffect effect) {
        super(effect);
    }

    @Override
    public SixyBeastEffect copy() {
        return new SixyBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            int counterAmount = controller.getAmount(0, 6, "Secretly put up to six counters on " + permanent.getName(), game);
            permanent.addCounters(CounterType.P1P1.createInstance(counterAmount), source.getControllerId(), source, game);
            Player opponent = null;
            Set<UUID> opponents = game.getOpponents(source.getControllerId());
            if (!opponents.isEmpty()) {
                if (opponents.size() > 1) {
                    Target targetOpponent = new TargetOpponent(true);
                    if (controller.chooseTarget(Outcome.Neutral, targetOpponent, source, game)) {
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    }
                } else {
                    opponent = game.getPlayer(opponents.iterator().next());
                }
            }
            if (opponent != null) {
                int guessedAmount = opponent.getAmount(0, 6, "Guess the number of counters on " + permanent.getName(), game);
                game.informPlayers(opponent.getLogName() + " guessed " + guessedAmount + " as the number of counters on " + permanent.getLogName());
                if (counterAmount == guessedAmount) {
                    permanent.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
