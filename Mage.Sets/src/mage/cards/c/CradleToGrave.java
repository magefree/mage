
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author bieno002
 */
public final class CradleToGrave extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature that entered the battlefield this turn");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
        filter.add(EnteredThisTurnPredicate.instance);
    }
    
    public CradleToGrave(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");

        // Destroy target nonblack creature that entered the battlefield this turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
    }

    private CradleToGrave(final CradleToGrave card) {
        super(card);
    }

    @Override
    public CradleToGrave copy() {
        return new CradleToGrave(this);
    }
}
