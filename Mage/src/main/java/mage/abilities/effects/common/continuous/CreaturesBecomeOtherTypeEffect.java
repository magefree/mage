package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

public class CreaturesBecomeOtherTypeEffect extends ContinuousEffectImpl {

    protected final FilterPermanent filter;
    private final SubType subType;

    public CreaturesBecomeOtherTypeEffect(FilterPermanent filter, SubType subType, Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        this.filter = filter;
        this.subType = subType;

        this.dependendToTypes.add(DependencyType.BecomeCreature);  // Opalescence and Starfield of Nyx
        this.staticText = this.filter.getMessage() + " is " + this.subType.getIndefiniteArticle()
                + " " + this.subType.toString() + " in addition to its other types";
    }

    protected CreaturesBecomeOtherTypeEffect(final CreaturesBecomeOtherTypeEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.subType = effect.subType;
    }

    @Override
    public CreaturesBecomeOtherTypeEffect copy() {
        return new CreaturesBecomeOtherTypeEffect(this);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> objects) {
        if (objects.isEmpty()) {
            return false;
        }
        for (MageItem object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            ((Permanent) object).addSubType(game, this.subType);
        }
        return true;
    }

    @Override
    public List<MageItem> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return new ArrayList<>(game.getBattlefield()
                .getActivePermanents(filter, source.getControllerId(), game));
    }
}