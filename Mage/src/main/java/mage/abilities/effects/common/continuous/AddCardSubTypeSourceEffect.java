package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
        Permanent permanent = game.getPermanentEntering(source.getSourceId());
        if (permanent != null) {
            affectedObjectList.add(new MageObjectReference(source.getSourceId(), game.getState().getZoneChangeCounter(source.getSourceId()) + 1, game));
        } else {
            affectedObjectList.add(new MageObjectReference(source.getSourceId(), game));
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        int offset = 0;
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
            offset++;
        }
        if (permanent != null && affectedObjectList.contains(new MageObjectReference(permanent, game, offset))) {
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
        StringBuilder sb = new StringBuilder();
        sb.append("{this} becomes ");
        boolean article = false;
        for (SubType subType : addedSubTypes) {
            if (!article) {
                if (subType.toString().startsWith("A") || subType.toString().startsWith("E")) {
                    sb.append("an ");
                } else {
                    sb.append("a ");
                }
                article = true;
            }
            sb.append(subType.toString().toLowerCase(Locale.ENGLISH)).append(" ");
        }
        if (inAddition) {
            sb.append(" in addition to its other types ");
        }
        sb.append(this.getDuration().toString());
        return sb.toString();
    }
}
