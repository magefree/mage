package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * Created by IGOUDT on 5-4-2017.
 */
public class BecomesCreatureIfVehicleEffect extends ContinuousEffectImpl {

    public BecomesCreatureIfVehicleEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.staticText = "As long as enchanted permanent is a Vehicle, it's a creature in addition to its other types";
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    protected BecomesCreatureIfVehicleEffect(final BecomesCreatureIfVehicleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getPermanentSourceAttachedToIfItStillExists(game);
        if (permanent != null && permanent.hasSubtype(SubType.VEHICLE, game)) {
            permanent.addCardType(game, CardType.CREATURE);
            return true;
        }
        return false;
    }

    @Override
    public BecomesCreatureIfVehicleEffect copy() {
        return new BecomesCreatureIfVehicleEffect(this);
    }
}
