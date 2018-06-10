
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public final class EnchantedEvening extends CardImpl {

    public EnchantedEvening(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{W/U}{W/U}");

        // All permanents are enchantments in addition to their other types.
        Effect effect = new EnchangedEveningEffect(CardType.ENCHANTMENT, Duration.WhileOnBattlefield, new FilterPermanent());
        effect.setText("All permanents are enchantments in addition to their other types");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

    }

    public EnchantedEvening(final EnchantedEvening card) {
        super(card);
    }

    @Override
    public EnchantedEvening copy() {
        return new EnchantedEvening(this);
    }

    // need to be enclosed class for dependent check of continuous effects
    class EnchangedEveningEffect extends ContinuousEffectImpl {

        private final CardType addedCardType;
        private final FilterPermanent filter;

        public EnchangedEveningEffect(CardType addedCardType, Duration duration, FilterPermanent filter) {
            super(duration, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
            this.addedCardType = addedCardType;
            this.filter = filter;
            this.dependencyTypes.add(DependencyType.EnchantmentAddingRemoving);
        }

        public EnchangedEveningEffect(final EnchangedEveningEffect effect) {
            super(effect);
            this.addedCardType = effect.addedCardType;
            this.filter = effect.filter;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
                if (permanent != null && !permanent.getCardType().contains(addedCardType)) {
                    permanent.addCardType(addedCardType);
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
