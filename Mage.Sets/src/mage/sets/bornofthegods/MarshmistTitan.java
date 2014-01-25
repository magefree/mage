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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class MarshmistTitan extends CardImpl<MarshmistTitan> {

    public MarshmistTitan(UUID ownerId) {
        super(ownerId, 76, "Marshmist Titan", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{6}{B}");
        this.expansionSetCode = "BNG";
        this.subtype.add("Giant");

        this.color.setBlack(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Marshmist Titan costs {X} less to cast, where X is your devotion to black.
        this.addAbility(new SimpleStaticAbility(Zone.OUTSIDE, new MarshmistTitanCostReductionEffect()));
    }

    public MarshmistTitan(final MarshmistTitan card) {
        super(card);
    }

    @Override
    public MarshmistTitan copy() {
        return new MarshmistTitan(this);
    }
}

class MarshmistTitanCostReductionEffect extends CostModificationEffectImpl<MarshmistTitanCostReductionEffect> {

    public MarshmistTitanCostReductionEffect() {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "{this} costs {X} less to cast, where X is your devotion to black  <i>(Each {B} in the mana costs of permanents you control counts toward your devotion to black.)</i> ";
    }

    public MarshmistTitanCostReductionEffect(final MarshmistTitanCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility)abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getColorless() > 0) {
            int count = new DevotionCount(ColoredManaSymbol.B).calculate(game, source);
            int newCount = mana.getColorless() - count;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setColorless(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public MarshmistTitanCostReductionEffect copy() {
        return new MarshmistTitanCostReductionEffect(this);
    }
}
