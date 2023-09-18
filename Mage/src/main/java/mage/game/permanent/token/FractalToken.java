package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FractalToken extends TokenImpl {

    public FractalToken() {
        super("Fractal Token", "0/0 green and blue Fractal creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FRACTAL);
        color.setGreen(true);
        color.setBlue(true);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }

    private FractalToken(final FractalToken token) {
        super(token);
    }

    public FractalToken copy() {
        return new FractalToken(this);
    }

    public static QuandrixTokenEffect getEffect(DynamicValue xValue, String text) {
        return new QuandrixTokenEffect(xValue, text);
    }

    private static final class QuandrixTokenEffect extends OneShotEffect {

        private final DynamicValue xValue;

        private QuandrixTokenEffect(DynamicValue xValue, String text) {
            super(Outcome.Benefit);
            this.xValue = xValue;
            this.staticText = "create a 0/0 green and blue Fractal creature token. " + text;
        }

        private QuandrixTokenEffect(final QuandrixTokenEffect effect) {
            super(effect);
            this.xValue = effect.xValue;
        }

        @Override
        public QuandrixTokenEffect copy() {
            return new QuandrixTokenEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Token token = new FractalToken();
            token.putOntoBattlefield(1, game, source, source.getControllerId());
            int value = xValue.calculate(game, source, this);
            if (value < 1) {
                return true;
            }
            for (UUID tokenId : token.getLastAddedTokenIds()) {
                Permanent permanent = game.getPermanent(tokenId);
                if (permanent == null) {
                    continue;
                }
                permanent.addCounters(CounterType.P1P1.createInstance(value), source.getControllerId(), source, game);
            }
            return true;
        }
    }

}
