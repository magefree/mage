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
 *
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
        public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
            UUID playerThatChoseId = (UUID) game.getState().getValue(sourceId + ChooseSecretOpponentEffect.SECRET_OWNER);
            if (playerThatChoseId == null || !playerThatChoseId.equals(controllerId)) {
                return false;
            }
            UUID opponentId = (UUID) game.getState().getValue(sourceId + ChooseSecretOpponentEffect.SECRET_OPPONENT);
            return opponentId != null;
        }

        @Override
        public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana, Cost costToPay) {
            UUID playerThatChoseId = (UUID) game.getState().getValue(sourceId + ChooseSecretOpponentEffect.SECRET_OWNER);
            if (playerThatChoseId == null || !playerThatChoseId.equals(controllerId)) {
                return false;
            }
            UUID opponentId = (UUID) game.getState().getValue(sourceId + ChooseSecretOpponentEffect.SECRET_OPPONENT);
            if (opponentId != null) {
                game.getState().setValue(sourceId + ChooseSecretOpponentEffect.SECRET_OWNER, null); // because only once, the vale is set to null
                Player controller = game.getPlayer(controllerId);
                Player opponent = game.getPlayer(opponentId);
                MageObject sourceObject = game.getObject(sourceId);
                if (controller != null && opponent != null && sourceObject != null) {
                    if (sourceObject instanceof Permanent) {
                        ((Permanent) sourceObject).addInfo(ChooseSecretOpponentEffect.SECRET_OPPONENT, null, game);
                    }
                    game.informPlayers(sourceObject.getLogName() + ": " + controller.getLogName() + " reveals the secretly chosen opponent " + opponent.getLogName());
                }
                paid = true;
            }
            return paid;
        }

        @Override
        public RevealSecretOpponentCost copy() {
            return new RevealSecretOpponentCost(this);
        }


}
