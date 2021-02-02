
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LevelX2
 */
public final class Ruination extends CardImpl {

    public Ruination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}");


        // Destroy all nonbasic lands.
        this.getSpellAbility().addEffect(new DestroyAllEffect(FilterLandPermanent.nonbasicLands()));
    }

    private Ruination(final Ruination card) {
        super(card);
    }

    @Override
    public Ruination copy() {
        return new Ruination(this);
    }
}
