package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EnchantedEvening extends CardImpl {

    public EnchantedEvening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W/U}{W/U}");

        // All permanents are enchantments in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new EnchangedEveningEffect()));
    }

    private EnchantedEvening(final EnchantedEvening card) {
        super(card);
    }

    @Override
    public EnchantedEvening copy() {
        return new EnchantedEvening(this);
    }

    // need to be enclosed class for dependent check of continuous effects
    private static class EnchangedEveningEffect extends ContinuousEffectImpl {

        private EnchangedEveningEffect() {
            super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
            this.dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
            this.dependencyTypes.add(DependencyType.AuraAddingRemoving);
            this.staticText = "All permanents are enchantments in addition to their other types";
        }

        private EnchangedEveningEffect(final EnchangedEveningEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (Permanent permanent : game.getBattlefield().getActivePermanents(
                    StaticFilters.FILTER_PERMANENT, source.getControllerId(),
                    source, game
            )) {
                if (permanent != null) {
                    permanent.addCardType(game, CardType.ENCHANTMENT);
                }
            }
            return true;
        }

        @Override
        public EnchangedEveningEffect copy() {
            return new EnchangedEveningEffect(this);
        }
    }
}
