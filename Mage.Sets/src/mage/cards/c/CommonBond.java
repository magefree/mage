
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;


/**
 *
 * @author LevelX2
 */
public final class CommonBond extends CardImpl {

    public CommonBond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{G}{W}");


        // Put a +1/+1 counter on target creature. Put a +1/+1 counter on target creature.
        this.getSpellAbility().addEffect(new CommonBondEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("first creature")));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(new FilterCreaturePermanent("second creature (can be the same as the first)")));
    }

    private CommonBond(final CommonBond card) {
        super(card);
    }

    @Override
    public CommonBond copy() {
        return new CommonBond(this);
    }
}

class CommonBondEffect extends OneShotEffect {

    public CommonBondEffect() {
        super(Outcome.BoostCreature);
        staticText = "Put a +1/+1 counter on target creature. Put a +1/+1 counter on target creature.";
    }

    public CommonBondEffect(final CommonBondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int affectedTargets = 0;
        Permanent permanent = game.getPermanent(source.getTargets().get(0).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
            affectedTargets ++;
        }
        permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        if (permanent != null) {
            permanent.addCounters(CounterType.P1P1.createInstance(1), source.getControllerId(), source, game);
            affectedTargets ++;
        }
        return affectedTargets > 0;
    }

    @Override
    public CommonBondEffect copy() {
        return new CommonBondEffect(this);
    }
}
