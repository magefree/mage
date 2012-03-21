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
package mage.sets.darkascension;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.GainAbilityControlledEffect;
import mage.abilities.effects.common.continious.GainAbilityControllerEffect;
import mage.abilities.effects.common.continious.GainProtectionFromColorTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.choices.ChoiceColor;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class FaithsShield extends CardImpl<FaithsShield> {

    public FaithsShield(UUID ownerId) {
        super(ownerId, 7, "Faith's Shield", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "DKA";

        this.color.setWhite(true);

        // Target permanent you control gains protection from the color of your choice until end of turn.
        
        // Fateful hour - If you have 5 or less life, instead you and each permanent you control gain protection from the color of your choice until end of turn.
        this.getSpellAbility().addEffect(new FaithsShieldEffect());
        this.getSpellAbility().addTarget(new TargetControlledPermanent());
        this.getSpellAbility().addChoice(new ChoiceColor());        
    }

    public FaithsShield(final FaithsShield card) {
        super(card);
    }

    @Override
    public FaithsShield copy() {
        return new FaithsShield(this);
    }
}

class FaithsShieldEffect extends OneShotEffect<FaithsShieldEffect> {

    public FaithsShieldEffect() {
        super(Outcome.Protect);
        staticText = "Target permanent you control gains protection from the color of your choice until end of turn\nFateful hour - If you have 5 or less life, instead you and each permanent you control gain protection from the color of your choice until end of turn";
    }
    
    public FaithsShieldEffect(final FaithsShieldEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        if (FatefulHourCondition.getInstance().apply(game, source)) {
            ChoiceColor choice = (ChoiceColor) source.getChoices().get(0);
            FilterCard filter = new FilterCard();
       		filter.setUseColor(true);
			filter.setColor(choice.getColor());
			filter.setMessage(choice.getChoice());
        	filter.setScopeColor(Filter.ComparisonScope.Any);

            Ability ability = new ProtectionAbility(filter) ;
            game.addEffect(new GainAbilityControlledEffect(ability, Duration.EndOfTurn), source);
            game.addEffect(new GainAbilityControllerEffect(ability, Duration.EndOfTurn), source);
        }
        else {
            game.addEffect(new GainProtectionFromColorTargetEffect(Duration.EndOfTurn), source);
        }
        return false;
    }

    @Override
    public FaithsShieldEffect copy() {
        return new FaithsShieldEffect(this);
    }
    
}
