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

    public RoomCardHalfImpl(CardSetInfo setInfo, String costs, RoomCard splitCardParent, SpellAbilityType spellAbilityType) {
        super(splitCardParent.ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, costs, splitCardParent, spellAbilityType);
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
}
