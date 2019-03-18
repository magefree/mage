package mage.cards.c;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class Conversion extends CardImpl {

    public Conversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, sacrifice Conversion unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(
                        new ManaCostsImpl("{W}{W}")),
                TargetController.YOU,
                false));

        // All Mountains are Plains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConversionEffect()));

    }

    public Conversion(final Conversion card) {
        super(card);
    }

    @Override
    public Conversion copy() {
        return new Conversion(this);
    }

    static class ConversionEffect extends ContinuousEffectImpl {

        ConversionEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Detriment);
            this.staticText = "All Mountains are Plains";
        }

        ConversionEffect(final ConversionEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public ConversionEffect copy() {
            return new ConversionEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent land : game.getBattlefield().getAllActivePermanents(CardType.LAND)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        if (land.getSubtype(game).contains(SubType.MOUNTAIN)) {
                            land.getSubtype(game).clear();
                            land.getSubtype(game).add(SubType.PLAINS);
                            game.getState().setValue("conversion"
                                    + source.getId()
                                    + land.getId()
                                    + land.getZoneChangeCounter(game),
                                    "true");
                        }
                        break;
                    case AbilityAddingRemovingEffects_6:
                        if (game.getState().getValue("conversion"
                                + source.getId()
                                + land.getId()
                                + land.getZoneChangeCounter(game)) != null
                                && game.getState().getValue("conversion"
                                        + source.getId()
                                        + land.getId()
                                        + land.getZoneChangeCounter(game)).equals("true")) {
                            land.removeAllAbilities(source.getSourceId(), game);
                            if (land.getSubtype(game).contains(SubType.FOREST)) {
                                land.addAbility(new GreenManaAbility(), source.getSourceId(), game);
                            }
                            if (land.getSubtype(game).contains(SubType.PLAINS)) {
                                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                            }
                            if (land.getSubtype(game).contains(SubType.MOUNTAIN)) {
                                land.addAbility(new RedManaAbility(), source.getSourceId(), game);
                            }
                            if (land.getSubtype(game).contains(SubType.ISLAND)) {
                                land.addAbility(new BlueManaAbility(), source.getSourceId(), game);
                            }
                            if (land.getSubtype(game).contains(SubType.SWAMP)) {
                                land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                            }
                        }
                        break;
                }
            }
            return true;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.AbilityAddingRemovingEffects_6
                    || layer == Layer.TypeChangingEffects_4;
        }

        @Override
        public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
            return allEffectsInLayer
                    .stream()
                    .filter(effect -> effect.getDependencyTypes().contains(DependencyType.BecomePlains))
                    .map(Effect::getId)
                    .collect(Collectors.toSet());
        }
    }
}
