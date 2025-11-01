package mage.cards;

import mage.Mana;
import mage.ObjectColor;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RoomCharacteristicsEffect;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;

import java.util.UUID;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    private SpellAbilityType lastCastHalf = null;

    protected RoomCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costsLeft,
            String costsRight, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, costsLeft, costsRight, spellAbilityType, types);

        String[] names = setInfo.getName().split(" // ");

        leftHalfCard = new RoomCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(),
                        setInfo.getRarity(), setInfo.getGraphicInfo()),
                types, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new RoomCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(),
                        setInfo.getRarity(), setInfo.getGraphicInfo()),
                types, costsRight, this, SpellAbilityType.SPLIT_RIGHT);

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

        // Set the name based on unlocked halves
        String newName = "";

        if (isLeftUnlocked && roomCard.getLeftHalfCard() != null) {
            newName += roomCard.getLeftHalfCard().getName();
        }

        if (isRightUnlocked && roomCard.getRightHalfCard() != null) {
            if (!newName.isEmpty()) {
                newName += " // "; // Split card name separator
            }
            newName += roomCard.getRightHalfCard().getName();
        }

        permanent.setName(newName);

        // Set the mana value based on unlocked halves
        // Create a new Mana object to accumulate the costs
        Mana totalManaCost = new Mana();

        // Add the mana from the left half's cost to our total Mana object
        if (isLeftUnlocked) {
            if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
                ManaCosts<ManaCost> leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
                totalManaCost.add(leftHalfManaCost.getMana());
            }
        }

        // Add the mana from the right half's cost to our total Mana object
        if (isRightUnlocked) {
            if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
                ManaCosts<ManaCost> rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
                totalManaCost.add(rightHalfManaCost.getMana());
            }
        }

        String newManaCostString = totalManaCost.toString();
        ManaCostsImpl<ManaCost> newManaCosts;

        // If both halves are locked or total 0, it's 0mv.
        if (newManaCostString.isEmpty() || totalManaCost.count() == 0) {
            newManaCosts = new ManaCostsImpl<>("");
        } else {
            newManaCosts = new ManaCostsImpl<>(newManaCostString);
        }

        permanent.setManaCost(newManaCosts);

        // Set color indicator based on unlocked halves
        ObjectColor newColor = new ObjectColor();
        if (isLeftUnlocked && roomCard.getLeftHalfCard() != null) {
            newColor.addColor(roomCard.getLeftHalfCard().getColor());
        }
        if (isRightUnlocked && roomCard.getRightHalfCard() != null) {
            newColor.addColor(roomCard.getRightHalfCard().getColor());
        }
        permanent.getColor().setColor(newColor);

        permanent.getAbilities().clear();

        // Add abilities from the main card
        for (Ability ability : roomCard.getAbilities()) {
            permanent.addAbility(ability, null, null, true);
        }

        // Get abilities from unlocked halves and give unlock abilities for locked halves
        if (isLeftUnlocked && roomCard.getLeftHalfCard() != null) {
            Abilities<Ability> leftAbilities = roomCard.getLeftHalfCard().getAbilities();
            for (Ability ability : leftAbilities) {
                permanent.addAbility(ability, roomCard.getLeftHalfCard().getId(), game, true);
            }
        } else {
            // Add the Special Action to unlock doors.
            // These will ONLY be active if the corresponding half is LOCKED!
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(roomCard.getLeftHalfCard().getManaCost(), true);
            permanent.addAbility(leftUnlockAbility, roomCard.getLeftHalfCard().getId(), game);
        }

        if (isRightUnlocked && roomCard.getRightHalfCard() != null) {
            Abilities<Ability> rightAbilities = roomCard.getRightHalfCard().getAbilities();
            for (Ability ability : rightAbilities) {
                permanent.addAbility(ability, roomCard.getRightHalfCard().getId(), game,true);
            }
        } else {
            RoomUnlockAbility rightUnlockAbility = new RoomUnlockAbility(roomCard.getRightHalfCard().getManaCost(), false);
            permanent.addAbility(rightUnlockAbility, roomCard.getRightHalfCard().getId(), game);
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

// For the overall Room card flavor text and mana value effect.
class RoomAbility extends SimpleStaticAbility {
    public RoomAbility() {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);
        this.addEffect(new RoomCharacteristicsEffect());
    }

    protected RoomAbility(final RoomAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "<i>(You may cast either half. That door unlocks on the battlefield. " +
                "As a sorcery, you may pay the mana cost of a locked door to unlock it.)</i>";
    }

    @Override
    public RoomAbility copy() {
        return new RoomAbility(this);
    }
}