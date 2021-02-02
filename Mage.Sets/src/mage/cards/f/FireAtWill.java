
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.DamageMultiEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author jeffwadsworth
 *
 */
public final class FireAtWill extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("attacking or blocking creatures");

    static {
        filter.add(Predicates.or(
                AttackingPredicate.instance,
                BlockingPredicate.instance));
    }

    public FireAtWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R/W}{R/W}{R/W}");

        // Fire at Will deals 3 damage divided as you choose among one, two, or three target attacking or blocking creatures.
        this.getSpellAbility().addEffect(new DamageMultiEffect(3).setText("{this} deals 3 damage divided as you choose among one, two, or three target attacking or blocking creatures."));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3, filter));

    }

    private FireAtWill(final FireAtWill card) {
        super(card);
    }

    @Override
    public FireAtWill copy() {
        return new FireAtWill(this);
    }
}
