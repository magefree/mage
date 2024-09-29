package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 * @author Loki
 */
public class FlipSourceEffect extends OneShotEffect {

    private final Token flipToken;

    public FlipSourceEffect(Token flipToken) {
        super(Outcome.BecomeCreature);
        this.flipToken = flipToken;
        staticText = "flip {this}";
    }

    protected FlipSourceEffect(final FlipSourceEffect effect) {
        super(effect);
        this.flipToken = effect.flipToken;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        Player controller = game.getPlayer(source.getControllerId());
        if (permanent != null && controller != null) {
            if (permanent.flip(game)) {
                ContinuousEffect effect = new ConditionalContinuousEffect(new CopyTokenEffect(flipToken), FlippedCondition.instance, "");
                game.addEffect(effect, source);
                if (!game.isSimulation()) {
                    game.informPlayers(new StringBuilder(controller.getLogName()).append(" flips ").append(permanent.getName()).toString());
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public FlipSourceEffect copy() {
        return new FlipSourceEffect(this);
    }

}
