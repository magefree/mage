
package mage.cards.d;

import java.util.UUID;
import mage.Mana;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class DarkRitual extends CardImpl {

    public DarkRitual(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.BlackMana(3)));
    }

    private DarkRitual(final DarkRitual card) {
        super(card);
    }

    @Override
    public DarkRitual copy() {
        return new DarkRitual(this);
    }
}
