package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

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
    public ChangeMaxNumberThatCanAttackSourceEffect copy() {
        return new ChangeMaxNumberThatCanAttackSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Change the rule
        if (controller.getMaxAttackedBy() > maxAttackedBy) {
            controller.setMaxAttackedBy(maxAttackedBy);
        }
        return true;
    }
}
