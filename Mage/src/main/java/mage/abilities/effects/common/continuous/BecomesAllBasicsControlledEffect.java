package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author TheElk801
 */
public class BecomesAllBasicsControlledEffect extends ContinuousEffectImpl {

    public BecomesAllBasicsControlledEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "Lands you control are every basic land type in addition to their other types";
        dependencyTypes.add(DependencyType.BecomeMountain);
        dependencyTypes.add(DependencyType.BecomeForest);
        dependencyTypes.add(DependencyType.BecomeSwamp);
        dependencyTypes.add(DependencyType.BecomeIsland);
        dependencyTypes.add(DependencyType.BecomePlains);
    }

    private BecomesAllBasicsControlledEffect(final BecomesAllBasicsControlledEffect effect) {
        super(effect);
    }

    @Override
    public BecomesAllBasicsControlledEffect copy() {
        return new BecomesAllBasicsControlledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), game)) {
            permanent.addSubType(
                    game,
                    SubType.PLAINS,
                    SubType.ISLAND,
                    SubType.SWAMP,
                    SubType.MOUNTAIN,
                    SubType.FOREST
            );
            permanent.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
            permanent.addAbility(new BlueManaAbility(), source.getSourceId(), game);
            permanent.addAbility(new BlackManaAbility(), source.getSourceId(), game);
            permanent.addAbility(new RedManaAbility(), source.getSourceId(), game);
            permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
        }
        return true;
    }
}
