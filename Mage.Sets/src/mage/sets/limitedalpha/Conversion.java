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
package mage.sets.limitedalpha;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.AbilityAddingRemovingEffects_6;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class Conversion extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Mountain", "Mountains");

    public Conversion(UUID ownerId) {
        super(ownerId, 199, "Conversion", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "LEA";

        // At the beginning of your upkeep, sacrifice Conversion unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{W}{W}")), TargetController.YOU, false));

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

    class ConversionEffect extends ContinuousEffectImpl {

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
            for (Permanent land : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                switch (layer) {
                    case AbilityAddingRemovingEffects_6:
                        land.removeAllAbilities(source.getSourceId(), game);
                        land.addAbility(new WhiteManaAbility(), source.getSourceId(), game);
                        break;
                    case TypeChangingEffects_4:
                        land.getSubtype().clear();
                        land.getSubtype().add("Plains");
                        break;
                }
            }
            return true;
        }

        @Override
        public boolean hasLayer(Layer layer) {
            return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
        }

        @Override
        public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
            // the dependent classes needs to be an enclosed class for dependent check of continuous effects
            Set<UUID> dependentTo = null;
            for (ContinuousEffect effect : allEffectsInLayer) {
                // http://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/286046-conversion-magus-of-the-moon
                if (effect.getDependencyTypes().contains(DependencyType.BecomeMountain)) {
                    if (dependentTo == null) {
                        dependentTo = new HashSet<>();
                    }
                    dependentTo.add(effect.getId());
                }
            }
            return dependentTo;
        }

    }

}
