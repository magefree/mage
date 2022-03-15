package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX2
 */
public class ExileReturnBattlefieldOwnerNextEndStepSourceEffect extends OneShotEffect {

    private final boolean returnTapped;

    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect() {
        this(false);
    }

    /**
     * @param returnTapped Does the source return tapped to the battlefield
     */
    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect(boolean returnTapped) {
        super(Outcome.Benefit);
        this.returnTapped = returnTapped;
        staticText = "exile {this}. Return it to the battlefield " + (returnTapped ? "tapped " : "") +
                "under its owner's control at the beginning of the next end step";
    }

    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect(ExileReturnBattlefieldOwnerNextEndStepSourceEffect effect) {
        super(effect);
        this.returnTapped = effect.returnTapped;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return true;
        }
        controller.moveCards(permanent, Zone.EXILED, source, game);
        //create delayed triggered ability and return it from every public zone it was next moved to
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(returnTapped, true)
                        .setTargetPointer(new FixedTarget(permanent.getId(), game))
        ), source);
        return true;
    }

    @Override
    public ExileReturnBattlefieldOwnerNextEndStepSourceEffect copy() {
        return new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(this);
    }
}
