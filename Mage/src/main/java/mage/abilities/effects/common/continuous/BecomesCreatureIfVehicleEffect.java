package mage.abilities.effects.common.continuous;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

/**
 * Created by IGOUDT on 5-4-2017.
 */
public class BecomesCreatureIfVehicleEffect extends ContinuousEffectImpl {

    private final CardType addedType = CardType.CREATURE;

    public BecomesCreatureIfVehicleEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.staticText = "As long as enchanted permanent is a Vehicle, it's a creature in addition to its other types";
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    protected BecomesCreatureIfVehicleEffect(final BecomesCreatureIfVehicleEffect effect) {
        super(effect);
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        if (attachment != null && attachment.getAttachedTo() != null) {
            Permanent attachedTo = game.getPermanent(attachment.getAttachedTo());
            if (attachedTo != null) {
                affectedObjects.add(attachedTo);
            }
        }
        return !affectedObjects.isEmpty();
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            ((Permanent) object).addCardType(game, addedType);
        }
    }

    @Override
    public BecomesCreatureIfVehicleEffect copy() {
        return new BecomesCreatureIfVehicleEffect(this);
    }
}