package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.SkipCombatStepEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class BlindingAngel extends CardImpl {
    
    public BlindingAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Blinding Angel deals combat damage to a player, that player skips their next combat phase.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SkipCombatStepEffect(Duration.OneUse).setText("that player skips their next combat phase."), false, true));
        
    }
    
    private BlindingAngel(final BlindingAngel card) {
        super(card);
    }
    
    @Override
    public BlindingAngel copy() {
        return new BlindingAngel(this);
    }
}
