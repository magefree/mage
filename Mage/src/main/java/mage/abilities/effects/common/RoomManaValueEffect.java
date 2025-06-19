package mage.abilities.effects.common;

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
import mage.Mana;

/**
 * Continuous effect that sets the mv of a Room permanent based on its unlocked halves.
 */
public class RoomManaValueEffect extends ContinuousEffectImpl {

    public RoomManaValueEffect() {
        super(Duration.WhileOnBattlefield, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.Neutral);
        staticText = "its mana value is equal to the total mana value of its unlocked halves";
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

        Card originalCard = permanent.getMainCard();
        if (!(originalCard instanceof SplitCard)) {
            return false;
        }

        SplitCard roomCard = (SplitCard) originalCard;

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