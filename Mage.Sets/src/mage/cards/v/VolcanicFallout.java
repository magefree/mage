
package mage.cards.v;

import java.util.UUID;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class VolcanicFallout extends CardImpl {

    public VolcanicFallout(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");


        // Volcanic Fallout can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // Volcanic Fallout deals 2 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(2));
    }

    private VolcanicFallout(final VolcanicFallout card) {
        super(card);
    }

    @Override
    public VolcanicFallout copy() {
        return new VolcanicFallout(this);
    }
}
