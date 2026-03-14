package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.SplitCard;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * Continuous effect that sets the name and mana value of a Room permanent based
 * on its unlocked halves.
 * Functions as a characteristic-defining ability.
 * 709.5. Some split cards are permanent cards with a single shared type line.
 * A shared type line on such an object represents two static abilities that
 * function on the battlefield.
 * These are "As long as this permanent doesn't have the 'left half unlocked'
 * designation, it doesn't have the name, mana cost, or rules text of this
 * object's left half"
 * and "As long as this permanent doesn't have the 'right half unlocked'
 * designation, it doesn't have the name, mana cost, or rules text of this
 * object's right half."
 * These abilities, as well as which half of that permanent a characteristic is
 * in, are part of that object's copiable values.
 * @author oscscull
 */
public class RoomCharacteristicsEffect extends ContinuousEffectImpl {


    public RoomCharacteristicsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TextChangingEffects_3, SubLayer.NA,
                Outcome.Neutral);
        staticText = "";
    }

    private RoomCharacteristicsEffect(final RoomCharacteristicsEffect effect) {
        super(effect);
    }

    @Override
    public RoomCharacteristicsEffect copy() {
        return new RoomCharacteristicsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            return false;
        }

        return removeCharacteristics(game, permanent);
    }

    public boolean removeCharacteristics(Game game, Permanent permanent) {
        Card roomCardBlueprint = getCard(permanent);

        if (!(roomCardBlueprint instanceof SplitCard)) {
            return false;
        }

        SplitCard roomCard = (SplitCard) roomCardBlueprint;

        // Remove the name based on unlocked halves
        String newName = permanent.getName();

        boolean isLeftUnlocked = permanent.isLeftDoorUnlocked();
        if (!isLeftUnlocked && roomCard.getLeftHalfCard() != null) {
            newName = newName.replace(roomCard.getLeftHalfCard().getName() + " // ", "");
        }

        boolean isRightUnlocked = permanent.isRightDoorUnlocked();
        if (!isRightUnlocked && roomCard.getRightHalfCard() != null) {
            newName = newName
                    .replace(" // " + roomCard.getRightHalfCard().getName(), "")
                    .replace(roomCard.getRightHalfCard().getName(), "");
        }

        permanent.setName(newName);

        // Set the mana value based on unlocked halves
        // Create a new Mana object to accumulate the costs
        SpellAbility roomCardSpellAbility = roomCard.getSpellAbility().copy();
        // Remove the mana from the left half's cost to our total Mana object
        if (!isLeftUnlocked) {
            ManaCosts<ManaCost> leftHalfManaCost = null;
            if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
                leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
            }
            if (leftHalfManaCost != null) {
                CardUtil.adjustCost(roomCardSpellAbility, leftHalfManaCost, true);
            }
        }

        // Remove the mana from the right half's cost to our total Mana object
        if (!isRightUnlocked) {
            ManaCosts<ManaCost> rightHalfManaCost = null;
            if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
                rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
            }
            if (rightHalfManaCost != null) {
                CardUtil.adjustCost(roomCardSpellAbility, rightHalfManaCost, true);
            }
        }

        ManaCosts<ManaCost> roomCardManaCosts = roomCardSpellAbility.getManaCostsToPay();
        if (roomCardManaCosts.getText().equals("{0}")) {
            roomCardManaCosts = new ManaCostsImpl<>();
        }
        permanent.setManaCost(roomCardManaCosts);


        // Remove abilities from locked halves and add unlock abilities
        Abilities<Ability> removedLeftAbilities = new AbilitiesImpl<>();
        Abilities<Ability> removedRightAbilities = new AbilitiesImpl<>();
        Card abilitySource = permanent;
        if (permanent.isCopy()) {
            abilitySource = (Card) permanent.getCopyFrom();
        }
        for (Ability ability : abilitySource.getAbilities(game)) {
            if (!isLeftUnlocked) {
                if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getAbilities().contains(ability)) {
                    if (!removedLeftAbilities.contains(ability)) {
                        removedLeftAbilities.add(ability);
                    }
                    permanent.removeAbility(ability, null, game);
                    continue;
                }
            }
            if (!isRightUnlocked) {
                if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getAbilities().contains(ability)) {
                    if (!removedRightAbilities.contains(ability)) {
                        removedRightAbilities.add(ability);
                    }
                    permanent.removeAbility(ability, null, game);
                }
            }
        }
        // Add the Special Action to unlock doors.
        // These will ONLY be active if the corresponding half is LOCKED!
        if (!removedLeftAbilities.isEmpty()) {
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(roomCard.getLeftHalfCard().getManaCost(), true);
            permanent.addAbility(leftUnlockAbility, roomCard.getLeftHalfCard().getId(), game);
        }
        if (!removedRightAbilities.isEmpty()) {
            RoomUnlockAbility rightUnlockAbility = new RoomUnlockAbility(roomCard.getRightHalfCard().getManaCost(), false);
            permanent.addAbility(rightUnlockAbility, roomCard.getRightHalfCard().getId(), game);
        }
        return true;
    }

    private static Card getCard(Permanent permanent) {
        Card roomCardBlueprint;

        // Handle copies
        if (permanent.isCopy()) {
            MageObject copiedObject = permanent.getCopyFrom();
            if (copiedObject instanceof PermanentCard) {
                roomCardBlueprint = ((PermanentCard) copiedObject).getCard();
            } else if (copiedObject instanceof Card) {
                roomCardBlueprint = (Card) copiedObject;
            } else {
                roomCardBlueprint = permanent.getMainCard();
            }
        } else {
            roomCardBlueprint = permanent.getMainCard();
        }
        return roomCardBlueprint;
    }

    public void restoreUnlockedStats(Game game, Permanent permanent) {
        // remove unlock abilities
        for (Ability ability : permanent.getAbilities(game)) {
            if (ability instanceof RoomUnlockAbility) {
                if (((RoomUnlockAbility) ability).isLeftHalf() && permanent.isLeftDoorUnlocked()) {
                    permanent.removeAbility(ability, null, game);
                } else if (!((RoomUnlockAbility) ability).isLeftHalf() && permanent.isRightDoorUnlocked()) {
                    permanent.removeAbility(ability, null, game);
                }
            }
        }
        // restore removed abilities
        // copies need abilities to be added back to game state for triggers
        SplitCard roomCard = (SplitCard) getCard(permanent);
        UUID sourceId = permanent.isCopy() ? permanent.getId() : null;
        Game gameParam = permanent.isCopy() ? game : null;
        if (permanent.isLeftDoorUnlocked()) {
            for (Ability ability : roomCard.getLeftHalfCard().getAbilities()) {
                permanent.addAbility(ability, sourceId, gameParam, true);
            }
        }
        if (permanent.isRightDoorUnlocked()) {
            for (Ability ability : roomCard.getRightHalfCard().getAbilities()) {
                permanent.addAbility(ability, sourceId, gameParam, true);
            }
        }
    }
}