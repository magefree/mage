// Save this as: mage.cards.RoomCardHalfImpl.java
package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.UUID;

public class RoomCardHalfImpl extends SplitCardHalfImpl {

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
        // Get the ID of the main RoomCard, which will become the permanent
        UUID parentCardId = this.getParentCard().getId();

        if (abilityType == SpellAbilityType.SPLIT_LEFT) {
            game.getState().setValue(parentCardId + "_ROOM_LAST_CAST_HALF", SpellAbilityType.SPLIT_LEFT);
            System.out.println("  Stored SPLIT_LEFT for Parent ID: " + parentCardId);
        } else if (abilityType == SpellAbilityType.SPLIT_RIGHT) {
            game.getState().setValue(parentCardId + "_ROOM_LAST_CAST_HALF", SpellAbilityType.SPLIT_RIGHT);
            System.out.println("  Stored SPLIT_RIGHT for Parent ID: " + parentCardId);
        } else {
            // This case should ideally not happen for a half being cast as SPLIT_LEFT/RIGHT
            game.getState().setValue(parentCardId + "_ROOM_LAST_CAST_HALF", null);
            System.out.println("  Stored null (unknown SpellAbilityType) for Parent ID: " + parentCardId);
        }

        return super.cast(game, fromZone, ability, controllerId);
    }
}