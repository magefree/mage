
package mage.game.events;

import java.util.UUID;
import mage.abilities.costs.Cost;

/**
 *
 * @author LevelX2
 */
public class CostEvent extends GameEvent {

    protected Cost cost;

    public CostEvent(EventType type, UUID targetId, UUID sourceId, UUID playerId, Cost cost) {
        super(type, targetId, sourceId, playerId);
        this.cost = cost;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

}
