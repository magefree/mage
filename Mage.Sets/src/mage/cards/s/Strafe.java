
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox

 */
public final class Strafe extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonred creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.RED)));
    }

    public Strafe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}");

        // Strafe deals 3 damage to target nonred creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private Strafe(final Strafe card) {
        super(card);
    }

    @Override
    public Strafe copy() {
        return new Strafe(this);
    }
}
