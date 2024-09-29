package mage.cards.s;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShootTheSheriff extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("non-outlaw creature");

    static {
        filter.add(Predicates.not(OutlawPredicate.instance));
    }

    public ShootTheSheriff(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Destroy target non-outlaw creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private ShootTheSheriff(final ShootTheSheriff card) {
        super(card);
    }

    @Override
    public ShootTheSheriff copy() {
        return new ShootTheSheriff(this);
    }
}
// everyone else is fair game
