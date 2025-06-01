package mage.abilities.effects.common.continuous;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    protected BecomesBasicLandEnchantedEffect(final BecomesBasicLandEnchantedEffect effect) {
        super(effect);
        this.landTypes.addAll(effect.landTypes);
    }

    @Override
    public boolean applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageObject> objects) {
       for (MageObject object : objects) {
           if (!(object instanceof Permanent)) {
               continue;
           }
           Permanent permanent = (Permanent) object;
           // lands intrinsically have the mana ability associated with their type, so added here in layer 4
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
       }
       return true;
    }

    @Override
    public List<MageObject> queryAffectedObjects(Layer layer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment == null) {
            return Collections.emptyList();
        }
        Permanent enchanted = game.getPermanent(enchantment.getAttachedTo());
        return enchanted != null ? Collections.singletonList(enchanted) : Collections.emptyList();
    }

    @Override
    public BecomesBasicLandEnchantedEffect copy() {
        return new BecomesBasicLandEnchantedEffect(this);
    }
}
