
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DamageEverythingEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author fireshoes
 */
public final class DakmorPlague extends CardImpl {

    public DakmorPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{B}{B}");

        // Dakmor Plague deals 3 damage to each creature and each player.
        this.getSpellAbility().addEffect(new DamageEverythingEffect(3));
    }

    private DakmorPlague(final DakmorPlague card) {
        super(card);
    }

    @Override
    public DakmorPlague copy() {
        return new DakmorPlague(this);
    }
}
