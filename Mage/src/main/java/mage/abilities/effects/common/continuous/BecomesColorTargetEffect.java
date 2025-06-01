package mage.abilities.effects.common.continuous;

import java.util.List;
import java.util.stream.Collectors;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        if (objects.isEmpty() && this.duration == Duration.Custom) {
            this.discard();
            return false;
        }
        for (MageObject object : objects) {
            if (!(object instanceof Spell) && !(object instanceof Permanent)) {
                continue;
            }
            if (retainColor) {
                object.getColor(game).addColor(setColor);
            } else {
                object.getColor(game).setColor(setColor);
            }
        }
        return true;
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
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return getTargetPointer().getTargets(game, source)
                .stream()
                .map(game::getObject)
                .filter(obj -> obj instanceof Permanent || obj instanceof Spell)
                .collect(Collectors.toList());
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
