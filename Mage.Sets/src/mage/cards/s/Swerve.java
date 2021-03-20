
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
 * @author jeffwadsworth
 */
public final class Swerve extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("spell with a single target");
    
    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public Swerve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{R}");


        // Change the target of target spell with a single target.
        this.getSpellAbility().addEffect(new ChooseNewTargetsTargetEffect(true, true));
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        
    }

    private Swerve(final Swerve card) {
        super(card);
    }

    @Override
    public Swerve copy() {
        return new Swerve(this);
    }
}
