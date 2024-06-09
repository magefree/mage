package mage.abilities.costs;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Targets;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.UUID;

public interface Cost extends Serializable, Copyable<Cost> {

    UUID getId();

    String getText();

    Cost setText(String text);

    /**
     * Check is it possible to pay
     * For mana it checks only single color and amount available, not total mana cost
     */
    boolean canPay(Ability ability, Ability source, UUID controllerId, Game game);

    boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana);

    boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay);

    boolean isPaid();

    void clearPaid();

    void setPaid();

    /**
     * Warning, can return copied list in composite costs, so it will be un-changeable
     * Use targets list modification only in CostAdjuster for single card/effect
     */
    Targets getTargets();

    Cost copy();

}
