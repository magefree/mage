package mage.abilities.costs;

import java.io.Serializable;
import java.util.UUID;

import mage.abilities.Ability;
import mage.game.Game;
import mage.target.Targets;

public interface Cost extends Serializable {

    UUID getId();

    String getText();

    void setText(String text);

    /**
     * Check is it possible to pay
     * For mana it checks only single color and amount available, not total mana cost
     */
    boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game);

    boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana);

    boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay);

    boolean isPaid();

    void clearPaid();

    void setPaid();

    Targets getTargets();

    Cost copy();

}
