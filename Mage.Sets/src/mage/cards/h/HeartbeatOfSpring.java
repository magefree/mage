
package mage.cards.h;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author Loki
 */
public final class HeartbeatOfSpring extends CardImpl {

    public HeartbeatOfSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");

        
        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                new FilterLandPermanent("a player taps a land"),
                SetTargetPointer.PERMANENT));
    }

    private HeartbeatOfSpring(final HeartbeatOfSpring card) {
        super(card);
    }

    @Override
    public HeartbeatOfSpring copy() {
        return new HeartbeatOfSpring(this);
    }
}
