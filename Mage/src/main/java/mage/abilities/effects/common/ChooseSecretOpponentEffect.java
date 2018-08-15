package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;
/**
 *
 * @author LevelX2
 * @author credman0
 */
public class ChooseSecretOpponentEffect extends OneShotEffect {

    public static final String SECRET_OPPONENT = "_secOpp";
    public static final String SECRET_OWNER = "_secOwn";

        public ChooseSecretOpponentEffect() {
            super(Outcome.Neutral);
            staticText = "secretly choose an opponent";
        }

        public ChooseSecretOpponentEffect(final ChooseSecretOpponentEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            MageObject mageObject = game.getPermanentEntering(source.getSourceId());
            if (mageObject == null) {
                mageObject = game.getObject(source.getSourceId());
            }
            if (controller != null && mageObject != null) {
                TargetOpponent targetOpponent = new TargetOpponent();
                targetOpponent.setTargetName("opponent (secretly)");
                while (!controller.choose(outcome, targetOpponent, source.getSourceId(), game)) {
                    if (!controller.canRespond()) {
                        return false;
                    }
                }
                if (targetOpponent.getTargets().isEmpty()) {
                    return false;
                }
                if (!game.isSimulation()) {
                    game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has secretly chosen an opponent.");
                }
                game.getState().setValue(mageObject.getId() + SECRET_OPPONENT, targetOpponent.getTargets().get(0));
                game.getState().setValue(mageObject.getId() + SECRET_OWNER, controller.getId());
                if (mageObject instanceof Permanent) {
                    ((Permanent) mageObject).addInfo(SECRET_OPPONENT,
                            CardUtil.addToolTipMarkTags(controller.getLogName() + " has secretly chosen an opponent."), game);
                }
            }
            return false;
        }

        @Override
        public ChooseSecretOpponentEffect copy() {
            return new ChooseSecretOpponentEffect(this);
        }


}
