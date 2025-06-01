package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Plopman, TheElk801
 */
public class AddBasicLandTypeAllLandsEffect extends ContinuousEffectImpl {

    private final SubType subType;

    public AddBasicLandTypeAllLandsEffect(SubType subType) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.AIDontUseIt);
        this.subType = subType;
        this.staticText = "Each land is " + subType.getIndefiniteArticle() + " "
                + subType.getDescription() + " in addition to its other land types";
        switch (subType) {
            case PLAINS:
                this.dependencyTypes.add(DependencyType.BecomePlains);
                break;
            case ISLAND:
                this.dependencyTypes.add(DependencyType.BecomeIsland);
                break;
            case SWAMP:
                this.dependencyTypes.add(DependencyType.BecomeSwamp);
                break;
            case MOUNTAIN:
                this.dependencyTypes.add(DependencyType.BecomeMountain);
                break;
            case FOREST:
                this.dependencyTypes.add(DependencyType.BecomeForest);
                break;
            default:
                throw new UnsupportedOperationException("Subtype should be a basic land type");
        }
    }

    private AddBasicLandTypeAllLandsEffect(final AddBasicLandTypeAllLandsEffect effect) {
        super(effect);
        this.subType = effect.subType;
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
        Ability ability;
        switch (subType) {
            case PLAINS:
                ability = new WhiteManaAbility();
                break;
            case ISLAND:
                ability = new BlueManaAbility();
                break;
            case SWAMP:
                ability = new BlackManaAbility();
                break;
            case MOUNTAIN:
                ability = new RedManaAbility();
                break;
            case FOREST:
                ability = new GreenManaAbility();
                break;
            default:
                ability = null;
        }
        if (ability == null) {
            return false;
        }
        for (MageObject object : objects) {
            if (!(object instanceof Permanent)) {
                continue;
            }
            Permanent land = (Permanent) object;
            land.addSubType(game, subType);
            // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
            // So the ability removing has to be done before Layer 6
            // Lands have their mana ability intrinsically, so that is added in layer 4
            if (!land.getAbilities(game).contains(ability)) {
                land.addAbility(ability, source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        return game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public AddBasicLandTypeAllLandsEffect copy() {
        return new AddBasicLandTypeAllLandsEffect(this);
    }
}
