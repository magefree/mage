package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class AssimilateContinuousEffect extends ContinuousEffectImpl {

    private final MageObjectReference mor;

    public AssimilateContinuousEffect(MageObjectReference mor) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "It's a Borg artifact creature and loses all other creature types.";
        this.mor = mor;
        dependencyTypes.add(DependencyType.ArtifactAddingRemoving);
    }

    private AssimilateContinuousEffect(final AssimilateContinuousEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public AssimilateContinuousEffect copy() {
        return new AssimilateContinuousEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        affectedObjectList.add(mor);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = affectedObjectList.get(0).getPermanent(game);
        if (permanent == null) {
            this.discard();
            return false;
        }
        if (layer == Layer.TypeChangingEffects_4) {
            permanent.setIsAllCreatureTypes(game, false);
            permanent.retainAllArtifactSubTypes(game);
            permanent.removeAllCardTypes(game);
            permanent.addCardType(game, CardType.ARTIFACT);
            permanent.addCardType(game, CardType.CREATURE);
            permanent.addSubType(game, SubType.BORG);
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}
