package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

public class VehiclesBecomeArtifactCreatureEffect extends ContinuousEffectImpl {

    public VehiclesBecomeArtifactCreatureEffect(Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.BecomeCreature);
        staticText = "Vehicles you control become artifact creatures until end of turn";
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    private VehiclesBecomeArtifactCreatureEffect(final VehiclesBecomeArtifactCreatureEffect effect) {
        super(effect);
    }

    @Override
    public VehiclesBecomeArtifactCreatureEffect copy() {
        return new VehiclesBecomeArtifactCreatureEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (permanent != null && permanent.hasSubtype(SubType.VEHICLE, game)) {
                if (sublayer == SubLayer.NA) {
                    permanent.addCardType(game, CardType.ARTIFACT);
                    permanent.addCardType(game, CardType.CREATURE);// TODO: Check if giving CREATURE Type is correct
                }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }
}
