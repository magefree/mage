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

    private static final CardType addedType = CardType.CREATURE;

    public BecomesCreatureIfVehicleEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.staticText = "As long as enchanted permanent is a Vehicle, it's a creature in addition to its other types";
    }

    public BecomesCreatureIfVehicleEffect(final BecomesCreatureIfVehicleEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura != null && aura.getAttachedTo() != null) {
            Permanent enchanted = game.getPermanent(aura.getAttachedTo());
            if (enchanted != null && enchanted.hasSubtype(SubType.VEHICLE, game)) {
                enchanted.addCardType(game, addedType);
            }
        }

        return true;
    }

    @Override
    public BecomesCreatureIfVehicleEffect copy() {
        return new BecomesCreatureIfVehicleEffect(this);
    }
}