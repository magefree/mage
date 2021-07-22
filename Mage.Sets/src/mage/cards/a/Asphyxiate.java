
package mage.cards.a;

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
 * @author LevelX2
 */
public final class Asphyxiate extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("untapped creature");
    
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }
    
    public Asphyxiate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{B}{B}");


        // Destroy target untapped creature.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private Asphyxiate(final Asphyxiate card) {
        super(card);
    }

    @Override
    public Asphyxiate copy() {
        return new Asphyxiate(this);
    }
}
