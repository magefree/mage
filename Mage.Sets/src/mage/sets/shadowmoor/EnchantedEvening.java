/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class EnchantedEvening extends CardImpl {

    public EnchantedEvening(UUID ownerId) {
        super(ownerId, 140, "Enchanted Evening", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W/U}{W/U}");
        this.expansionSetCode = "SHM";

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
                    permanent.getCardType().add(addedCardType);
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
