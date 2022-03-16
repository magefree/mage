
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

/**
 *
 * @author Plopman
 */
public final class RedElementalBlast extends CardImpl {

    private static final FilterPermanent filterPermanent = new FilterPermanent("blue permanent");
    private static final FilterSpell filterSpell = new FilterSpell("blue spell");
    static{
        filterPermanent.add(new ColorPredicate(ObjectColor.BLUE));
        filterSpell.add(new ColorPredicate(ObjectColor.BLUE));
    }
    
    public RedElementalBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Choose one - Counter target blue spell; or destroy target blue permanent.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filterSpell));
        
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filterPermanent));
        
        this.getSpellAbility().addMode(mode);
    }

    private RedElementalBlast(final RedElementalBlast card) {
        super(card);
    }

    @Override
    public RedElementalBlast copy() {
        return new RedElementalBlast(this);
    }
}
