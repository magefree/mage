package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JRHerlehy Created on 4/8/17.
 */
public class AddCreatureTypeAdditionEffect extends ContinuousEffectImpl {

    private final SubType subType;
    private final boolean giveBlackColor;

    public AddCreatureTypeAdditionEffect(SubType subType, boolean giveBlackColor) {
        super(Duration.Custom, Outcome.Neutral);
        this.subType = subType;
        this.giveBlackColor = giveBlackColor;
        updateText();
    }

    protected AddCreatureTypeAdditionEffect(final AddCreatureTypeAdditionEffect effect) {
        super(effect);
        this.subType = effect.subType;
        this.giveBlackColor = effect.giveBlackColor;
    }

    private void updateText() {
        if (this.giveBlackColor) {
            this.staticText = "That creature is a black " + subType + " in addition to its other colors and types";
        } else {
            this.staticText = "That creature is a " + subType + " in addition to its other types";
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent creature;
        if (source.getTargets().getFirstTarget() == null) {
            creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        } else {
            creature = game.getPermanent(source.getTargets().getFirstTarget());
            if (creature == null) {
                creature = game.getPermanentEntering(source.getTargets().getFirstTarget());
            }
        }
        if (creature != null) {
            affectedObjects.add(creature);
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Permanent creature = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    creature.addSubType(game, subType);
                    break;
                case ColorChangingEffects_5:
                    if (this.giveBlackColor) {
                        creature.getColor(game).setBlack(true);
                    }
                    break;
            }
        }
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        List<MageItem> affectedObjects = new ArrayList<>();
        if (queryAffectedObjects(layer, source, game, affectedObjects)) {
            applyToObjects(layer, sublayer, source, game, affectedObjects);
            return true;
        }
        this.used = true;
        return false;
    }

    @Override
    public AddCreatureTypeAdditionEffect copy() {
        return new AddCreatureTypeAdditionEffect(this);
    }
}
