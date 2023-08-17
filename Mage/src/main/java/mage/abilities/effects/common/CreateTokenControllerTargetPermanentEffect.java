package mage.abilities.effects.common;


import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @Author Susucr
 * <p>
 * Have the Controller of target permanent (or LKI controller) create a single Token.
 * <p>
 * For now a single token is created, without any special attribute.
 * Could be extended to do more like CreateTokenEffect in the future.
 */
public class CreateTokenControllerTargetPermanentEffect extends OneShotEffect {
    private final Token token;

    public CreateTokenControllerTargetPermanentEffect(Token token) {
        super(Outcome.Neutral);
        this.token = token;
        this.staticText = setText();
    }

    protected CreateTokenControllerTargetPermanentEffect(final CreateTokenControllerTargetPermanentEffect effect) {
        super(effect);
        this.token = effect.token;
    }

    @Override
    public CreateTokenControllerTargetPermanentEffect copy() {
        return new CreateTokenControllerTargetPermanentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) game.getLastKnownInformation(this.getTargetPointer().getFirst(game, source), Zone.BATTLEFIELD);
        if (creature != null) {
            Player controllerOfTarget = game.getPlayer(creature.getControllerId());
            if (controllerOfTarget != null) {
                return token.putOntoBattlefield(1, game, source, controllerOfTarget.getId());
            }
        }
        return false;
    }

    private String setText() {
        return "Its controller creates " + CardUtil.addArticle(token.getDescription());
    }
}
