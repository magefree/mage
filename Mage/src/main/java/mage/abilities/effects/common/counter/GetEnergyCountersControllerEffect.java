package mage.abilities.effects.common.counter;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author emerald000
 */
public class GetEnergyCountersControllerEffect extends OneShotEffect {

    private final DynamicValue value;

    public GetEnergyCountersControllerEffect(int value) {
        this(StaticValue.get(value));
    }

    public GetEnergyCountersControllerEffect(DynamicValue value) {
        super(Outcome.Benefit);
        this.value = value;
        setText();
    }

    protected GetEnergyCountersControllerEffect(final GetEnergyCountersControllerEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.addCounters(CounterType.ENERGY.createInstance(value.calculate(game, source, this)), source.getControllerId(), source, game);
        }
        return false;
    }

    private void setText() {
        if (staticText == null || !staticText.isEmpty()) {
            return;
        }
        if (value.getMessage().equals("that many")) {
            staticText = "that many {E}";
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("you get ");
        int val;
        if (value instanceof StaticValue) {
            val = ((StaticValue) value).getValue();
        } else {
            val = 1;
        }
        if (val < 6) {
            for (int i = 0; i < val; i++) {
                sb.append("{E}");
            }
        } else {
            sb.append(CardUtil.numberToText(val));
            sb.append(" {E}");
        }
        sb.append(" <i>(");
        sb.append(CardUtil.getSimpleCountersText(val, "an", "energy"));
        sb.append(")</i>");
        if (value instanceof StaticValue) {
            sb.append('.');
        } else {
            sb.append(" for each ");
            sb.append(value.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public GetEnergyCountersControllerEffect copy() {
        return new GetEnergyCountersControllerEffect(this);
    }
}
