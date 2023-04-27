package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class ChangeMaxNumberThatCanAttackSourceEffect extends ContinuousEffectImpl {

    private final int maxAttackedBy;

    public ChangeMaxNumberThatCanAttackSourceEffect(int maxAttackedBy) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.maxAttackedBy = maxAttackedBy;
        staticText = "No more than " + (maxAttackedBy == 1 ? "one" : "two") + " creatures can attack you each combat";
    }

    public ChangeMaxNumberThatCanAttackSourceEffect(final ChangeMaxNumberThatCanAttackSourceEffect effect) {
        super(effect);
        this.maxAttackedBy = effect.maxAttackedBy;
    }

    @Override
    public ChangeMaxNumberThatCanAttackSourceEffect copy() {
        return new ChangeMaxNumberThatCanAttackSourceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer != Layer.RulesEffects) {
            return false;
        }

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Change the rule
        if (controller.getMaxAttackedBy()> maxAttackedBy) {
            controller.setMaxAttackedBy(maxAttackedBy);
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
