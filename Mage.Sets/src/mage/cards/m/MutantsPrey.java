

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.CounterPredicate;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class MutantsPrey extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature you control with a +1/+1 counter on it");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter1.add(new ControllerPredicate(TargetController.YOU));
        filter1.add(new CounterPredicate(CounterType.P1P1));
        filter2.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public MutantsPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Target creature you control with a +1/+1 counter on it fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter2));
    }

    public MutantsPrey(final MutantsPrey card) {
        super(card);
    }

    @Override
    public MutantsPrey copy() {
        return new MutantsPrey(this);
    }

}
