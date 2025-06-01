package mage.abilities.effects.common.combat;

import mage.MageObject;
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

import java.util.ArrayList;
import java.util.List;
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

    UUID goadingPlayerId;

    public GoadTargetEffect() {
        this(Duration.UntilYourNextTurn);
    }

    public GoadTargetEffect(Duration duration) {
        super(duration, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
    }

    protected GoadTargetEffect(final GoadTargetEffect effect) {
        super(effect);
        this.goadingPlayerId = effect.goadingPlayerId;
    }

    @Override
    public GoadTargetEffect copy() {
        return new GoadTargetEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addGoadingPlayer(this.goadingPlayerId);
        }
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return;
        }
        this.goadingPlayerId = controller.getId();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent targetCreature = game.getPermanent(targetId);
            if (targetCreature != null) {
                game.informPlayers(controller.getLogName() + " is goading " + targetCreature.getLogName());
            }
        }
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        List<MageObject> objects = new ArrayList<>();
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            Permanent targetCreature = game.getPermanent(targetId);
            if (targetCreature != null) {
                objects.add(targetCreature);
            }
        }
        return objects;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "goad " + getTargetPointer().describeTargets(mode.getTargets(), "that creature")
                + ". <i>(Until your next turn, that creature attacks each combat if able and attacks a player other than you if able.)</i>";
    }
}
