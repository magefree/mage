package mage.abilities.dynamicvalue.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class PermanentsYouOwnThatOpponentsControlCount implements DynamicValue {
    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        if (sourceAbility != null) {
            Player controller = game.getPlayer(sourceAbility.getControllerId());

            if (controller != null) {
                UUID controllerId = controller.getId();
                Set<UUID> opponentIds = game.getOpponents(controllerId);
                int count = 0;

                for (UUID opponentId : opponentIds){
                    List<Permanent> opponentPermanents = game.getBattlefield().getAllActivePermanents(opponentId);

                    for (Permanent opponentPermanent : opponentPermanents){
                        if (opponentPermanent.getOwnerId().equals(controllerId)){
                            count++;
                        }
                    }
                }

                return count;
            }
        }
        return 0;
    }

    @Override
    public DynamicValue copy() {
        return new PermanentsYouOwnThatOpponentsControlCount();
    }

    @Override
    public String getMessage() {
        return "number of permanents you own that your opponents control";
    }

    @Override
    public String toString() {
        return "1";
    }
}

