
package mage.abilities.condition.common;

import java.util.Objects;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.game.Game;


/**
 * As long as the card has remained in the same zone 
 * since this condition was created, the condition is true.
 *
 * @author spjspj
 */
public class SourceHasRemainedInSameZoneCondition implements Condition {

    UUID idToCheck;
    int timesChangedZones;
    
    public SourceHasRemainedInSameZoneCondition(UUID idToCheck) {
        this.idToCheck = idToCheck;
        this.timesChangedZones = -1;
    }
    
    public SourceHasRemainedInSameZoneCondition(UUID idToCheck, Game game) {
        this.idToCheck = idToCheck;
        this.timesChangedZones = -1;
        if (this.idToCheck != null && game != null && game.getCard(this.idToCheck) != null) {
            this.timesChangedZones = game.getState().getZoneChangeCounter(this.idToCheck);
        }
    }

    public SourceHasRemainedInSameZoneCondition getInstance(UUID cardId) {
        return new SourceHasRemainedInSameZoneCondition(cardId);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (idToCheck != null && game.getCard(idToCheck) != null) {
            if (timesChangedZones == -1) {
                timesChangedZones = game.getState().getZoneChangeCounter(idToCheck);
            }
            if (timesChangedZones == game.getState().getZoneChangeCounter(idToCheck)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public String toString() {
        return "if {this} has remained in the same zone";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SourceHasRemainedInSameZoneCondition that = (SourceHasRemainedInSameZoneCondition) obj;
        return this.timesChangedZones == that.timesChangedZones
                && Objects.equals(this.idToCheck, that.idToCheck);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idToCheck, timesChangedZones);
    }
}
