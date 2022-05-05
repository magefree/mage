package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BecomesBasicLandEnchantedEffect extends ContinuousEffectImpl {

    protected List<SubType> landTypes = new ArrayList<>();

    public BecomesBasicLandEnchantedEffect(SubType... landNames) {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
        landTypes.addAll(Arrays.asList(landNames));
        this.staticText = "enchanted land is " + CardUtil.addArticle(CardUtil.concatWithAnd(landTypes
                .stream()
                .map(SubType::getDescription)
                .collect(Collectors.toList())
        ));
    }

    public BecomesBasicLandEnchantedEffect(final BecomesBasicLandEnchantedEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public BecomesBasicLandEnchantedEffect copy() {
        return new BecomesBasicLandEnchantedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null || enchantment.getAttachedTo() == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(enchantment.getAttachedTo());
        if (permanent == null) {
            return false;
        }
        // lands intrictically have the mana ability associated with their type, so added here in layer 4
        permanent.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
        permanent.addSubType(game, landTypes);
        permanent.removeAllAbilities(source.getSourceId(), game);
        for (SubType landType : landTypes) {
            switch (landType) {
                case PLAINS:
                    if (permanent.hasSubtype(SubType.PLAINS, game)) {
                        permanent.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    }
                    break;
                case ISLAND:
                    if (permanent.hasSubtype(SubType.ISLAND, game)) {
                        permanent.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    }
                    break;
                case SWAMP:
                    if (permanent.hasSubtype(SubType.SWAMP, game)) {
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
            }
        }
        return true;
    }
}
