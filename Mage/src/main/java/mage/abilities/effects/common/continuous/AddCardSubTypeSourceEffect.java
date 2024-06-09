package mage.abilities.effects.common.continuous;

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
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null && affectedObjectList.contains(new MageObjectReference(permanent, game))) {
            if (!inAddition) {
                permanent.removeAllCreatureTypes(game);
            }
            for (SubType cardType : addedSubTypes) {
                permanent.addSubType(game, cardType);
            }
            return true;
        } else if (this.getDuration() == Duration.Custom) {
            this.discard();
        }
        return false;
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
