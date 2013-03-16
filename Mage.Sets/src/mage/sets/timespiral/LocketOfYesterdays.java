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
package mage.sets.timespiral;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.RetraceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class LocketOfYesterdays extends CardImpl<LocketOfYesterdays> {

    public LocketOfYesterdays(UUID ownerId) {
        super(ownerId, 258, "Locket of Yesterdays", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.expansionSetCode = "TSP";

        // Spells you cast cost {1} less to cast for each card with the same name as that spell in your graveyard.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new LocketOfYesterdaysCostReductionEffect()));
    }

    public LocketOfYesterdays(final LocketOfYesterdays card) {
        super(card);
    }

    @Override
    public LocketOfYesterdays copy() {
        return new LocketOfYesterdays(this);
    }
}

class LocketOfYesterdaysCostReductionEffect extends CostModificationEffectImpl<LocketOfYesterdaysCostReductionEffect> {

    LocketOfYesterdaysCostReductionEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "Spells you cast cost {1} less to cast for each card with the same name as that spell in your graveyard";
    }

    LocketOfYesterdaysCostReductionEffect(LocketOfYesterdaysCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        MageObject sourceObject = game.getObject(abilityToModify.getSourceId());
        if (sourceObject != null) {
            int amount = 0;
            for (UUID cardId :game.getState().getPlayer(source.getControllerId()).getGraveyard()) {
                Card card = game.getPermanent(cardId);
                if (card != null && card.getName().equals(sourceObject.getName())) {
                    amount++;
                }
            }
            if (amount > 0) {
                SpellAbility spellAbility = (SpellAbility) abilityToModify;
                CardUtil.adjustCost(spellAbility, amount);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify.getControllerId().equals(source.getControllerId()) &&
            (abilityToModify instanceof SpellAbility || abilityToModify instanceof FlashbackAbility || abilityToModify instanceof RetraceAbility)) {
                return true;
        }
        return false;
    }

    @Override
    public LocketOfYesterdaysCostReductionEffect copy() {
        return new LocketOfYesterdaysCostReductionEffect(this);
    }

}
