
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.common.delayed.PactDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class SlaughterPact extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonblack creature");

    static {
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }
    
    public SlaughterPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{0}");

        this.color.setBlack(true);
        
        // Destroy target nonblack creature.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        // At the beginning of your next upkeep, pay {2}{B}. If you don't, you lose the game.
        this.getSpellAbility().addEffect(new CreateDelayedTriggeredAbilityEffect(new PactDelayedTriggeredAbility(new ManaCostsImpl("{2}{B}")),false));
    }

    public SlaughterPact(final SlaughterPact card) {
        super(card);
    }

    @Override
    public SlaughterPact copy() {
        return new SlaughterPact(this);
    }
}
