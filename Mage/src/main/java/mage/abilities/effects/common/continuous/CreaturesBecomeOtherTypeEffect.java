package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class CreaturesBecomeOtherTypeEffect extends ContinuousEffectImpl {

    protected final FilterPermanent filter;
    private final SubType subType;

    public CreaturesBecomeOtherTypeEffect(FilterPermanent filter, SubType subType, Duration duration) {
        super(duration, Outcome.Neutral);
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
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public CreaturesBecomeOtherTypeEffect copy() {
        return new CreaturesBecomeOtherTypeEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer == Layer.TypeChangingEffects_4) {
            for (Permanent object : game.getBattlefield().getActivePermanents(this.filter, source.getControllerId(), game)) {
                object.addSubType(game, this.subType);
            }
        }

        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}