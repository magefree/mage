package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
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
import mage.game.permanent.PermanentCard; // Ensure this import is present
import mage.Mana;

/**
 * Continuous effect that sets the mv of a Room permanent based on its unlocked
 * halves.
 * 
 * 604.3. Some static abilities are characteristic-defining abilities.
 * A characteristic-defining ability conveys information about an object’s
 * characteristics
 * that would normally be found elsewhere on that object
 * (such as in its mana cost, type line, or power/toughness box).
 * Characteristic-defining abilities can add to or override information found
 * elsewhere on that object.
 * Characteristic-defining abilities function in all zones. They also function
 * outside the game and before the game begins.
 *
 * 709.5. Some split cards are permanent cards with a single shared type line.
 * A shared type line on such an object represents two static abilities that
 * function on the battlefield.
 * These are “As long as this permanent doesn’t have the ‘left half unlocked’
 * designation, it doesn’t have the name, mana cost, or rules text of this
 * object’s left half”
 * and “As long as this permanent doesn’t have the ‘right half unlocked’
 * designation, it doesn’t have the name, mana cost, or rules text of this
 * object’s right half.”
 * These abilities, as well as which half of that permanent a characteristic is
 * in, are part of that object’s copiable values.
 */
public class RoomManaValueEffect extends ContinuousEffectImpl {

    public RoomManaValueEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a,
                Outcome.Neutral);
        staticText = "";
    }

    private RoomManaValueEffect(final RoomManaValueEffect effect) {
        super(effect);
    }

    @Override
    public RoomManaValueEffect copy() {
        return new RoomManaValueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (permanent == null) {
            return false;
        }

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

        if (!(roomCardBlueprint instanceof SplitCard)) {
            return false;
        }

        SplitCard roomCard = (SplitCard) roomCardBlueprint;

        // Create a new Mana object to accumulate the costs
        Mana totalManaCost = new Mana();

        // Add the mana from the left half's cost to our total Mana object
        boolean isLeftUnlocked = permanent.isLeftHalfUnlocked();
        if (isLeftUnlocked) {
            ManaCosts leftHalfManaCost = null;
            if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
                leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
            }
            if (leftHalfManaCost != null) {
                totalManaCost.add(leftHalfManaCost.getMana());
            }
        }

        // Add the mana from the right half's cost to our total Mana object
        boolean isRightUnlocked = permanent.isRightHalfUnlocked();
        if (isRightUnlocked) {
            ManaCosts rightHalfManaCost = null;
            if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
                rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
            }
            if (rightHalfManaCost != null) {
                totalManaCost.add(rightHalfManaCost.getMana());
            }
        }

        String newManaCostString = totalManaCost.toString();
        ManaCostsImpl newManaCosts;

        // If both halves are locked or total 0, it's 0mv.
        if (newManaCostString.isEmpty() || totalManaCost.count() == 0) {
            newManaCosts = new ManaCostsImpl<>("");
        } else {
            newManaCosts = new ManaCostsImpl<>(newManaCostString);
        }

        permanent.setManaCost(newManaCosts);

        return true;
    }
}