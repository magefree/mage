
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Quercitron
 */
public final class PhantasmalTerrain extends CardImpl {

    public PhantasmalTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // As Phantasmal Terrain enters the battlefield, choose a basic land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.Neutral)));

        // Enchanted land is the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhantasmalTerrainContinuousEffect()));
    }

    public PhantasmalTerrain(final PhantasmalTerrain card) {
        super(card);
    }

    @Override
    public PhantasmalTerrain copy() {
        return new PhantasmalTerrain(this);
    }
}

class PhantasmalTerrainContinuousEffect extends ContinuousEffectImpl {

    public PhantasmalTerrainContinuousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "enchanted land is the chosen type";
    }

    public PhantasmalTerrainContinuousEffect(final PhantasmalTerrainContinuousEffect effect) {
        super(effect);
    }

    @Override
    public PhantasmalTerrainContinuousEffect copy() {
        return new PhantasmalTerrainContinuousEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
        if (enchantment != null && enchantment.getAttachedTo() != null && choice != null) {
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land != null) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (sublayer == SubLayer.NA) {
                            land.getSubtype(game).clear();
                            land.getSubtype(game).add(choice);
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (sublayer == SubLayer.NA) {
                            land.getAbilities().clear();
                            if (choice.equals(SubType.FOREST)) {
                                land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                            }
                            if (choice.equals(SubType.PLAINS)) {
                                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                            }
                            if (choice.equals(SubType.MOUNTAIN)) {
                                land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                            }
                            if (choice.equals(SubType.ISLAND)) {
                                land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                            }
                            if (choice.equals(SubType.SWAMP)) {
                                land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                            }
                        }
                        break;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 
                || layer == Layer.TypeChangingEffects_4;
    }

}
