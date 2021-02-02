
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureOrPlaneswalkerPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class StarOfExtinction extends CardImpl {

    public StarOfExtinction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");

        // Destroy target land. Star of Extinction deals 20 damage to each creature and each planeswalker.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new DamageAllEffect(20, new FilterCreatureOrPlaneswalkerPermanent("creature and each planeswalker")));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private StarOfExtinction(final StarOfExtinction card) {
        super(card);
    }

    @Override
    public StarOfExtinction copy() {
        return new StarOfExtinction(this);
    }
}
