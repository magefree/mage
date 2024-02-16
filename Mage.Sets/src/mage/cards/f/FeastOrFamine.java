
package mage.cards.f;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Mode;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FeastOrFamine extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("nonartifact, nonblack creature");

    static {
        filter.add(Predicates.not(CardType.ARTIFACT.getPredicate()));
        filter.add(Predicates.not(new ColorPredicate(ObjectColor.BLACK)));
    }

    public FeastOrFamine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");

        // Choose one - Create a 2/2 black Zombie creature token; 
        this.getSpellAbility().addEffect(new CreateTokenEffect(new ZombieToken()));
        
        // or destroy target nonartifact, nonblack creature and it can't be regenerated.
        Mode mode = new Mode(new DestroyTargetEffect(true));
        mode.addTarget(new TargetCreaturePermanent(filter));
        this.getSpellAbility().addMode(mode);
    }

    private FeastOrFamine(final FeastOrFamine card) {
        super(card);
    }

    @Override
    public FeastOrFamine copy() {
        return new FeastOrFamine(this);
    }
}
