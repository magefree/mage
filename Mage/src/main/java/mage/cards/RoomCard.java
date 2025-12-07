package mage.cards;

import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.RoomAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    private SpellAbilityType lastCastHalf = null;

    protected RoomCard(UUID ownerId, CardSetInfo setInfo, String costsLeft, String costsRight) {
        super(ownerId, setInfo, costsLeft, costsRight, SpellAbilityType.SPLIT, new CardType[]{CardType.ENCHANTMENT});

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

    public static void setRoomCharacteristics(Permanent permanent, Game game) {
        if (!(permanent.getMainCard() instanceof RoomCard)) {
            return;
        }
        setRoomCharacteristics(permanent, (RoomCard) permanent.getMainCard(), game, permanent.isLeftDoorUnlocked(), permanent.isRightDoorUnlocked());
    }

    // Static method for setting room characteristics on permanents
    public static void setRoomCharacteristics(Permanent permanent, RoomCard roomCard, Game game, boolean isLeftUnlocked, boolean isRightUnlocked) {
        permanent.setName(roomCard.name);

        permanent.setManaCost(roomCard.getManaCost());

        // Set color indicator based on unlocked halves
        ObjectColor newColor = new ObjectColor();
        if (isLeftUnlocked && roomCard.getLeftHalfCard() != null) {
            newColor.addColor(roomCard.getLeftHalfCard().getColor());
        }
        if (isRightUnlocked && roomCard.getRightHalfCard() != null) {
            newColor.addColor(roomCard.getRightHalfCard().getColor());
        }
        permanent.getColor().setColor(roomCard.getColor());

        // Get abilities from each half
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
        RoomCard roomCard = null;
        // Get the parent card to access the lastCastHalf variable
        if (permanent instanceof PermanentToken) {
            Card mainCard = permanent.getMainCard();
            if (mainCard instanceof RoomCard) {
                roomCard = (RoomCard) mainCard;
            }
        } else {
            Card card = game.getCard(permanent.getId());
            if (card instanceof RoomCard) {
                roomCard = (RoomCard) card;
            }
        }
        if (roomCard == null) {
            return true;
        }

        SpellAbilityType lastCastHalf = roomCard.getLastCastHalf();

        if (lastCastHalf == SpellAbilityType.SPLIT_LEFT || lastCastHalf == SpellAbilityType.SPLIT_RIGHT) {
            roomCard.setLastCastHalf(null);
            return permanent.unlockDoor(game, source, lastCastHalf == SpellAbilityType.SPLIT_LEFT);
        }

        return true;
    }
}
