package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

/**
 * @author oscscull
 */
public class RoomCardHalfImpl extends SplitCardHalfImpl implements RoomCardHalf {

    public RoomCardHalfImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs,
            RoomCard splitCardParent, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, cardTypes, costs, splitCardParent, spellAbilityType);
    }

    protected RoomCardHalfImpl(final RoomCardHalfImpl card) {
        super(card);
    }

    @Override
    public RoomCardHalfImpl copy() {
        return new RoomCardHalfImpl(this);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        SpellAbilityType abilityType = ability.getSpellAbilityType();
        UUID parentCardId = this.getParentCard().getId();

        if (abilityType == SpellAbilityType.SPLIT_LEFT) {
            game.getState().setValue(parentCardId + "_ROOM_LAST_CAST_HALF", SpellAbilityType.SPLIT_LEFT);
        } else if (abilityType == SpellAbilityType.SPLIT_RIGHT) {
            game.getState().setValue(parentCardId + "_ROOM_LAST_CAST_HALF", SpellAbilityType.SPLIT_RIGHT);
        } else {
            game.getState().setValue(parentCardId + "_ROOM_LAST_CAST_HALF", null);
        }

        return super.cast(game, fromZone, ability, controllerId);
    }
    
    @Override
    public void setZone(Zone zone, Game game) {
        if (zone == Zone.BATTLEFIELD) {
            game.setZone(splitCardParent.getId(), zone);
            game.setZone(splitCardParent.getLeftHalfCard().getId(), Zone.OUTSIDE);
            game.setZone(splitCardParent.getRightHalfCard().getId(), Zone.OUTSIDE);
            return;
        }
        game.setZone(splitCardParent.getId(), zone);
        game.setZone(splitCardParent.getLeftHalfCard().getId(), zone);
        game.setZone(splitCardParent.getRightHalfCard().getId(), zone);
    }
}