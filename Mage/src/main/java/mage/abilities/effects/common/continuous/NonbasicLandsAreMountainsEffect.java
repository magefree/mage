package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.RedManaAbility;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author LevelX2
 */
public class NonbasicLandsAreMountainsEffect extends ContinuousEffectImpl {

    public NonbasicLandsAreMountainsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "nonbasic lands are Mountains";
        this.dependencyTypes.add(DependencyType.BecomeMountain);
        this.dependendToTypes.add(DependencyType.BecomeNonbasicLand);
    }

    private NonbasicLandsAreMountainsEffect(final NonbasicLandsAreMountainsEffect effect) {
        super(effect);
    }

    @Override
    public NonbasicLandsAreMountainsEffect copy() {
        return new NonbasicLandsAreMountainsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent land : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LANDS_NONBASIC, source.getControllerId(), game)) {
            // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
            // So the ability removing has to be done before Layer 6
            // Lands have their mana ability intrinsically, so that is added in layer 4
            land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
            land.addSubType(game, SubType.MOUNTAIN);
            land.removeAllAbilities(source.getSourceId(), game);
            land.addAbility(new RedManaAbility(), source.getSourceId(), game);
        }
        return true;
    }
}
