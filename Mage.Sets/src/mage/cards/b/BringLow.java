
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BringLow extends CardImpl {

    public BringLow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{R}");


        // Bring Low deals 3 damage to target creature. If that creature has a +1/+1 counter on it, Bring Low deals 5 damage to it instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DamageTargetEffect(5),
                new DamageTargetEffect(3),
                new TargetHasCounterCondition(CounterType.P1P1),
                "{this} deals 3 damage to target creature. If that creature has a +1/+1 counter on it, Bring Low deals 5 damage to it instead"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BringLow(final BringLow card) {
        super(card);
    }

    @Override
    public BringLow copy() {
        return new BringLow(this);
    }
}

class TargetHasCounterCondition implements Condition {

    private final CounterType counterType;

    public TargetHasCounterCondition(CounterType counterType) {
        this.counterType = counterType;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!source.getTargets().isEmpty()) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                return permanent.getCounters(game).containsKey(counterType);
            }
        }
        return false;
    }
}
