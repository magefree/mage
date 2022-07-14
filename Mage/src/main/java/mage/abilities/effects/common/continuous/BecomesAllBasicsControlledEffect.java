package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.List;

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
            permanent.addSubType(game,
                    SubType.PLAINS,
                    SubType.ISLAND,
                    SubType.SWAMP,
                    SubType.MOUNTAIN,
                    SubType.FOREST);
            // Optimization.
            // Remove basic mana abilities since they are redundant with AnyColorManaAbility
            // and keeping them will only produce too many combinations inside ManaOptions
            Ability[] basicManaAbilities = new Ability[]{
                    new WhiteManaAbility(),
                    new BlueManaAbility(),
                    new BlackManaAbility(),
                    new RedManaAbility(),
                    new GreenManaAbility()
            };
            for (Ability basicManaAbility : basicManaAbilities) {
                if (permanent.getAbilities(game).containsRule(basicManaAbility)) {
                    permanent.removeAbility(basicManaAbility, source.getSourceId(), game);
                }
            }
            // Add the {T}: Add one mana of any color ability
            // This is functionally equivalent to having five "{T}: Add {COLOR}" for each COLOR in {W}{U}{B}{R}{G}
            AnyColorManaAbility ability = new AnyColorManaAbility();
            if (!permanent.getAbilities(game).containsRule(ability)) {
                permanent.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }
}
