package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.choices.ChoiceColor;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX
 */
public class BecomesColorTargetEffect extends ContinuousEffectImpl {

    private ObjectColor setColor;
    private final boolean retainColor;

    /**
     * Set the color of a spell or permanent
     */
    public BecomesColorTargetEffect(Duration duration) {
        this(null, duration);
    }

    public BecomesColorTargetEffect(ObjectColor setColor, Duration duration) {
        this(setColor, false, duration);
    }

    public BecomesColorTargetEffect(ObjectColor setColor, boolean retainColor, Duration duration) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setColor = setColor;
        this.retainColor = retainColor;
    }

    protected BecomesColorTargetEffect(final BecomesColorTargetEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
        this.retainColor = effect.retainColor;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);

        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            discard();
            return;
        }
        if (setColor == null) {
            ChoiceColor choice = new ChoiceColor();
            if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
                discard();
                return;
            }
            setColor = choice.getColor();
            if (!game.isSimulation()) {
                game.informPlayers(controller.getLogName() + " has chosen the color: " + setColor.toString());
            }
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            MageObject mageObject = game.getObject(targetId);
            if (mageObject instanceof Spell || mageObject instanceof Permanent) {
                affectedObjects.add(mageObject);
            }
        }
        if (affectedObjects.isEmpty()) {
            if (duration == Duration.Custom) {
                discard();
            }
            return false;
        }
        return true;
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            MageObject mageObject = (MageObject) object;
            if (retainColor) {
                mageObject.getColor(game).addColor(setColor);
            } else {
                mageObject.getColor(game).setColor(setColor);
            }
        }
    }

    @Override
    public BecomesColorTargetEffect copy() {
        return new BecomesColorTargetEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder(getTargetPointer().describeTargets(mode.getTargets(), "it"));
        sb.append(getTargetPointer().isPlural(mode.getTargets()) ? " become " : " becomes ");
        if (setColor == null) {
            sb.append("the color of your choice");
        } else {
            sb.append(setColor.getDescription());
        }
        if (retainColor) {
            sb.append(" in addition to its other colors");
        }
        if (!duration.toString().isEmpty()) {
            sb.append(' ').append(duration.toString());
        }
        return sb.toString();
    }
}
