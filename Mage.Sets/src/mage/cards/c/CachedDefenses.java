
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.keyword.BolsterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class CachedDefenses extends CardImpl {

    public CachedDefenses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Bolster 3.
        this.getSpellAbility().addEffect(new BolsterEffect(3));
    }

    private CachedDefenses(final CachedDefenses card) {
        super(card);
    }

    @Override
    public CachedDefenses copy() {
        return new CachedDefenses(this);
    }
}
