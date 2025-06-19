// src/main/java/mage/abilities/effects/common/RoomNameEffect.java
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
 * Continuous effect that sets the name of a Room permanent based on its unlocked halves.
 * Functions as a characteristic-defining ability.
 * Rule 709.5: "As long as this permanent doesn’t have the ‘left half unlocked’ designation,
 * it doesn’t have the name... of this object’s left half” and vice versa for the right half.
 */
public class RoomNameEffect extends ContinuousEffectImpl {

    public RoomNameEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA,
                Outcome.Neutral);
        staticText = ""; // No visible text needed, as it's a CDA
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
   System.out.println("here ");
   System.out.println("1 - getting permanent");
   Permanent permanent = game.getPermanent(source.getSourceId());
   if (permanent == null) {
       System.out.println("2 - permanent is null, returning false");
       return false;
   }
   
   System.out.println("3 - permanent found, getting room card blueprint");
   Card roomCardBlueprint;
   if (permanent.isCopy()) {
       System.out.println("4 - permanent is copy");
       MageObject copiedObject = permanent.getCopyFrom();
       System.out.println("5 - got copied object");
       if (copiedObject instanceof PermanentCard) {
           System.out.println("6 - copied object is PermanentCard");
           roomCardBlueprint = ((PermanentCard) copiedObject).getCard();
       } else if (copiedObject instanceof Card) {
           System.out.println("7 - copied object is Card");
           roomCardBlueprint = (Card) copiedObject;
       } else {
           System.out.println("8 - copied object is other, getting main card");
           roomCardBlueprint = permanent.getMainCard();
       }
   } else {
       System.out.println("9 - permanent not copy, getting main card");
       roomCardBlueprint = permanent.getMainCard();
   }
   
   System.out.println("10 - checking if split card");
   if (!(roomCardBlueprint instanceof SplitCard)) {
       System.out.println("11 - not split card, returning false");
       return false;
   }
   System.out.println("12 - is split card, casting");
   SplitCard roomCard = (SplitCard) roomCardBlueprint;
   
   System.out.println("13 - checking unlock status");
   boolean isLeftUnlocked = permanent.isLeftHalfUnlocked();
   System.out.println("14 - left unlocked: " + isLeftUnlocked);
   boolean isRightUnlocked = permanent.isRightHalfUnlocked();
   System.out.println("15 - right unlocked: " + isRightUnlocked);
   
   System.out.println("16 - building new name");
   String newName = "";

   if (isLeftUnlocked && roomCard.getLeftHalfCard() != null) {
       System.out.println("17 - adding left half name");
       newName += roomCard.getLeftHalfCard().getName();
       System.out.println("18 - left half name added: " + newName);
   }

   if (isRightUnlocked && roomCard.getRightHalfCard() != null) {
       System.out.println("19 - adding right half name");
       if (!newName.isEmpty()) {
           System.out.println("20 - adding separator");
           newName += " // "; // Standard split card name separator
       }
       newName += roomCard.getRightHalfCard().getName();
       System.out.println("21 - right half name added: " + newName);
   }

   System.out.println("22 - checking if name is empty");
   // If neither is unlocked, the rule implies it "doesn't have" either name.
   // So, the name should be empty.
   if (newName.isEmpty()) {
       System.out.println("23 - setting empty name");
       permanent.setName(""); // Set to empty if no halves are unlocked or valid
       System.out.println("24 - empty name set");
   } else {
       System.out.println("25 - setting new name: " + newName);
       permanent.setName(newName);
       System.out.println("26 - new name set");
   }

   System.out.println("27 - returning true");
   return true;
}
}