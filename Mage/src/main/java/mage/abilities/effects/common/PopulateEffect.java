
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
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

    private final boolean tappedAndAttacking;
    private static final FilterPermanent filter = new FilterPermanent("token for populate");

    static {
        filter.add(TokenPredicate.instance);
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PopulateEffect() {
        this("");
    }

    public PopulateEffect(String prefixText) {
        this(false);
        this.staticText = (!prefixText.isEmpty() ? prefixText + " p" : "P") + "opulate <i>(Create a token that's a copy of a creature token you control.)</i>";
    }

    public PopulateEffect(boolean tappedAndAttacking) {
        super(Outcome.Copy);
        this.tappedAndAttacking = tappedAndAttacking;
        this.staticText = "populate. The token enters the battlefield tapped and attacking. " +
                "<i>(To populate, create a token that's a copy of a creature token you control.)</i>";
    }

    public PopulateEffect(final PopulateEffect effect) {
        super(effect);
        this.tappedAndAttacking = effect.tappedAndAttacking;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Target target = new TargetPermanent(filter);
        target.setNotTarget(true);
        if (!target.canChoose(source.getControllerId(), game)) {
            return true;
        }
        player.choose(Outcome.Copy, target, source.getSourceId(), game);
        Permanent tokenToCopy = game.getPermanent(target.getFirstTarget());
        if (tokenToCopy == null) {
            return true;
        }
        game.informPlayers("Token selected for populate: " + tokenToCopy.getLogName());
        Effect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, tappedAndAttacking, tappedAndAttacking
        );
        effect.setTargetPointer(new FixedTarget(target.getFirstTarget()));
        return effect.apply(game, source);
    }

    @Override
    public PopulateEffect copy() {
        return new PopulateEffect(this);
    }
}
