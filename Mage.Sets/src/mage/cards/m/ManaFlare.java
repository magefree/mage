
package mage.cards.m;

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
 * @author fireshoes
 */
public final class ManaFlare extends CardImpl {

    public ManaFlare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                new FilterLandPermanent("a player taps a land"),
                SetTargetPointer.PERMANENT));
    }

    private ManaFlare(final ManaFlare card) {
        super(card);
    }

    @Override
    public ManaFlare copy() {
        return new ManaFlare(this);
    }
}
