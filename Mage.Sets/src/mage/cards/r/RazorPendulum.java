package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazorPendulum extends CardImpl {

    public RazorPendulum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");


        // At the beginning of each player’s end step, if that player has 5 or less life, Razor Pendulum deals 2 damage to that player.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfEndStepTriggeredAbility(
                        new RazorPendulumEffect(), TargetController.ANY, false
                ), RazorPendulumCondition.instance, "At the beginning of each player's end step, " +
                "if that player has 5 or less life, {this} deals 2 damage to that player."
        ));
    }

    private RazorPendulum(final RazorPendulum card) {
        super(card);
    }

    @Override
    public RazorPendulum copy() {
        return new RazorPendulum(this);
    }
}

class RazorPendulumEffect extends OneShotEffect {

    RazorPendulumEffect() {
        super(Outcome.Benefit);
    }

    private RazorPendulumEffect(final RazorPendulumEffect effect) {
        super(effect);
    }

    @Override
    public RazorPendulumEffect copy() {
        return new RazorPendulumEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        return player.damage(2, source.getSourceId(), source, game) > 0;
    }
}

enum RazorPendulumCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(game.getActivePlayerId());
        if (player == null) {
            return false;
        }
        return player.getLife() < 6;
    }
}
