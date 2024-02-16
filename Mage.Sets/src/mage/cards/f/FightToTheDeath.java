package mage.cards.f;

import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FightToTheDeath extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking creatures and all blocked creatures");

    static {
        filter.add(Predicates.or(
                BlockingPredicate.instance,
                BlockedPredicate.instance
        ));
    }

    public FightToTheDeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Destroy all blocking creatures and all blocked creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
    }

    private FightToTheDeath(final FightToTheDeath card) {
        super(card);
    }

    @Override
    public FightToTheDeath copy() {
        return new FightToTheDeath(this);
    }
}
