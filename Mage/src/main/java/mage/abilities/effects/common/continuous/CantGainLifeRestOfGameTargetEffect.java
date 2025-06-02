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

import java.util.Collections;
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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Player)) {
                continue;
            }
            ((Player) object).setCanGainLife(false);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Player targetPlayer = game.getPlayer(game.getActivePlayerId());
        return targetPlayer != null ? Collections.singletonList(targetPlayer) : Collections.emptyList();
    }

    @Override
    public CantGainLifeRestOfGameTargetEffect copy() {
        return new CantGainLifeRestOfGameTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.setCanGainLife(false);
        }
        return true;
    }
}
