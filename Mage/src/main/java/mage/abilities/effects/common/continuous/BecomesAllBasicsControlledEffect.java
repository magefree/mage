package mage.abilities.effects.common.continuous;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Iterator;

/**
 * @author TheElk801
 */
public class BecomesAllBasicsControlledEffect extends ContinuousEffectImpl {
    // Used only for Permanent.containsRule() to check if the permanent has a basic mana ability that should be removed
    private static final Ability[] basicManaAbilities = {
            new WhiteManaAbility(),
            new BlueManaAbility(),
            new BlackManaAbility(),
            new RedManaAbility(),
            new GreenManaAbility()
    };

    public BecomesAllBasicsControlledEffect(Duration duration) {
        super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        this.staticText = "lands you control are every basic land type in addition to their other types";
        dependendToTypes.add(DependencyType.BecomeNonbasicLand);
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
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (getAffectedObjectsSet()) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), game)) {
                affectedObjectList.add(new MageObjectReference(permanent, game));
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!getAffectedObjectsSet()) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source.getControllerId(), game
            )) {
                removeTypes(permanent, game, source);
            }
            return true;
        }
        for (Iterator<MageObjectReference> it = affectedObjectList.iterator(); it.hasNext(); ) {
            Permanent permanent = it.next().getPermanent(game);
            if (permanent != null) {
                removeTypes(permanent, game, source);
            } else {
                it.remove();
            }
        }
        return true;
    }

    private static void removeTypes(Permanent permanent, Game game, Ability source) {
        permanent.addSubType(game,
                SubType.PLAINS,
                SubType.ISLAND,
                SubType.SWAMP,
                SubType.MOUNTAIN,
                SubType.FOREST);
        // Optimization: Remove basic mana abilities since they are redundant with AnyColorManaAbility
        //               and keeping them will only produce too many combinations inside ManaOptions
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
}
