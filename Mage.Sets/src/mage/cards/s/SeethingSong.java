
package mage.cards.s;

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
public final class SeethingSong extends CardImpl {

    public SeethingSong(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{R}");

        this.getSpellAbility().addEffect(new BasicManaEffect(Mana.RedMana(5)));
    }

    private SeethingSong(final SeethingSong card) {
        super(card);
    }

    @Override
    public SeethingSong copy() {
        return new SeethingSong(this);
    }
}
