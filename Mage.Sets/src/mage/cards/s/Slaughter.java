
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.BuybackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Temba21
 */
public final class Slaughter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }
    
    public Slaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{B}{B}");

        // Buyback-Pay 4 life.
        this.addAbility(new BuybackAbility(new PayLifeCost(4)));
        
        // Destroy target nonblack creature. It can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public Slaughter(final Slaughter card) {
        super(card);
    }

    @Override
    public Slaughter copy() {
        return new Slaughter(this);
    }
}
