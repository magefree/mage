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
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
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
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesAllBasicsControlledEffect copy() {
        return new BecomesAllBasicsControlledEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (!permanent.hasSubtype(SubType.SWAMP, game)) {
                        permanent.getSubtype(game).add(SubType.SWAMP);
                    }
                    if (!permanent.hasSubtype(SubType.MOUNTAIN, game)) {
                        permanent.getSubtype(game).add(SubType.MOUNTAIN);
                    }
                    if (!permanent.hasSubtype(SubType.FOREST, game)) {
                        permanent.getSubtype(game).add(SubType.FOREST);
                    }
                    if (!permanent.hasSubtype(SubType.ISLAND, game)) {
                        permanent.getSubtype(game).add(SubType.ISLAND);
                    }
                    if (!permanent.hasSubtype(SubType.PLAINS, game)) {
                        permanent.getSubtype(game).add(SubType.PLAINS);
                    }
                    if (permanent.hasSubtype(SubType.SWAMP, game)
                            && !permanent.getAbilities().containsRule(new BlackManaAbility())) {
                        permanent.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    }
                    if (permanent.hasSubtype(SubType.MOUNTAIN, game)
                            && !permanent.getAbilities().containsRule(new RedManaAbility())) {
                        permanent.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    }
                    if (permanent.hasSubtype(SubType.FOREST, game)
                            && !permanent.getAbilities().containsRule(new GreenManaAbility())) {
                        permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    }
                    if (permanent.hasSubtype(SubType.ISLAND, game)
                            && !permanent.getAbilities().containsRule(new BlueManaAbility())) {
                        permanent.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    }
                    if (permanent.hasSubtype(SubType.PLAINS, game)
                            && !permanent.getAbilities().containsRule(new WhiteManaAbility())) {
                        permanent.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    }
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.TypeChangingEffects_4;
    }
}
