package mage.cards;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.RoomEnterUnlockEffect;
import mage.abilities.effects.common.RoomManaValueEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    protected RoomCard(UUID ownerId, CardSetInfo setInfo, CardType[] typesLeft, CardType[] typesRight, String costsLeft, String costsRight, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, CardType.mergeTypes(typesLeft, typesRight), costsLeft, costsRight, spellAbilityType); 

        String[] names = setInfo.getName().split(" // ");
        
        leftHalfCard = new RoomCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesLeft, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new RoomCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesRight, costsRight, this, SpellAbilityType.SPLIT_RIGHT);

        // Add the oneshot effect to unlock a door on cast -> etb
        Ability entersAbility = new EntersBattlefieldAbility(new RoomEnterUnlockEffect());
        entersAbility.setRuleVisible(false);
        this.addAbility(entersAbility);

        // Add the continuous effect for mana value
        // This effect will run on the battlefield and adjust the permanent's mana value
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RoomManaValueEffect()));
    }

    protected RoomCard(RoomCard card) {
        super(card);
    }
}