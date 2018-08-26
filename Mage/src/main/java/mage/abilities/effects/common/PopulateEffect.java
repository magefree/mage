
package mage.abilities.effects.common;

import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
//
//    701.27. Populate
//
//    701.27a To populate means to choose a creature token you control and put a
//    token onto the battlefield that's a copy of that creature token.
//
//    701.27b If you control no creature tokens when instructed to populate, you
//    won't put a token onto the battlefield.
//
public class PopulateEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent("token for populate");

    static {
        filter.add(new TokenPredicate());
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PopulateEffect() {
        this("");
    }

    public PopulateEffect(String prefixText) {
        super(Outcome.Copy);
        this.staticText = (!prefixText.isEmpty() ? prefixText + " p" : "P") + "opulate <i>(Create a token that's a copy of a creature token you control.)</i>";
    }

    public PopulateEffect(final PopulateEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Target target = new TargetPermanent(filter);
            target.setNotTarget(true);
            if (target.canChoose(source.getControllerId(), game)) {
                player.choose(Outcome.Copy, target, source.getSourceId(), game);
                Permanent tokenToCopy = game.getPermanent(target.getFirstTarget());
                if (tokenToCopy != null) {
                    if (!game.isSimulation()) {
                        game.informPlayers("Token selected for populate: " + tokenToCopy.getLogName());
                    }
                    Effect effect = new CreateTokenCopyTargetEffect();
                    effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
                    return effect.apply(game, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public PopulateEffect copy() {
        return new PopulateEffect(this);
    }
}
