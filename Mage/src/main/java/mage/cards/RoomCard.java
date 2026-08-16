package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.RoomAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RoomCharacteristicsEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    private SpellAbilityType lastCastHalf = null;

    protected RoomCard(UUID ownerId, CardSetInfo setInfo, String costsLeft, String costsRight) {
        super(ownerId, setInfo, costsLeft, costsRight, SpellAbilityType.SPLIT, new CardType[]{CardType.ENCHANTMENT});
        this.addSubType(SubType.ROOM);

        String[] names = setInfo.getName().split(" // ");

        leftHalfCard = new RoomCardHalfImpl(
                new CardSetInfo(names[0], setInfo), costsLeft, this, SpellAbilityType.SPLIT_LEFT
        );
        rightHalfCard = new RoomCardHalfImpl(
                new CardSetInfo(names[1], setInfo), costsRight, this, SpellAbilityType.SPLIT_RIGHT
        );

        // Add the one-shot effect to unlock a door on cast -> ETB
        Ability entersAbility = new EntersBattlefieldAbility(new RoomEnterUnlockEffect());
        entersAbility.setRuleVisible(false);
        this.addAbility(entersAbility);

        this.addAbility(new RoomAbility());
    }

    protected RoomCard(RoomCard card) {
        super(card);
        this.lastCastHalf = card.lastCastHalf;
    }

    public SpellAbilityType getLastCastHalf() {
        return lastCastHalf;
    }

    public void setLastCastHalf(SpellAbilityType lastCastHalf) {
        this.lastCastHalf = lastCastHalf;
    }

    @Override
    public Abilities<Ability> getAbilities() {
        return this.abilities;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return this.abilities;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);

        if (zone == Zone.BATTLEFIELD) {
            game.setZone(getLeftHalfCard().getId(), Zone.OUTSIDE);
            game.setZone(getRightHalfCard().getId(), Zone.OUTSIDE);
            return;
        }

        game.setZone(getLeftHalfCard().getId(), zone);
        game.setZone(getRightHalfCard().getId(), zone);
    }

    public static void addRoomCharacteristics(Permanent permanent, RoomCard roomCard, Game game) {
        // 709.5.
        // Some split cards are permanent cards with a single shared type line. A shared type line 
        // on such an object represents two static abilities that function on the battlefield. These 
        // are “As long as this permanent doesn’t have the ‘left half unlocked’ designation, it 
        // doesn’t have the name, mana cost, or rules text of this object’s left half” and “As long 
        // as this permanent doesn’t have the ‘right half unlocked’ designation, it doesn’t have the 
        // name, mana cost, or rules text of this object’s right half.” These abilities, as well as 
        // which half of that permanent a characteristic is in, are part of that object’s copiable 
        // values.

        // add all rooms data, real rule apply by RoomCharacteristicsEffect
        // if you catch duplicated effect then debug effect's code

        permanent.setName(roomCard.getName());
        permanent.setManaCost(roomCard.getManaCost());

        Abilities<Ability> leftAbilities = roomCard.getLeftHalfCard().getAbilities();
        for (Ability ability : leftAbilities) {
            permanent.addAbility(ability, roomCard.getLeftHalfCard().getId(), game, true);
        }

        Abilities<Ability> rightAbilities = roomCard.getRightHalfCard().getAbilities();
        for (Ability ability : rightAbilities) {
            permanent.addAbility(ability, roomCard.getRightHalfCard().getId(), game, true);
        }
    }
}

class RoomEnterUnlockEffect extends OneShotEffect {
    public RoomEnterUnlockEffect() {
        super(Outcome.Neutral);
        staticText = "";
    }

    private RoomEnterUnlockEffect(final RoomEnterUnlockEffect effect) {
        super(effect);
    }

    @Override
    public RoomEnterUnlockEffect copy() {
        return new RoomEnterUnlockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            return false;
        }

        if (permanent.wasRoomUnlockedOnCast()) {
            return false;
        }

        permanent.unlockRoomOnCast(game);

        // Get the parent card to access the lastCastHalf variable
        RoomCard roomCardBlueprint = (RoomCard) RoomCharacteristicsEffect.findRoomCard(permanent);
        if (roomCardBlueprint == null) {
            return true;
        }

        // TODO: possible buggy with AI -- find spell mode by spell ability and do not store it in card's data due game states isolation?!
        SpellAbilityType lastCastHalf = roomCardBlueprint.getLastCastHalf();
        if (lastCastHalf == SpellAbilityType.SPLIT_LEFT || lastCastHalf == SpellAbilityType.SPLIT_RIGHT) {
            roomCardBlueprint.setLastCastHalf(null);
            return permanent.unlockDoor(game, source, lastCastHalf == SpellAbilityType.SPLIT_LEFT);
        }

        return true;
    }
}
