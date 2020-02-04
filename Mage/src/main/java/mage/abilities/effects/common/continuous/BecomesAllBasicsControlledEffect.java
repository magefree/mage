package mage.abilities.effects.common.continuous;

import java.util.Collection;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.SubType;
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
        game.getState()
                .getBattlefield()
                .getAllActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)
                .stream()
                .forEach(land -> this.modifyLand(land, layer, source, game));
        return true;
    }

    private void modifyLand(Permanent land, Layer layer, Ability source, Game game) {
        if (layer == Layer.AbilityAddingRemovingEffects_6) {
            Mana mana = new Mana();
            land.getAbilities()
                    .stream()
                    .filter(BasicManaAbility.class::isInstance)
                    .map(BasicManaAbility.class::cast)
                    .map(basicManaAbility -> basicManaAbility.getNetMana(game))
                    .flatMap(Collection::stream)
                    .forEach(mana::add);
            if (mana.getGreen() == 0) {
                land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
            }
            if (mana.getRed() == 0) {
                land.addAbility(new RedManaAbility(), source.getSourceId(), game);
            }
            if (mana.getBlue() == 0) {
                land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
            }
            if (mana.getWhite() == 0) {
                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
            }
            if (mana.getBlack() == 0) {
                land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
            }
        }
        if (layer == Layer.TypeChangingEffects_4) {
            SubType.getBasicLands()
                    .stream()
                    .filter(subType -> !land.hasSubtype(subType, game))
                    .forEach(land.getSubtype(game)::add);
        }
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }
}
