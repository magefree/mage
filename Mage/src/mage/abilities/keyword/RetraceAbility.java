/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.abilities.keyword;

import mage.abilities.SpellAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.cards.Card;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.filter.common.FilterLandCard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public class RetraceAbility extends SpellAbility {

    public RetraceAbility(Card card) {
        super(card.getManaCost(), card.getName() + " with retrace", Zone.GRAVEYARD, SpellAbilityType.BASE_ALTERNATE);
        this.getCosts().addAll(card.getSpellAbility().getCosts().copy());
        this.addCost(new DiscardTargetCost(new TargetCardInHand(new FilterLandCard())));
        this.getEffects().addAll(card.getSpellAbility().getEffects().copy());
        this.getTargets().addAll(card.getSpellAbility().getTargets().copy());
        this.getChoices().addAll(card.getSpellAbility().getChoices().copy());
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.timing = card.getSpellAbility().getTiming();

    }

    public RetraceAbility(final RetraceAbility ability) {
        super(ability);
    }

    @Override
    public RetraceAbility copy() {
        return new RetraceAbility(this);
    }

    @Override
    public String getRule(boolean all) {
        return getRule();
    }

    @Override
    public String getRule() {
        return "Retrace <i>(You may cast this card from your graveyard by discarding a land card in addition to paying its other costs.)</i>";
    }
}
