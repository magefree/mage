package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            this.discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            switch (layer) {
                case TypeChangingEffects_4:
                    permanent.addSubType(game, subType);
                    break;
                case ColorChangingEffects_5:
                    if (this.giveBlackColor) {
                        permanent.getColor(game).setBlack(true);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.ColorChangingEffects_5 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        UUID targetId = Optional.ofNullable(source.getTargets().getFirstTarget())
                .orElseGet(() -> getTargetPointer().getFirst(game, source));

        Permanent creature = Optional.ofNullable(game.getPermanent(targetId))
                .orElseGet(() -> game.getPermanentEntering(targetId));
        return creature != null ? Collections.singletonList(creature) : Collections.emptyList();
    }

    @Override
    public AddCreatureTypeAdditionEffect copy() {
        return new AddCreatureTypeAdditionEffect(this);
    }
}
