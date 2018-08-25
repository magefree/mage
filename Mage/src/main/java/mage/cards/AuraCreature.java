package mage.cards;

import java.util.List;
import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;

/**
 * A class to represent creature cards that can be auras.
 * i.e., cards with bestow and licids
 * @author kevinwshin
 */
public abstract class AuraCreature extends CardImpl{
    
    public AuraCreature(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs) {
        super(ownerId, setInfo, cardTypes, costs);
    }

    //unattach all attached permanents after moving to exile
    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        boolean successfullyMoved = super.moveToExile(exileId, name, sourceId, game, appliedEffects);
        unattach();
        return successfullyMoved;
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        boolean successfullyMoved = super.moveToZone(toZone, sourceId, game, flag);
        unattach();
        return successfullyMoved;
    }
    
    private void unattach() {
        //GameImpl.java:1980
    }

}
