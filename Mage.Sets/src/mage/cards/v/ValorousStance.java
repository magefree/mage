
package mage.cards.v;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ToughnessPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class ValorousStance extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with toughness 4 or greater");

    static {
        filter.add(new ToughnessPredicate(ComparisonType.MORE_THAN, 3));
    }
    
    public ValorousStance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        
        //Choose one - Target creature gains Indestructible until end of turn
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        // or destroy target creature with toughness 4 or greater.
        Mode mode1 = new Mode(new DestroyTargetEffect());
        mode1.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode1);

    }

    private ValorousStance(final ValorousStance card) {
        super(card);
    }

    @Override
    public ValorousStance copy() {
        return new ValorousStance(this);
    }
    
}
