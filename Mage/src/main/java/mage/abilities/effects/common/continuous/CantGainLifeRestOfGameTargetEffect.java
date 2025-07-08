package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

import java.util.List;

/**
 * @author Susucr
 */
public class CantGainLifeRestOfGameTargetEffect extends ContinuousEffectImpl {

    public CantGainLifeRestOfGameTargetEffect() {
        super(Duration.EndOfGame, Layer.PlayerEffects, SubLayer.NA, Outcome.Neutral);
        staticText = "that player can't gain life for the rest of the game";
    }

    private CantGainLifeRestOfGameTargetEffect(final CantGainLifeRestOfGameTargetEffect effect) {
        super(effect);
    }

    @Override
    public CantGainLifeRestOfGameTargetEffect copy() {
        return new CantGainLifeRestOfGameTargetEffect(this);
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Player) object).setCanGainLife(false);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            affectedObjects.add(player);
            return true;
        }
        return false;
    }
}
