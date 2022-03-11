
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
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class SilumgarsCommand extends CardImpl {
    private static final FilterPermanent filter2 = new FilterPermanent("planeswalker");

    static {
        filter2.add(CardType.PLANESWALKER.getPredicate());
    }

    public SilumgarsCommand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}{B}");

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);
        
        // Counter target noncreature spell;
        this.getSpellAbility().getEffects().add(new CounterTargetEffect());
        this.getSpellAbility().getTargets().add(new TargetSpell(StaticFilters.FILTER_SPELL_NON_CREATURE));
        
        // or Return target permanent to its owner's hand; 
        Mode mode = new Mode(new ReturnToHandTargetEffect());
        mode.addTarget(new TargetPermanent());
        this.getSpellAbility().getModes().addMode(mode);        
     
        // or Target creature gets -3/-3 until end of turn;
        mode = new Mode(new BoostTargetEffect(-3, -3, Duration.EndOfTurn));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().getModes().addMode(mode);        

        // or Destroy target planeswalker.
        mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().getModes().addMode(mode);        
    }

    private SilumgarsCommand(final SilumgarsCommand card) {
        super(card);
    }

    @Override
    public SilumgarsCommand copy() {
        return new SilumgarsCommand(this);
    }
}
