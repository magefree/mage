package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * @author jmharmon
 */
public final class Glaciers extends CardImpl {

    public Glaciers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{U}");

        // At the beginning of your upkeep, sacrifice Glaciers unless you pay {W}{U}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{W}{U}")), TargetController.YOU, false));

        // All Mountains are Plains.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GlaciersEffect()));
    }

    private Glaciers(final Glaciers card) {
        super(card);
    }

    @Override
    public Glaciers copy() {
        return new Glaciers(this);
    }

    static class GlaciersEffect extends ContinuousEffectImpl {

        GlaciersEffect() {
            super(Duration.WhileOnBattlefield, Outcome.Detriment);
            this.staticText = "All Mountains are Plains";
        }

        GlaciersEffect(final GlaciersEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }

        @Override
        public GlaciersEffect copy() {
            return new GlaciersEffect(this);
        }

        @Override
        public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
            for (Permanent land : game.getBattlefield().getAllActivePermanents(CardType.LAND, game)) {
                switch (layer) {
                    case TypeChangingEffects_4:
                        // the land mana ability is intrinsic, so apply at this layer not layer 6
                        if (land.hasSubtype(SubType.MOUNTAIN, game)) {
                            land.removeAllSubTypes(game, SubTypeSet.NonBasicLandType);
                            land.addSubType(game, SubType.PLAINS);
                            land.removeAllAbilities(source.getSourceId(), game);
                            land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                            break;
                        }
                }

            }
            return true;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.TypeChangingEffects_4;
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
