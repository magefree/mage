package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CrewAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.ClownRobotToken;
import mage.players.Player;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ClownCar extends CardImpl {

    public ClownCar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}");

        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Clown Car enters the battlefield, roll X six-sided dice. For each odd result, create a 1/1 white Clown Robot artifact creature token. For each even result, put a +1/+1 on Clown Car.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ClownCarEffect()));

        // Crew 2
        this.addAbility(new CrewAbility(2));
    }

    private ClownCar(final ClownCar card) {
        super(card);
    }

    @Override
    public ClownCar copy() {
        return new ClownCar(this);
    }
}

class ClownCarEffect extends OneShotEffect {

    ClownCarEffect() {
        super(Outcome.Benefit);
        staticText = "roll X six-sided dice. For each odd result, " +
                "create a 1/1 white Clown Robot artifact creature token. " +
                "For each even result, put a +1/+1 on {this}";
    }

    private ClownCarEffect(final ClownCarEffect effect) {
        super(effect);
    }

    @Override
    public ClownCarEffect copy() {
        return new ClownCarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        int xValue = ManacostVariableValue.ETB.calculate(game, source, this);
        if (player == null || xValue < 1) {
            return false;
        }
        Map<Integer, Integer> rollMap = player
                .rollDice(outcome, source, game, 6, xValue, 0)
                .stream()
                .map(x -> x % 2)
                .collect(Collectors.toMap(Function.identity(), x -> 1, Integer::sum));
        int odds = rollMap.getOrDefault(1, 0);
        if (odds > 0) {
            new ClownRobotToken().putOntoBattlefield(odds, game, source);
        }
        int evens = rollMap.getOrDefault(0, 0);
        if (evens > 0) {
            Optional.ofNullable(source.getSourcePermanentIfItStillExists(game))
                    .ifPresent(permanent -> permanent.addCounters(CounterType.P1P1.createInstance(evens), source, game));
        }
        return true;
    }
}
