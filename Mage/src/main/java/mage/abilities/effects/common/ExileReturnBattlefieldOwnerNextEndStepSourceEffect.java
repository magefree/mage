
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class ExileReturnBattlefieldOwnerNextEndStepSourceEffect extends OneShotEffect {

    private boolean returnAlways;
    private boolean returnTapped;

    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect() {
        this(false);
    }

    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect(boolean returnAlways) {
        this(returnAlways, false);
    }

    /**
     *
     * @param returnAlways Return the permanent also if it does not go to exile
     * but is moved to another zone (e.g. command zone by commander replacement
     * effect)
     * @param returnTapped Does the source return tapped to the battlefield
     */
    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect(boolean returnAlways, boolean returnTapped) {
        super(Outcome.Benefit);
        this.returnTapped = returnTapped;
        this.returnAlways = returnAlways;
        staticText = "exile {this}. Return it to the battlefield "
                + (returnTapped ? "tapped " : "")
                + "under its owner's control at the beginning of the next end step";
    }

    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect(ExileReturnBattlefieldOwnerNextEndStepSourceEffect effect) {
        super(effect);
        this.returnAlways = effect.returnAlways;
        this.returnTapped = effect.returnTapped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                int zcc = game.getState().getZoneChangeCounter(permanent.getId());
                boolean exiled = controller.moveCardToExileWithInfo(permanent, source.getSourceId(), permanent.getIdName(), source, game, Zone.BATTLEFIELD, true);
                if (exiled || (returnAlways && (zcc == game.getState().getZoneChangeCounter(permanent.getId()) - 1))) {
                    //create delayed triggered ability and return it from every public zone it was next moved to
                    AtTheBeginOfNextEndStepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                            new ReturnToBattlefieldUnderOwnerControlSourceEffect(returnTapped, zcc + 1));
                    game.addDelayedTriggeredAbility(delayedAbility, source);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect copy() {
        return new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(this);
    }

}
