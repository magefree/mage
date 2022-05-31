package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class GoadTargetEffect extends ContinuousEffectImpl {

    /**
     * 701.36. Goad
     * <p>
     * 701.36a Certain spells and abilities can goad a creature. Until the next
     * turn of the controller of that spell or ability, that creature attacks
     * each combat if able and attacks a player other than that player if able.
     */
    public GoadTargetEffect() {
        super(Duration.UntilYourNextTurn, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
    }

    private GoadTargetEffect(final GoadTargetEffect effect) {
        super(effect);
    }

    @Override
    public GoadTargetEffect copy() {
        return new GoadTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent targetCreature = game.getPermanent(targetId);
            if (targetCreature != null) {
                game.informPlayers(controller.getLogName() + " is goading " + targetCreature.getLogName());
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent targetCreature = game.getPermanent(targetId);
            targetCreature.addGoadingPlayer(source.getControllerId());
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }

        return "goad target " + (!mode.getTargets().isEmpty() ? mode.getTargets().get(0).getTargetName() : " creature")
                + ". <i>(Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)</i>";
    }
}
