package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class AddCardSubTypeSourceEffect extends ContinuousEffectImpl {

    private final boolean inAddition;
    private final List<SubType> addedSubTypes = new ArrayList<>();

    public AddCardSubTypeSourceEffect(Duration duration, SubType... addedSubType) {
        this(duration, false, addedSubType);
    }

    public AddCardSubTypeSourceEffect(Duration duration, boolean inAddition, SubType... addedSubType) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.inAddition = inAddition;
        this.addedSubTypes.addAll(Arrays.asList(addedSubType));
    }

    private AddCardSubTypeSourceEffect(final AddCardSubTypeSourceEffect effect) {
        super(effect);
        this.inAddition = effect.inAddition;
        this.addedSubTypes.addAll(effect.addedSubTypes);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty() && this.getDuration() == Duration.Custom) {
            discard();
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent permanent = (Permanent) object;
            if (!inAddition) {
                permanent.removeAllCreatureTypes(game);
            }
            for (SubType subType : addedSubTypes) {
                permanent.addSubType(game, subType);
            }
        }
        return true;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        for (MageObjectReference mor : affectedObjectList) {
            if (mor.refersTo(source.getSourceId(), game)) {
                return Collections.singletonList(mor.getPermanent(game));
            }
        }
        return Collections.emptyList();
    }

    @Override
    public AddCardSubTypeSourceEffect copy() {
        return new AddCardSubTypeSourceEffect(this);
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String subTypeText = addedSubTypes
                .stream()
                .map(SubType::toString)
                .collect(Collectors.joining(" "));
        return "{this} becomes "
                + CardUtil.addArticle(subTypeText)
                + (inAddition ? " in addition to its other types" : "")
                + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }
}
