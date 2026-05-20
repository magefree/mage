package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

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

    public static Effect getEffect(DynamicValue xValue, String text) {
        return getEffect(1, xValue, text);
    }

    public static Effect getEffect(int amount, DynamicValue xValue, String text) {
        return new FractalTokenEffect(amount, xValue, text);
    }

    private static final class FractalTokenEffect extends OneShotEffect {

        private final int amount;
        private final DynamicValue xValue;

        private FractalTokenEffect(int amount, DynamicValue xValue, String text) {
            super(Outcome.Benefit);
            this.amount = amount;
            this.xValue = xValue;
            this.staticText = "create " + CardUtil.numberToText(amount, "a") +
                    " 0/0 green and blue Fractal creature token" + (amount > 1 ? "s" : "") + text;
        }

        private FractalTokenEffect(final FractalTokenEffect effect) {
            super(effect);
            this.amount = effect.amount;
            this.xValue = effect.xValue;
        }

        @Override
        public FractalTokenEffect copy() {
            return new FractalTokenEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Token token = new FractalToken();
            token.putOntoBattlefield(amount, game, source, source.getControllerId());
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
