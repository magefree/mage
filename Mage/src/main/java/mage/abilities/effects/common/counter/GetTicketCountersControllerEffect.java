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
 * @author muz
 */
public class GetTicketCountersControllerEffect extends OneShotEffect {

    private final DynamicValue value;

    public GetTicketCountersControllerEffect(int value) {
        this(StaticValue.get(value));
    }

    public GetTicketCountersControllerEffect(DynamicValue value) {
        super(Outcome.Benefit);
        this.value = value;
        setText();
    }

    protected GetTicketCountersControllerEffect(final GetTicketCountersControllerEffect effect) {
        super(effect);
        this.value = effect.value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.addCounters(CounterType.TICKET.createInstance(value.calculate(game, source, this)), source.getControllerId(), source, game);
        }
        return false;
    }

    private void setText() {
        if (staticText != null && !staticText.isEmpty()) {
            return;
        }
        if (value.toString().equals("that many")) {
            staticText = "you get that many {TK} <i>(ticket counters)</i>.";
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
                sb.append("{TK}");
            }
        } else {
            sb.append(CardUtil.numberToText(val));
            sb.append(" {TK}");
        }
        sb.append(" <i>(");
        sb.append(CardUtil.getSimpleCountersText(val, "a", "ticket"));
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
    public GetTicketCountersControllerEffect copy() {
        return new GetTicketCountersControllerEffect(this);
    }
}
