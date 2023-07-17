
package mage.cards.g;

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
 * @author ChesseTheWasp
 */
public final class GreenElementalBlast extends CardImpl {
    
    private static final FilterPermanent filterPermanent = new FilterPermanent("black permanent");
    private static final FilterSpell filterSpell = new FilterSpell("black spell");
    static{
        filterPermanent.add(new ColorPredicate(ObjectColor.BLACK));
        filterSpell.add(new ColorPredicate(ObjectColor.BLACK));
    }
    

    public GreenElementalBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Choose one - Counter target black spell; or destroy target black permanent.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filterSpell));
        
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filterPermanent));
        
        this.getSpellAbility().addMode(mode);
    }

    private GreenElementalBlast(final GreenElementalBlast card) {
        super(card);
    }

    @Override
    public GreenElementalBlast copy() {
        return new GreenElementalBlast(this);
    }
}
