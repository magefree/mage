package mage.cards;

import java.util.UUID;

import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    private boolean leftHalfUnlocked = false;
    private boolean rightHalfUnlocked = false;
    private SpellAbilityType lastCastHalf;

    protected RoomCard(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, CardType[] cardTypes2, String string,
            String string2, SpellAbilityType split) {
        super(ownerId, setInfo, cardTypes, cardTypes2, string,
                string2, split);
    }

    protected RoomCard(RoomCard card) {
        super(card);
        // make sure all parts created and parent ref added
        this.leftHalfCard = card.getLeftHalfCard().copy();
        ((SplitCardHalf) leftHalfCard).setParentCard(this);
        this.rightHalfCard = card.rightHalfCard.copy();
        ((SplitCardHalf) rightHalfCard).setParentCard(this);
    }
    
    public boolean isLeftHalfLocked() {
        return !leftHalfUnlocked;
    }
    
    public boolean isRightHalfLocked() {
        return !rightHalfUnlocked;
    }
    
    public void unlockLeftHalf(Game game, Ability source) {
        leftHalfUnlocked = true;
        // Trigger any unlock abilities
        game.fireEvent(new GameEvent(GameEvent.EventType.UNLOCK_DOOR, getId(), source, getControllerOrOwnerId()));
    }
    
    public void unlockRightHalf(Game game, Ability source) {
        rightHalfUnlocked = true;
        game.fireEvent(new GameEvent(GameEvent.EventType.UNLOCK_DOOR, getId(), source, getControllerOrOwnerId()));
    }
    
    public SpellAbilityType getLastCastHalf() {
        return lastCastHalf;
    }
    
    public void setLastCastHalf(SpellAbilityType half) {
        this.lastCastHalf = half;
    }
}