package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 *
 *
 * @author jeffwadsworth
 *
 *
 *
 */
public final class BlowflyInfestation extends CardImpl {

    private static final String rule = "Whenever a creature dies, if it had a -1/-1 counter on it, put a -1/-1 counter on target creature.";

    public BlowflyInfestation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        //Whenever a creature dies, if it had a -1/-1 counter on it, put a -1/-1 counter on target creature.
        Effect effect = new BlowflyInfestationEffect();
        TriggeredAbility triggeredAbility = new DiesCreatureTriggeredAbility(effect, false, false, true);
        triggeredAbility.addTarget(new TargetCreaturePermanent());
        Condition condition = new BlowflyInfestationCondition();
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(triggeredAbility, condition, rule));

    }

    private BlowflyInfestation(final BlowflyInfestation card) {
        super(card);
    }

    @Override
    public BlowflyInfestation copy() {
        return new BlowflyInfestation(this);
    }
}

class BlowflyInfestationCondition implements Condition {

    private Permanent permanent;

    @Override
    public boolean apply(Game game, Ability source) {
        for (Effect effect : source.getEffects()) {
            if (effect.getTargetPointer().getFirst(game, source) != null) {
                permanent = effect.getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            }
        }
        if (permanent != null) {
            return permanent.getCounters(game).containsKey(CounterType.M1M1);
        }
        return false;
    }
}

class BlowflyInfestationEffect extends OneShotEffect {

    public BlowflyInfestationEffect() {
        super(Outcome.Detriment);
    }

    private BlowflyInfestationEffect(final BlowflyInfestationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            creature.addCounters(CounterType.M1M1.createInstance(), source.getControllerId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public BlowflyInfestationEffect copy() {
        return new BlowflyInfestationEffect(this);
    }
}
