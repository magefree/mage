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
import mage.util.CardUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author LevelX2
 */
public class ChangeMaxNumberThatCanAttackSourceEffect extends ContinuousEffectImpl {

    private final int maxAttackedBy;

    public ChangeMaxNumberThatCanAttackSourceEffect(int maxAttackedBy) {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.maxAttackedBy = maxAttackedBy;
        staticText = "no more than " + CardUtil.numberToText(maxAttackedBy) +
                " creature" + (maxAttackedBy > 1 ? "s" : "") + " can attack you each combat";
    }

    protected ChangeMaxNumberThatCanAttackSourceEffect(final ChangeMaxNumberThatCanAttackSourceEffect effect) {
        super(effect);
        this.maxAttackedBy = effect.maxAttackedBy;
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
            Player player = (Player) object;
            if (player.getMaxAttackedBy() > maxAttackedBy) {
                player.setMaxAttackedBy(maxAttackedBy);
            }
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null ? Collections.singletonList(controller) : Collections.emptyList();
    }

    @Override
    public ChangeMaxNumberThatCanAttackSourceEffect copy() {
        return new ChangeMaxNumberThatCanAttackSourceEffect(this);
    }
}
