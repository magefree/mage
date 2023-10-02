
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
 * @author ChesseTheWasp
 */
public class GetResourceCountersControllerEffect extends OneShotEffect {

    private final DynamicValue value;

    public GetResourceCountersControllerEffect(int value) {
        this(StaticValue.get(value));
    }

    public GetResourceCountersControllerEffect(DynamicValue value) {
        super(Outcome.Benefit);
        this.value = value;
        setText();
    }

    protected GetResourceCountersControllerEffect(final GetResourceCountersControllerEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.addCounters(CounterType.RESOURCE.createInstance(value.calculate(game, source, this)), source.getControllerId(), source, game);
        }
        return false;
    }

    private void setText() {
        if (!staticText.isEmpty()) {
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("you get ");
        int val = 1;
        if (value instanceof StaticValue) {
            val = ((StaticValue) value).getValue();
        }
        for (int i = 0; i < val; i++) {
            sb.append("{R}");
        }
        sb.append(" <i>(");
        sb.append(CardUtil.numberToText(val, "an"));
        sb.append(" resource counter");
        sb.append(val > 1 ? "s" : "");
        sb.append(")</i>");
        if ((value instanceof StaticValue)) {
            sb.append('.');
        } else {
            sb.append(" for each ");
            sb.append(value.getMessage());
        }
        staticText = sb.toString();
    }

    @Override
    public GetResourceCountersControllerEffect copy() {
        return new GetResourceCountersControllerEffect(this);
    }
}
