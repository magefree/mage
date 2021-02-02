
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Plopman
 */
public final class Inferno extends CardImpl {

    public Inferno(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{R}{R}");


        // Inferno deals 6 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(6));
    }

    private Inferno(final Inferno card) {
        super(card);
    }

    @Override
    public Inferno copy() {
        return new Inferno(this);
    }
}
