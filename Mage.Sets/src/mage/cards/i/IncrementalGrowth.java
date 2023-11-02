package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IncrementalGrowth extends CardImpl {

    public IncrementalGrowth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");

        // Put a +1/+1 counter on target creature, two +1/+1 counters on another target 
        // creature, and three +1/+1 counters on a third target creature.
        this.getSpellAbility().addEffect(new IncrementalGrowthEffect());

        FilterCreaturePermanent filter1 = new FilterCreaturePermanent("creature (gets a +1/+1 counter)");
        TargetCreaturePermanent target1 = new TargetCreaturePermanent(filter1);
        target1.setTargetTag(1);
        this.getSpellAbility().addTarget(target1);

        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("another creature (gets two +1/+1 counter)");
        filter2.add(new AnotherTargetPredicate(2));
        TargetCreaturePermanent target2 = new TargetCreaturePermanent(filter2);
        target2.setTargetTag(2);
        this.getSpellAbility().addTarget(target2);

        FilterCreaturePermanent filter3 = new FilterCreaturePermanent("another creature (gets three +1/+1 counters)");
        filter3.add(new AnotherTargetPredicate(3));
        TargetCreaturePermanent target3 = new TargetCreaturePermanent(filter3);
        target3.setTargetTag(3);
        this.getSpellAbility().addTarget(target3);
    }

    private IncrementalGrowth(final IncrementalGrowth card) {
        super(card);
    }

    @Override
    public IncrementalGrowth copy() {
        return new IncrementalGrowth(this);
    }
}

class IncrementalGrowthEffect extends OneShotEffect {

    public IncrementalGrowthEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put a +1/+1 counter on target creature, "
                + "two +1/+1 counters on another target creature, "
                + "and three +1/+1 counters on a third target creature";
    }

    private IncrementalGrowthEffect(final IncrementalGrowthEffect effect) {
        super(effect);
    }

    @Override
    public IncrementalGrowthEffect copy() {
        return new IncrementalGrowthEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int i = 0;
        for (Target target : source.getTargets()) {
            i++;
            Permanent creature = game.getPermanent(target.getFirstTarget());
            if (creature != null) {
                creature.addCounters(CounterType.P1P1.createInstance(i), source.getControllerId(), source, game);
            }
        }
        return false;
    }
}
