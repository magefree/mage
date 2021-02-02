
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.HorsemanshipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class TripWire extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with horsemanship");

    static {
        filter.add(new AbilityPredicate(HorsemanshipAbility.class));
    }

    public TripWire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");

        // Destroy target creature with horsemanship.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private TripWire(final TripWire card) {
        super(card);
    }

    @Override
    public TripWire copy() {
        return new TripWire(this);
    }
}
