package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.CastFromEverywhereSourceCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author ciaconna007
 */
public final class NineLivesFamiliar extends CardImpl {

    public NineLivesFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.CAT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // This creature enters with eight revival counters on it if you cast it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.REVIVAL.createInstance(8)),
                CastFromEverywhereSourceCondition.instance, null,
                "with eight revival counters on it if you cast it"
        ));

        // When this creature dies, if it had a revival counter on it, return it to the battlefield with one fewer revival counter on it at the beginning of the next end step.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new NineLivesFamiliarEffect())
        ).setText("return it to the battlefield with one fewer revival counter " +
                "on it at the beginning of the next end step")).withInterveningIf(NineLivesFamiliarCondition.instance));
    }

    private NineLivesFamiliar(final NineLivesFamiliar card) {
        super(card);
    }

    @Override
    public NineLivesFamiliar copy() {
        return new NineLivesFamiliar(this);
    }
}

enum NineLivesFamiliarCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return CardUtil
                .getEffectValueFromAbility(source, "permanentLeftBattlefield", Permanent.class)
                .filter(permanent -> permanent.getCounters(game).getCount(CounterType.REVIVAL) > 0)
                .isPresent();
    }

    @Override
    public String toString() {
        return "it had a revival counter on it";
    }
}

class NineLivesFamiliarEffect extends OneShotEffect {

    NineLivesFamiliarEffect() {
        super(Outcome.Benefit);
        staticText = "return it to the battlefield with one fewer revival counter on it";
    }

    private NineLivesFamiliarEffect(final NineLivesFamiliarEffect effect) {
        super(effect);
    }

    @Override
    public NineLivesFamiliarEffect copy() {
        return new NineLivesFamiliarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentLeftBattlefield");
        if (permanent == null) {
            return false;
        }
        final int counters = permanent.getCounters(game).getCount(CounterType.REVIVAL) - 1;
        ReturnSourceFromGraveyardToBattlefieldWithCounterEffect effect = new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(CounterType.REVIVAL.createInstance(counters), false);
        effect.setText(staticText);
        effect.apply(game, source);
        return true;
    }
}
