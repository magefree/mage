package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public class HasSubtypesSourceEffect extends ContinuousEffectImpl {

    private final List<SubType> subtypes = new ArrayList<>();

    public HasSubtypesSourceEffect(SubType... subTypes) {
        super(Duration.EndOfGame, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        subtypes.addAll(Arrays.asList(subTypes));
        this.staticText = setText();
    }

    public HasSubtypesSourceEffect(final HasSubtypesSourceEffect effect) {
        super(effect);
        this.subtypes.addAll(effect.subtypes);
    }

    @Override
    public HasSubtypesSourceEffect copy() {
        return new HasSubtypesSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject == null) {
            return false;
        }
        for (SubType subType : subtypes) {
            sourceObject.addSubType(game, subType);
        }
        return true;
    }

    private String setText() {
        String s = "{this} is also ";
        switch (subtypes.size()) {
            case 0:
                throw new UnsupportedOperationException("Can't have zero subtypes");
            case 1:
                s += subtypes.get(0).getIndefiniteArticle() + " " + subtypes.get(0);
                break;
            case 2:
                s += subtypes.get(0).getIndefiniteArticle() + " " + subtypes.get(0);
                s += " and ";
                s += subtypes.get(1).getIndefiniteArticle() + " " + subtypes.get(1);
                break;
            default:
                for (int i = 0; i < subtypes.size(); i++) {
                    if (i == 0) {
                        s += subtypes.get(0).getIndefiniteArticle() + " " + subtypes.get(0) + ", ";
                    } else if (i == subtypes.size() - 1) {
                        s += "and " + subtypes.get(0);
                    } else {
                        s += subtypes.get(0) + ", ";
                    }
                }
        }
        return s;
    }
}
