package mage.abilities.costs.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.ChooseSecretOpponentEffect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author LevelX2
 * @author credman0
 */
public class RevealSecretOpponentCost extends CostImpl {

    public RevealSecretOpponentCost() {
        this.text = "Reveal the player you chose";
    }

    public RevealSecretOpponentCost(final RevealSecretOpponentCost cost) {
        super(cost);
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return controllerId != null
                && controllerId.equals(ChooseSecretOpponentEffect.getSecretOwner(source, game))
                && ChooseSecretOpponentEffect.getChosenPlayer(source, game) != null;
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (controllerId == null || !controllerId.equals(ChooseSecretOpponentEffect.getSecretOwner(source, game))) {
            return false;
        }
        UUID opponentId = ChooseSecretOpponentEffect.getChosenPlayer(source, game);
        if (opponentId == null) {
            return paid;
        }
        ChooseSecretOpponentEffect.setSecretOwner(null, source, game); // because only once, the value is set to null
        Player controller = game.getPlayer(controllerId);
        Player opponent = game.getPlayer(opponentId);
        MageObject sourceObject = game.getObject(source);
        if (controller != null && opponent != null && sourceObject != null) {
            if (sourceObject instanceof Permanent) {
                ((Permanent) sourceObject).addInfo(ChooseSecretOpponentEffect.SECRET_OPPONENT, null, game);
            }
            game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " reveals the secretly chosen opponent " + opponent.getLogName());
        }
        paid = true;
        return paid;
    }

    @Override
    public RevealSecretOpponentCost copy() {
        return new RevealSecretOpponentCost(this);
    }
}
