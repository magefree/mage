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
package mage.sets.urzasdestiny;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import static mage.constants.Layer.PTChangingEffects_7;
import static mage.constants.Layer.TypeChangingEffects_4;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author Mitchel Stein
 */
public class Opalescence extends CardImpl {

    public Opalescence(UUID ownerId) {
        super(ownerId, 13, "Opalescence", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");
        this.expansionSetCode = "UDS";

        // Each other non-Aura enchantment is a creature with power and toughness each equal to its converted mana cost. It's still an enchantment.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpalescenceEffect()));

    }

    public Opalescence(final Opalescence card) {
        super(card);
    }

    @Override
    public Opalescence copy() {
        return new Opalescence(this);
    }

}

class OpalescenceEffect extends ContinuousEffectImpl {

    private static final FilterEnchantmentPermanent filter = new FilterEnchantmentPermanent("Each other non-Aura enchantment");
    private static final EnumSet checkDependencyTypes;

    static {
        filter.add(Predicates.not(new SubtypePredicate("Aura")));
        filter.add(new AnotherPredicate());
        checkDependencyTypes = EnumSet.of(DependencyType.AuraAddingRemoving, DependencyType.EnchantmentAddingRemoving);
    }

    public OpalescenceEffect() {
        super(Duration.WhileOnBattlefield, Outcome.BecomeCreature);
        staticText = "Each other non-Aura enchantment is a creature in addition to its other types and has base power and base toughness each equal to its converted mana cost";
    }

    public OpalescenceEffect(final OpalescenceEffect effect) {
        super(effect);
    }

    @Override
    public OpalescenceEffect copy() {
        return new OpalescenceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    if (sublayer == SubLayer.NA) {
                        if (!permanent.getCardType().contains(CardType.CREATURE)) {
                            permanent.getCardType().add(CardType.CREATURE);
                        }
                    }
                    break;

                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        int manaCost = permanent.getManaCost().convertedManaCost();
                        permanent.getPower().setValue(manaCost);
                        permanent.getToughness().setValue(manaCost);
                    }
            }

        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.PTChangingEffects_7 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public Set<UUID> isDependentTo(List<ContinuousEffect> allEffectsInLayer) {
        Set<UUID> dependentTo = null;
        for (ContinuousEffect effect : allEffectsInLayer) {
            for (DependencyType dependencyType : effect.getDependencyTypes()) {
                if (checkDependencyTypes.contains(dependencyType)) {
                    if (dependentTo == null) {
                        dependentTo = new HashSet<>();
                    }
                    dependentTo.add(effect.getId());
                }
            }
        }
        return dependentTo;
    }
}
