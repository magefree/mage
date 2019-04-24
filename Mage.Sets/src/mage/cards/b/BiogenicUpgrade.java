package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.effects.common.counter.DistributeCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanentAmount;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BiogenicUpgrade extends CardImpl {

    public BiogenicUpgrade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{G}{G}");

        // Distribute three +1/+1 counters among one, two, or three target creatures, then double the number of +1/+1 counters on each of those creatures.
        this.getSpellAbility().addEffect(new DistributeCountersEffect(
                CounterType.P1P1, 3, false,
                "one, two, or three target creatures"
        ));
        this.getSpellAbility().addTarget(new TargetCreaturePermanentAmount(3));
        this.getSpellAbility().addEffect(new BiogenicUpgradeEffect());
    }

    private BiogenicUpgrade(final BiogenicUpgrade card) {
        super(card);
    }

    @Override
    public BiogenicUpgrade copy() {
        return new BiogenicUpgrade(this);
    }
}

class BiogenicUpgradeEffect extends OneShotEffect {

    BiogenicUpgradeEffect() {
        super(Outcome.Benefit);
        staticText = ", then double the number of +1/+1 counters on each of those creatures.";
    }

    private BiogenicUpgradeEffect(final BiogenicUpgradeEffect effect) {
        super(effect);
    }

    @Override
    public BiogenicUpgradeEffect copy() {
        return new BiogenicUpgradeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID permanentId : source.getTargets().get(0).getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent == null) {
                continue;
            }
            Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(
                    permanent.getCounters(game).getCount(CounterType.P1P1)
            ));
            effect.setTargetPointer(new FixedTarget(permanent, game));
            effect.apply(game, source);
        }
        return true;
    }
}