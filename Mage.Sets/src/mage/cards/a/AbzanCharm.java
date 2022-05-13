
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetCreaturePermanentAmount;

/**
 *
 * @author LevelX2
 */
public final class AbzanCharm extends CardImpl {

    private static final FilterCreaturePermanent FILTER = new FilterCreaturePermanent("creature with power 3 or greater");

    static {
        FILTER.add(new PowerPredicate(ComparisonType.MORE_THAN, 2));
    }

    public AbzanCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{B}{G}");

        // Choose one -
        // *Exile target creature with power 3 or greater
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(FILTER));
        this.getSpellAbility().addEffect(new ExileTargetEffect());

        // *You draw two cards and you lose 2 life
        Mode mode = new Mode(new DrawCardSourceControllerEffect(2));
        mode.addEffect(new LoseLifeSourceControllerEffect(2));
        this.getSpellAbility().addMode(mode);

        // *Distribute two +1/+1 counters among one or two target creatures.
        mode = new Mode(new DistributeCountersEffect(CounterType.P1P1, 2, false, "one or two target creatures"));
        mode.addTarget(new TargetCreaturePermanentAmount(2));
        this.getSpellAbility().addMode(mode);

    }

    private AbzanCharm(final AbzanCharm card) {
        super(card);
    }

    @Override
    public AbzanCharm copy() {
        return new AbzanCharm(this);
    }
}
