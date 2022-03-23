
package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Quercitron
 */
public class RevealHandTargetEffect extends OneShotEffect {

    private final TargetController targetController;

    public RevealHandTargetEffect() {
        this(TargetController.OPPONENT);
    }

    public RevealHandTargetEffect(TargetController targetController) {
        super(Outcome.Discard);
        this.targetController = targetController;
        this.staticText = getText();
    }

    public RevealHandTargetEffect(final RevealHandTargetEffect effect) {
        super(effect);
        this.targetController = effect.targetController;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            player.revealCards(sourceObject.getIdName(), player.getHand(), game);
            return true;
        }
        return false;
    }

    @Override
    public RevealHandTargetEffect copy() {
        return new RevealHandTargetEffect(this);
    }

    private String getText() {
        StringBuilder sb = new StringBuilder("Target ");
        switch (targetController) {
            case OPPONENT:
                sb.append("opponent");
                break;
            case ANY:
                sb.append("player");
                break;
            default:
                break;
        }
        sb.append(" reveals their hand");
        return sb.toString();
    }
}
