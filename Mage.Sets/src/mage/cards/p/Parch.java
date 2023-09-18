
package mage.cards.p;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class Parch extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blue creature");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public Parch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}");


        // Choose one - Parch deals 2 damage to any target; or Parch deals 4 damage to target blue creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(2));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
        Mode mode = new Mode(new DamageTargetEffect(4));
        mode.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private Parch(final Parch card) {
        super(card);
    }

    @Override
    public Parch copy() {
        return new Parch(this);
    }
}
