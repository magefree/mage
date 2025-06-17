package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.RoomCard;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.game.Game;

public class RoomUnlockDoorEffect extends OneShotEffect {

    public RoomUnlockDoorEffect() {
        super(Outcome.Benefit);
        staticText = "unlock the appropriate door";
    }

    private RoomUnlockDoorEffect(final RoomUnlockDoorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        System.out.println("DEBUG: RoomUnlockDoorEffect.apply() called!"); // DEBUG
        
        RoomCard room = (RoomCard) source.getSourceObject(game);
        System.out.println("DEBUG: Room found: " + (room != null)); // DEBUG
        
        if (room != null && room.getLastCastHalf() != null) {
            System.out.println("DEBUG: Last cast half: " + room.getLastCastHalf()); // DEBUG
            
            if (room.getLastCastHalf() == SpellAbilityType.SPLIT_LEFT) {
                System.out.println("DEBUG: Unlocking left half"); // DEBUG
                room.unlockLeftHalf(game, source);
                return true;
            } else if (room.getLastCastHalf() == SpellAbilityType.SPLIT_RIGHT) {
                System.out.println("DEBUG: Unlocking right half"); // DEBUG
                room.unlockRightHalf(game, source);
                return true;
            }
        }
        System.out.println("DEBUG: RoomUnlockDoorEffect failed - room: " + room + ", lastCast: " + (room != null ? room.getLastCastHalf() : "null")); // DEBUG
        return false;
    }

    @Override
    public RoomUnlockDoorEffect copy() {
        return new RoomUnlockDoorEffect(this);
    }
}
