package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.token.BatToken;
import mage.watchers.common.PlayerGainedLifeWatcher;
import mage.watchers.common.PlayerLostLifeWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LunarConvocation extends CardImpl {

    public LunarConvocation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}{B}");

        // At the beginning of your end step, if you gained life this turn, each opponent loses 1 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new LoseLifeOpponentsEffect(1),
                false, LunarConvocationCondition.GAINED
        ));

        // At the beginning of your end step, if you gained and lost life this turn, create a 1/1 black Bat creature token with flying.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new CreateTokenEffect(new BatToken()),
                false, LunarConvocationCondition.GAINED_AND_LOST
        ));

        // {1}{B}, Pay 2 life: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new PayLifeCost(2));
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.addAbility(ability);
    }

    private LunarConvocation(final LunarConvocation card) {
        super(card);
    }

    @Override
    public LunarConvocation copy() {
        return new LunarConvocation(this);
    }
}

enum LunarConvocationCondition implements Condition {
    GAINED(false, "you gained life this turn"),
    GAINED_AND_LOST(true, "you gained and lost life this turn");
    private final boolean andLost;
    private final String message;

    LunarConvocationCondition(boolean andLost, String message) {
        this.andLost = andLost;
        this.message = message;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getState()
                .getWatcher(PlayerGainedLifeWatcher.class)
                .getLifeGained(source.getControllerId()) > 0
                && (!andLost || game
                .getState()
                .getWatcher(PlayerLostLifeWatcher.class)
                .getLifeLost(source.getControllerId()) > 0);
    }

    @Override
    public String toString() {
        return message;
    }
}
