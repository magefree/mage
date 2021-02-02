
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class DromokasGift extends CardImpl {

    public DromokasGift(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{G}");

        // Bolster 4.
        this.getSpellAbility().addEffect(new BolsterEffect(4));
    }

    private DromokasGift(final DromokasGift card) {
        super(card);
    }

    @Override
    public DromokasGift copy() {
        return new DromokasGift(this);
    }
}
