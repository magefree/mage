package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceHasCounterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.AngelToken;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class LuminarchAscension extends CardImpl {

    private static final Condition condition = new SourceHasCounterCondition(CounterType.QUEST, 4);

    public LuminarchAscension(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // At the beginning of each opponent's end step, if you didn't lose life this turn, you may put a quest counter on Luminarch Ascension.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.OPPONENT,
                new AddCountersSourceEffect(CounterType.QUEST.createInstance()),
                true, LuminarchAscensionCondition.instance
        ));

        // {1}{W}: Create a 4/4 white Angel creature token with flying. Activate this ability only if Luminarch Ascension has four or more quest counters on it.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                new CreateTokenEffect(new AngelToken()), new ManaCostsImpl<>("{1}{W}"), condition
        ));
    }

    private LuminarchAscension(final LuminarchAscension card) {
        super(card);
    }

    @Override
    public LuminarchAscension copy() {
        return new LuminarchAscension(this);
    }
}

enum LuminarchAscensionCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(PlayerLostLifeWatcher.class)
                .getLifeLost(source.getControllerId()) == 0;
    }

    @Override
    public String toString() {
        return "you didn't lose life this turn";
    }
}
