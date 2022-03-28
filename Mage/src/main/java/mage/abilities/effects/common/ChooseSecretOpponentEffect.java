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

import java.util.UUID;

/**
 * @author LevelX2
 * @author credman0
 * @author TheElk801
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
            mageObject = game.getObject(source);
        }
        if (controller == null || mageObject == null) {
            return false;
        }
        TargetOpponent targetOpponent = new TargetOpponent(true);
        targetOpponent.setTargetName("opponent (secretly)");
        controller.choose(outcome, targetOpponent, source, game);
        if (targetOpponent.getFirstTarget() == null) {
            return false;
        }
        game.informPlayers(mageObject.getName() + ": " + controller.getLogName() + " has secretly chosen an opponent.");
        setChosenPlayer(targetOpponent.getFirstTarget(), source, game);
        setSecretOwner(controller.getId(), source, game);
        if (!(mageObject instanceof Permanent)) {
            return true;
        }
        ((Permanent) mageObject).addInfo(
                SECRET_OPPONENT, CardUtil.addToolTipMarkTags(
                        controller.getLogName() + " has secretly chosen an opponent."
                ), game);
        return true;
    }

    public static void setChosenPlayer(UUID value, Ability source, Game game) {
        game.getState().setValue(getthing(source, game) + ChooseSecretOpponentEffect.SECRET_OPPONENT, value);
    }

    public static UUID getChosenPlayer(Ability source, Game game) {
        return (UUID) game.getState().getValue(getthing(source, game) + ChooseSecretOpponentEffect.SECRET_OPPONENT);
    }

    public static void setSecretOwner(UUID value, Ability source, Game game) {
        game.getState().setValue(getthing(source, game) + ChooseSecretOpponentEffect.SECRET_OWNER, value);
    }

    public static UUID getSecretOwner(Ability source, Game game) {
        return (UUID) game.getState().getValue(getthing(source, game) + ChooseSecretOpponentEffect.SECRET_OWNER);
    }

    private static String getthing(Ability source, Game game) {
        if (game.getPermanentEntering(source.getSourceId()) != null) { // Emissary of Grudges and Guardian Archon
            return "" + source.getSourceId() + '_' + (game.getPermanentEntering(source.getSourceId()).getZoneChangeCounter(game) + 1);
        }
        if (game.getPermanentOrLKIBattlefield(source.getSourceId()) != null) { // Stalking Leonin
        return "" + source.getSourceId() + '_' + (game.getPermanentOrLKIBattlefield(source.getSourceId()).getZoneChangeCounter(game));
        }
        return "Does not exist";
    }

    @Override
    public ChooseSecretOpponentEffect copy() {
        return new ChooseSecretOpponentEffect(this);
    }
}
