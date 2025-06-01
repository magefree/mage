package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Collections;
import java.util.List;

/**
 * Created by IGOUDT on 5-4-2017.
 */
public class BecomesCreatureIfVehicleEffect extends ContinuousEffectImpl {

    private CardType addedType = CardType.CREATURE;

    public BecomesCreatureIfVehicleEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        this.staticText = "As long as enchanted permanent is a Vehicle, it's a creature in addition to its other types";
        this.dependencyTypes.add(DependencyType.BecomeCreature);
    }

    protected BecomesCreatureIfVehicleEffect(final BecomesCreatureIfVehicleEffect effect) {
        super(effect);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            object.addCardType(game, addedType);
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent aura = game.getPermanent(source.getSourceId());
        if (aura == null) {
            return Collections.emptyList();
        }
        Permanent enchanted = game.getPermanent(aura.getAttachedTo());
        return enchanted != null ? Collections.singletonList(enchanted) : Collections.emptyList();
    }

    @Override
    public BecomesCreatureIfVehicleEffect copy() {
        return new BecomesCreatureIfVehicleEffect(this);
    }
}