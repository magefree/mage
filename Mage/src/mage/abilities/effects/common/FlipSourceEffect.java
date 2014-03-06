package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.condition.common.FlippedCondition;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;


/**
 * @author Loki
 */
public class FlipSourceEffect extends OneShotEffect<FlipSourceEffect> {

    private final Token flipToken;

    public FlipSourceEffect(Token flipToken) {
        super(Outcome.BecomeCreature);
        this.flipToken = flipToken;
        staticText = "flip it";
    }

    public FlipSourceEffect(final FlipSourceEffect effect) {
        super(effect);
        this.flipToken = effect.flipToken;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (permanent.flip(game)) {
                ContinuousEffect effect = new ConditionalContinousEffect(new CopyTokenEffect(flipToken), FlippedCondition.getInstance(), "");
                game.addEffect(effect, source);
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
