package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PrimoTheIndivisibleToken;
import mage.game.permanent.token.Token;
import mage.util.CardUtil;
import mage.watchers.common.LandfallWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZimoneAllQuestioning extends CardImpl {

    public ZimoneAllQuestioning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // At the beginning of your end step, if a land entered the battlefield under your control this turn and you control a prime number of lands, create Primo, the Indivisible, a legendary 0/0 green and blue Fractal creature token, then put that many +1/+1 counters on it.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new ZimoneAllQuestioningEffect(), TargetController.YOU,
                ZimoneAllQuestioningCondition.instance, false
        ), new LandfallWatcher());
    }

    private ZimoneAllQuestioning(final ZimoneAllQuestioning card) {
        super(card);
    }

    @Override
    public ZimoneAllQuestioning copy() {
        return new ZimoneAllQuestioning(this);
    }
}

enum ZimoneAllQuestioningCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(LandfallWatcher.class)
                .landPlayed(source.getControllerId())
                && CardUtil.isPrime(game
                .getBattlefield()
                .count(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), source, game));
    }

    @Override
    public String toString() {
        return "a land entered the battlefield under your control this turn and you control a prime number of lands";
    }
}

class ZimoneAllQuestioningEffect extends OneShotEffect {

    ZimoneAllQuestioningEffect() {
        super(Outcome.Benefit);
        staticText = "create Primo, the Indivisible, a legendary 0/0 green and blue " +
                "Fractal creature token, then put that many +1/+1 counters on it";
    }

    private ZimoneAllQuestioningEffect(final ZimoneAllQuestioningEffect effect) {
        super(effect);
    }

    @Override
    public ZimoneAllQuestioningEffect copy() {
        return new ZimoneAllQuestioningEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new PrimoTheIndivisibleToken();
        token.putOntoBattlefield(1, game, source);
        int count = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND,
                source.getControllerId(), source, game
        );
        if (count < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent != null) {
                permanent.addCounters(CounterType.P1P1.createInstance(count), source, game);
            }
        }
        return true;
    }
}
