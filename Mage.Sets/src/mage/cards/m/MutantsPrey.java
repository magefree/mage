

package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.FightTargetsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */


public final class MutantsPrey extends CardImpl {

    private static final FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature you control with a +1/+1 counter on it");
    private static final FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature an opponent controls");
    static {
        filter1.add(TargetController.YOU.getControllerPredicate());
        filter1.add(CounterType.P1P1.getPredicate());
        filter2.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public MutantsPrey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        // Target creature you control with a +1/+1 counter on it fights target creature an opponent controls.
        this.getSpellAbility().addEffect(new FightTargetsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter2));
    }

    private MutantsPrey(final MutantsPrey card) {
        super(card);
    }

    @Override
    public MutantsPrey copy() {
        return new MutantsPrey(this);
    }

}
