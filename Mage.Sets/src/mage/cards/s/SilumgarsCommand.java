
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SilumgarsCommand extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("noncreature spell");
    private static final FilterPermanent filter2 = new FilterPermanent("planeswalker");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.CREATURE)));
        filter2.add(new CardTypePredicate(CardType.PLANESWALKER));
    }

    public SilumgarsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{B}");

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        
        // Counter target noncreature spell;
        this.getSpellAbility().getEffects().add(new CounterTargetEffect());
        this.getSpellAbility().getTargets().add(new TargetSpell(filter));
        
        // or Return target permanent to its owner's hand; 
        Mode mode = new Mode();
        mode.getEffects().add(new ReturnToHandTargetEffect());
        mode.getTargets().add(new TargetPermanent());
        this.getSpellAbility().getModes().addMode(mode);        
     
        // or Target creature gets -3/-3 until end of turn;
        mode = new Mode();
        mode.getEffects().add(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        mode.getTargets().add(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().addMode(mode);        

        // or Destroy target planeswalker.
        mode = new Mode();
        mode.getEffects().add(new DestroyTargetEffect());
        mode.getTargets().add(new TargetPermanent(filter2));
        this.getSpellAbility().getModes().addMode(mode);        
    }

    public SilumgarsCommand(final SilumgarsCommand card) {
        super(card);
    }

    @Override
    public SilumgarsCommand copy() {
        return new SilumgarsCommand(this);
    }
}
