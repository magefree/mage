package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class TakeVengeance extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("tapped creature");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    public TakeVengeance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}");

        // Destroy target tapped creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private TakeVengeance(final TakeVengeance card) {
        super(card);
    }

    @Override
    public TakeVengeance copy() {
        return new TakeVengeance(this);
    }
}
