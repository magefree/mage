
package mage.cards.b;

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
public final class BlackElementalBlast extends CardImpl {
    
    private static final FilterPermanent filterPermanent = new FilterPermanent("green permanent");
    private static final FilterSpell filterSpell = new FilterSpell("green spell");
    static{
        filterPermanent.add(new ColorPredicate(ObjectColor.GREEN));
        filterSpell.add(new ColorPredicate(ObjectColor.GREEN));
    }
    

    public BlackElementalBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");


        // Choose one - Counter target Green spell; or destroy target Green permanent.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filterSpell));
        
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filterPermanent));
        
        this.getSpellAbility().addMode(mode);
    }

    private BlackElementalBlast(final BlackElementalBlast card) {
        super(card);
    }

    @Override
    public BlackElementalBlast copy() {
        return new BlackElementalBlast(this);
    }
}
