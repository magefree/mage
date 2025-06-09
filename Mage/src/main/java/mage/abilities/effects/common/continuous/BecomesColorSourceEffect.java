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
import mage.players.Player;

import java.util.List;

/**
 * @author LoneFox
 */
public class BecomesColorSourceEffect extends ContinuousEffectImpl {

    private ObjectColor setColor;

    public BecomesColorSourceEffect(Duration duration) {
        this(null, duration);
    }

    public BecomesColorSourceEffect(ObjectColor setColor, Duration duration) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.setColor = setColor;
    }

    protected BecomesColorSourceEffect(final BecomesColorSourceEffect effect) {
        super(effect);
        this.setColor = effect.setColor;
    }

    @Override
    public BecomesColorSourceEffect copy() {
        return new BecomesColorSourceEffect(this);
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
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            affectedObjects.add(mageObject);
        } else {
            this.discard();
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((MageObject) object).getColor(game).setColor(setColor);
        }
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} becomes " + (setColor == null ? "the color of your choice" : setColor.getDescription())
                + (duration.toString().isEmpty() ? "" : " " + duration.toString());
    }
}
