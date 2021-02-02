
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 *
 * @author LevelX2
 */
public final class SeismicRupture extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public SeismicRupture(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{R}");

        // Seismic Rupture deals 2 damage to each creature without flying.
        this.getSpellAbility().addEffect(new DamageAllEffect(2, filter));
    }

    private SeismicRupture(final SeismicRupture card) {
        super(card);
    }

    @Override
    public SeismicRupture copy() {
        return new SeismicRupture(this);
    }
}
