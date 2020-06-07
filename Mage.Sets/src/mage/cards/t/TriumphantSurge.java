package mage.cards.t;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TriumphantSurge extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, 3));
    }

    public TriumphantSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Destroy target creature with power 4 or greater. You gain 3 life.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new GainLifeEffect(3));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private TriumphantSurge(final TriumphantSurge card) {
        super(card);
    }

    @Override
    public TriumphantSurge copy() {
        return new TriumphantSurge(this);
    }
}
