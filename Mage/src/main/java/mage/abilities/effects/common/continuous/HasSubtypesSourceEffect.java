package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Warning, do not copy it - hasSubTypeForDeckbuilding uses it to find additional subtypes in cards
 *
 * @author TheElk801
 */
public final class HasSubtypesSourceEffect extends ContinuousEffectImpl {

    private final List<SubType> subtypes = new ArrayList<>();

    public HasSubtypesSourceEffect(SubType... subTypes) {
        super(Duration.EndOfGame, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        subtypes.addAll(Arrays.asList(subTypes));
    }

    protected HasSubtypesSourceEffect(final HasSubtypesSourceEffect effect) {
        super(effect);
        this.subtypes.addAll(effect.subtypes);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof MageObject)) {
                continue;
            }
            ((MageObject) object).addSubType(game, subtypes);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        return sourceObject != null ? Collections.singletonList(sourceObject) : Collections.emptyList();
    }

    @Override
    public HasSubtypesSourceEffect copy() {
        return new HasSubtypesSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "{this} is also " + subtypes.get(0).getIndefiniteArticle() + ' '
                + CardUtil.concatWithAnd(subtypes.stream().map(SubType::getDescription).collect(Collectors.toList()));
    }

    public boolean hasSubtype(SubType subType) {
        return subtypes.contains(subType);
    }
}
