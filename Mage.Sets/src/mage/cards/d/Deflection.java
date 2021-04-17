
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public final class Deflection extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("spell with a single target");
    
    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }
    
    public Deflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Change the target of target spell with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetSpell(filter));        
    }

    private Deflection(final Deflection card) {
        super(card);
    }

    @Override
    public Deflection copy() {
        return new Deflection(this);
    }
}
