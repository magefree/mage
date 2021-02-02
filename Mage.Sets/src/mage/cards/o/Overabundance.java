
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.common.TapForManaAllTriggeredManaAbility;
import mage.abilities.effects.mana.AddManaOfAnyTypeProducedEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author elliott-king
 */
public final class Overabundance extends CardImpl {

    public Overabundance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");
        

        // Whenever a player taps a land for mana, that player adds one mana of any type that land produced, and Overabundance deals 1 damage to that player.
        this.addAbility(new TapForManaAllTriggeredManaAbility(
                new AddManaOfAnyTypeProducedEffect(),
                new FilterLandPermanent( "a player taps a land"),
                SetTargetPointer.PERMANENT
        ));

        this.addAbility(new TapForManaAllTriggeredAbility(
                new DamageTargetEffect(1, true, "that player"),
                new FilterLandPermanent("a player taps a land"),
                SetTargetPointer.PLAYER
        ));
    }

    private Overabundance(final Overabundance card) {
        super(card);
    }

    @Override
    public Overabundance copy() {
        return new Overabundance(this);
    }
}
