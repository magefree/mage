package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmosElixir extends CardImpl {

    public CosmosElixir(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your end step, draw a card if your life total is greater than your starting life total. Otherwise, you gain 2 life.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new ConditionalOneShotEffect(
                new DrawCardSourceControllerEffect(1), new GainLifeEffect(2),
                CosmosElixirCondition.instance, "draw a card if your life total " +
                "is greater than your starting life total. Otherwise, you gain 2 life"
        ), TargetController.YOU, false));
    }

    private CosmosElixir(final CosmosElixir card) {
        super(card);
    }

    @Override
    public CosmosElixir copy() {
        return new CosmosElixir(this);
    }
}

enum CosmosElixirCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.getLife() > game.getStartingLife();
    }
}
