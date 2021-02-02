
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author ilcartographer
 */
public final class FireTempest extends CardImpl {

    public FireTempest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{5}{R}{R}");

        // Fire Tempest deals 6 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(6));
    }

    private FireTempest(final FireTempest card) {
        super(card);
    }

    @Override
    public FireTempest copy() {
        return new FireTempest(this);
    }
}
