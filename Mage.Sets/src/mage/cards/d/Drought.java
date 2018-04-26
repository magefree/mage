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
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.AbilityType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author L_J
 */
public class Drought extends CardImpl {

    public Drought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // At the beginning of your upkeep, sacrifice Drought unless you pay {W}{W}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl("{W}{W}")), TargetController.YOU, false));

        // Spells cost an additional "Sacrifice a Swamp" to cast for each black mana symbol in their mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DroughtAdditionalCostEffect(true)));

        // Activated abilities cost an additional "Sacrifice a Swamp" to activate for each black mana symbol in their activation costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DroughtAdditionalCostEffect(false)));
    }

    public Drought(final Drought card) {
        super(card);
    }

    @Override
    public Drought copy() {
        return new Drought(this);
    }
}

class DroughtAdditionalCostEffect extends CostModificationEffectImpl {

    private boolean appliesToSpells;
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Swamp");
    static{
        filter.add(new SubtypePredicate(SubType.SWAMP));
    }

    DroughtAdditionalCostEffect(boolean appliesToSpells) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.staticText = (appliesToSpells ? "Spells" : "Activated abilities") + " cost an additional \"Sacrifice a Swamp\" to activate for each black mana symbol in their " + (appliesToSpells ? "mana" : "activation") + " costs";
        this.appliesToSpells = appliesToSpells;
    }

    DroughtAdditionalCostEffect(DroughtAdditionalCostEffect effect) {
        super(effect);
        appliesToSpells = effect.appliesToSpells;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int blackSymbols = abilityToModify.getManaCosts().getMana().getBlack();
        TargetControlledPermanent target = new TargetControlledPermanent(blackSymbols, blackSymbols, filter, true);
        target.setRequired(false);
        abilityToModify.addCost(new SacrificeTargetCost(target));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return (appliesToSpells && abilityToModify.getAbilityType() == AbilityType.SPELL)
                || (!appliesToSpells && (abilityToModify.getAbilityType() == AbilityType.ACTIVATED || abilityToModify.getAbilityType() == AbilityType.MANA));
    }

    @Override
    public DroughtAdditionalCostEffect copy() {
        return new DroughtAdditionalCostEffect(this);
    }
}
