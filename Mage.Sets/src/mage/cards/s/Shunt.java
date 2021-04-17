
package mage.cards.s;

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
public final class Shunt extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with a single target");
    
    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }
    
    public Shunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{R}{R}");


        // Change the target of target spell with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetSpell(filter));        
    }

    private Shunt(final Shunt card) {
        super(card);
    }

    @Override
    public Shunt copy() {
        return new Shunt(this);
    }
}
