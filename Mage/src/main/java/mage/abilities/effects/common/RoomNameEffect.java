package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
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

/**
 * Continuous effect that sets the name of a Room permanent based on its
 * unlocked halves.
 * Functions as a characteristic-defining ability.
 * Rule 709.5: "As long as this permanent doesn’t have the ‘left half unlocked’
 * designation,
 * it doesn’t have the name... of this object’s left half” and vice versa for
 * the right half.
 */
public class RoomNameEffect extends ContinuousEffectImpl {

    public RoomNameEffect() {
        super(Duration.WhileOnBattlefield, Layer.PTChangingEffects_7, SubLayer.CharacteristicDefining_7a,
                Outcome.Neutral);
        staticText = "";
    }

    private RoomNameEffect(final RoomNameEffect effect) {
        super(effect);
    }

    @Override
    public RoomNameEffect copy() {
        return new RoomNameEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());

        if (permanent == null) {
            return false;
        }

        Card roomCardBlueprint;
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
        boolean isLeftUnlocked = permanent.isLeftHalfUnlocked();
        boolean isRightUnlocked = permanent.isRightHalfUnlocked();
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

        return true;
    }
}