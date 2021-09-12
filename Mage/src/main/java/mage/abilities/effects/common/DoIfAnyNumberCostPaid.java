package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

public class DoIfAnyNumberCostPaid extends OneShotEffect {

    private final Effect executingEffect;
    private final Cost cost;

    public DoIfAnyNumberCostPaid(OneShotEffect effect, Cost cost) {
        super(Outcome.Benefit);
        this.executingEffect = effect;
        this.cost = cost;
    }

    private DoIfAnyNumberCostPaid(final DoIfAnyNumberCostPaid effect) {
        super(effect);
        this.executingEffect = effect.executingEffect.copy();
        this.cost = effect.cost.copy();
    }

    @Override
    public DoIfAnyNumberCostPaid copy() {
        return new DoIfAnyNumberCostPaid(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        String costText = cost.getText();
        int timesPaid = 0;
        while (controller.canRespond()) {
            cost.clearPaid();
            if (cost.canPay(source, source, source.getControllerId(), game)
                    && controller.chooseUse(
                    outcome, "Pay " + costText + "? You have paid this cost " +
                            timesPaid + " time" + (timesPaid != 1 ? "s" : ""), source, game
            ) && cost.pay(source, game, source, source.getControllerId(), false)) {
                timesPaid++;
                continue;
            }
            break;
        }
        if (timesPaid > 0) {
            executingEffect.setValue("timesPaid", timesPaid);
            return executingEffect.apply(game, source);
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "you may pay " + cost.getText() + " any number of times. When you pay this cost one or more times, "
                + executingEffect.getText(mode);
    }
}
