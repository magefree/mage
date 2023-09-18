
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
 */
public final class BlueElementalBlast extends CardImpl {
    
    private static final FilterPermanent filterPermanent = new FilterPermanent("red permanent");
    private static final FilterSpell filterSpell = new FilterSpell("red spell");
    static{
        filterPermanent.add(new ColorPredicate(ObjectColor.RED));
        filterSpell.add(new ColorPredicate(ObjectColor.RED));
    }
    

    public BlueElementalBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");


        // Choose one - Counter target red spell; or destroy target red permanent.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filterSpell));
        
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filterPermanent));
        
        this.getSpellAbility().addMode(mode);
    }

    private BlueElementalBlast(final BlueElementalBlast card) {
        super(card);
    }

    @Override
    public BlueElementalBlast copy() {
        return new BlueElementalBlast(this);
    }
}
