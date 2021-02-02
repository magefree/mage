
package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author nantuko
 */
public final class Manabarbs extends CardImpl {

    public Manabarbs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");


        // Whenever a player taps a land for mana, Manabarbs deals 1 damage to that player.
        this.addAbility(new TapForManaAllTriggeredAbility(
                new DamageTargetEffect(1, true, "that player"),
                new FilterLandPermanent("a player taps a land"), SetTargetPointer.PLAYER));
    }

    private Manabarbs(final Manabarbs card) {
        super(card);
    }

    @Override
    public Manabarbs copy() {
        return new Manabarbs(this);
    }
}
