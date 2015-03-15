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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DashedCondition;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.keyword.DashAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class Warbringer extends CardImpl {

    public Warbringer(UUID ownerId) {
        super(ownerId, 168, "Warbringer", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.expansionSetCode = "DTK";
        this.subtype.add("Orc");
        this.subtype.add("Berserker");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Dash costs you pay cost {2} less (as long as this creature is on the battlefield).
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WarbringerSpellsCostReductionEffect()));

        // Dash {2}{R}
        this.addAbility(new DashAbility(this, "{2}{R}"));
    }

    public Warbringer(final Warbringer card) {
        super(card);
    }

    @Override
    public Warbringer copy() {
        return new Warbringer(this);
    }
}

class WarbringerSpellsCostReductionEffect extends CostModificationEffectImpl {

    public WarbringerSpellsCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.staticText = "Dash costs you pay cost {2} less (as long as this creature is on the battlefield)";
    }

    protected WarbringerSpellsCostReductionEffect(final WarbringerSpellsCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.getControllerId().equals(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    return DashedCondition.getInstance().apply(game, abilityToModify);
                }
            }
        }
        return false;
    }

    @Override
    public WarbringerSpellsCostReductionEffect copy() {
        return new WarbringerSpellsCostReductionEffect(this);
    }
}
