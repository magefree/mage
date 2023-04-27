package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseBasicLandTypeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
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
        this.addAbility(new EnchantAbility(auraTarget));

        // As Phantasmal Terrain enters the battlefield, choose a basic land type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseBasicLandTypeEffect(Outcome.Neutral)));

        // Enchanted land is the chosen type.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhantasmalTerrainContinuousEffect()));
    }

    private PhantasmalTerrain(final PhantasmalTerrain card) {
        super(card);
    }

    @Override
    public PhantasmalTerrain copy() {
        return new PhantasmalTerrain(this);
    }

    private static class PhantasmalTerrainContinuousEffect extends ContinuousEffectImpl {

        private PhantasmalTerrainContinuousEffect() {
            super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
            staticText = "Enchanted land is the chosen type";
        }

        private PhantasmalTerrainContinuousEffect(final PhantasmalTerrainContinuousEffect effect) {
            super(effect);
        }

        @Override
        public PhantasmalTerrainContinuousEffect copy() {
            return new PhantasmalTerrainContinuousEffect(this);
        }

        @Override
        public void init(Ability source, Game game) {
            super.init(source, game);
            SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
            switch (choice) {
                case FOREST:
                    dependencyTypes.add(DependencyType.BecomeForest);
                    break;
                case PLAINS:
                    dependencyTypes.add(DependencyType.BecomePlains);
                    break;
                case MOUNTAIN:
                    dependencyTypes.add(DependencyType.BecomeMountain);
                    break;
                case ISLAND:
                    dependencyTypes.add(DependencyType.BecomeIsland);
                    break;
                case SWAMP:
                    dependencyTypes.add(DependencyType.BecomeSwamp);
                    break;
            }
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent enchantment = game.getPermanent(source.getSourceId());
            SubType choice = SubType.byDescription((String) game.getState().getValue(source.getSourceId().toString() + ChooseBasicLandTypeEffect.VALUE_KEY));
            if (enchantment == null || enchantment.getAttachedTo() == null || choice == null) {
                return false;
            }
            Permanent land = game.getPermanent(enchantment.getAttachedTo());
            if (land == null) {
                return false;
            }
            land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
            land.addSubType(game, choice);
            land.removeAllAbilities(source.getSourceId(), game);
            switch (choice) {
                case FOREST:
                    land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                    break;
                case PLAINS:
                    land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                    break;
                case MOUNTAIN:
                    land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                    break;
                case ISLAND:
                    land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                    break;
                case SWAMP:
                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    break;
            }
            return true;
        }
    }
}
