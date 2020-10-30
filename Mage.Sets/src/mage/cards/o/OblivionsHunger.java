package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OblivionsHunger extends CardImpl {

    public OblivionsHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature you control gains indestructible until end of turn. Draw a card if that creature has a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ));
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), OblivionsHungerCondition.instance,
                "Draw a card if that creature has a +1/+1 counter on it"
        ));
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private OblivionsHunger(final OblivionsHunger card) {
        super(card);
    }

    @Override
    public OblivionsHunger copy() {
        return new OblivionsHunger(this);
    }
}

enum OblivionsHungerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        return permanent != null && permanent.getCounters(game).containsKey(CounterType.P1P1);
    }
}
