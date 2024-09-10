
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.watchers.common.PlayerLostLifeWatcher;

/**
 *
 * @author LevelX2
 */
public final class WoundReflection extends CardImpl {

    public WoundReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{5}{B}");


        // At the beginning of each end step, each opponent loses life equal to the life they lost this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new WoundReflectionEffect(), TargetController.ANY, false));
    }

    private WoundReflection(final WoundReflection card) {
        super(card);
    }

    @Override
    public WoundReflection copy() {
        return new WoundReflection(this);
    }
}

class WoundReflectionEffect extends OneShotEffect {
    
    public WoundReflectionEffect() {
        super(Outcome.LoseLife);
        this.staticText = "each opponent loses life equal to the life they lost this turn";
    }
    
    private WoundReflectionEffect(final WoundReflectionEffect effect) {
        super(effect);
    }
    
    @Override
    public WoundReflectionEffect copy() {
        return new WoundReflectionEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        PlayerLostLifeWatcher watcher = game.getState().getWatcher(PlayerLostLifeWatcher.class);
        if (controller != null && watcher != null) {
            for (UUID playerId : game.getOpponents(controller.getId())) {
                Player opponent =  game.getPlayer(playerId);
                if (opponent != null) {
                    int lifeLost = watcher.getLifeLost(playerId);
                    if (lifeLost > 0) {
                        opponent.loseLife(lifeLost, game, source, false);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
