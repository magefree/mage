
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BecomesBasicLandEnchantedEffect extends ContinuousEffectImpl {

    protected List<SubType> landTypes = new ArrayList<>();

    public BecomesBasicLandEnchantedEffect(SubType... landNames) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        landTypes.addAll(Arrays.asList(landNames));
        this.staticText = setText();
    }

    public BecomesBasicLandEnchantedEffect(final BecomesBasicLandEnchantedEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public BecomesBasicLandEnchantedEffect copy() {
        return new BecomesBasicLandEnchantedEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
            if (permanent != null) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        permanent.removeAllAbilities(source.getSourceId(), game);
                        for (SubType landType : landTypes) {
                            switch (landType) {
                                case SWAMP:
                                    if (permanent.hasSubtype(SubType.SWAMP, game)) { // type can be removed by other effect with newer timestamp, so no ability adding
                                        permanent.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case MOUNTAIN:
                                    if (permanent.hasSubtype(SubType.MOUNTAIN, game)) {
                                        permanent.addAbility(new RedManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case FOREST:
                                    if (permanent.hasSubtype(SubType.FOREST, game)) {
                                        permanent.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case ISLAND:
                                    if (permanent.hasSubtype(SubType.ISLAND, game)) {
                                        permanent.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                                case PLAINS:
                                    if (permanent.hasSubtype(SubType.PLAINS, game)) {
                                        permanent.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                                    }
                                    break;
                            }
                        }
                        break;
                    case TypeChangingEffects_4:
                        // subtypes are all removed by changing the subtype to a land type.
                        permanent.getSubtype(game).removeAll(SubType.getLandTypes(false));
                        permanent.getSubtype(game).addAll(landTypes);
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Enchanted land is a ");
        int i = 1;
        for (SubType landType : landTypes) {
            if (i > 1) {
                if (i == landTypes.size()) {
                    sb.append(" and ");
                } else {
                    sb.append(", ");
                }
            }
            i++;
            sb.append(landType);
        }
        return sb.toString();
    }
}
