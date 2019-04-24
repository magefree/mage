

package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */

public class PayVariableLifeCost extends VariableCostImpl {

    public PayVariableLifeCost() {
        this(false);
    }

    public PayVariableLifeCost(boolean additionalCostText) {
        super("life to pay");
        this.text = new StringBuilder(additionalCostText ? "as an additional cost to cast this spell, pay ":"Pay ")
                .append(xText).append(' ').append("life").toString();
    }

    public PayVariableLifeCost(final PayVariableLifeCost cost) {
        super(cost);
    }

    @Override
    public PayVariableLifeCost copy() {
        return new PayVariableLifeCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new PayLifeCost(xValue);
    }

    @Override
    public int getMaxValue(Ability source, Game game) {
        int maxValue = 0;
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            maxValue = controller.getLife();
        }
        return maxValue;
    }

}
