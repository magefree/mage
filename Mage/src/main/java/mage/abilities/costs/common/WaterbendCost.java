package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.game.Game;

import java.util.UUID;

/**
 * TODO: Implement properly
 *
 * @author TheElk801
 */
public class WaterbendCost extends ManaCostsImpl {

    public WaterbendCost(int amount) {
        this("{" + amount + '}');
    }

    public WaterbendCost(String mana) {
        super("");
        this.text = "waterbend " + mana;
    }

    private WaterbendCost(final WaterbendCost cost) {
        super(cost);
    }

    @Override
    public WaterbendCost copy() {
        return new WaterbendCost(this);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return false;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        return false;
    }
}
