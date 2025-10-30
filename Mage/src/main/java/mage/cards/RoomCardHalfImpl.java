package mage.cards;

import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
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
        this.addSubType(SubType.ROOM);
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
        RoomCard parentCard = (RoomCard) this.getParentCard();

        if (parentCard != null) {
            if (abilityType == SpellAbilityType.SPLIT_LEFT) {
                parentCard.setLastCastHalf(SpellAbilityType.SPLIT_LEFT);
            } else if (abilityType == SpellAbilityType.SPLIT_RIGHT) {
                parentCard.setLastCastHalf(SpellAbilityType.SPLIT_RIGHT);
            } else {
                parentCard.setLastCastHalf(null);
            }
        }

        return super.cast(game, fromZone, ability, controllerId);
    }

    /**
     * A room half is used for the spell half on the stack, similar to a normal split card.
     * On the stack, it has only one name, mana cost, etc.
     * However, in the hand and on the battlefield, it is the full card, which is the parent of the half.
     * This code helps to ensure that the parent, and not the halves, are the only part of the card active on the battlefield.
     * This is important for example when that half has a triggered ability etc that otherwise might trigger twice (once for the parent, once for the half)
     * - in the case that the half was an object on the battlefield. In all other cases, they should all move together.
     */
    @Override
    public void setZone(Zone zone, Game game) {
        if (zone == Zone.BATTLEFIELD) {
            game.setZone(splitCardParent.getId(), zone);
            game.setZone(splitCardParent.getLeftHalfCard().getId(), Zone.OUTSIDE);
            game.setZone(splitCardParent.getRightHalfCard().getId(), Zone.OUTSIDE);
            return;
        }
        super.setZone(zone, game);
    }
}