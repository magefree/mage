package mage.cards.c;

import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class Conversion extends CardImpl {

    public Conversion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, sacrifice Conversion unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(
                        new ManaCostsImpl<>("{W}{W}"))
        ));

        // All Mountains are Plains.
        this.addAbility(new SimpleStaticAbility(new ConversionEffect()));

    }

    private Conversion(final Conversion card) {
        super(card);
    }

    @Override
    public Conversion copy() {
        return new Conversion(this);
    }

    static class ConversionEffect extends ContinuousEffectImpl {

        ConversionEffect() {
            super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Detriment);
            this.staticText = "All Mountains are Plains";
        }

        private ConversionEffect(final ConversionEffect effect) {
            super(effect);
        }

        @Override
        public ConversionEffect copy() {
            return new ConversionEffect(this);
        }

        @Override
        public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
            for (MageItem object : affectedObjects) {
                Permanent land = (Permanent) object;
                land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                land.addSubType(game, SubType.PLAINS);
                land.removeAllAbilities(source.getSourceId(), game);
                land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
            }
        }

        @Override
        public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
            affectedObjects.addAll(game.getBattlefield().getActivePermanents(
                    new FilterLandPermanent(SubType.MOUNTAIN, "Mountains"), source.getControllerId(), source, game));
            return !affectedObjects.isEmpty();
        }

        @Override
        public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
            return allEffectsInLayer
                    .stream()
                    .filter(effect -> effect.getDependencyTypes().contains(DependencyType.BecomeMountain))
                    .map(Effect::getId)
                    .collect(Collectors.toSet());
        }
    }
}
