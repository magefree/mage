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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author BetaSteward
 */
public class ThaliaGuardianOfThraben extends CardImpl<ThaliaGuardianOfThraben> {

    public ThaliaGuardianOfThraben(UUID ownerId) {
        super(ownerId, 24, "Thalia, Guardian of Thraben", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "DKA";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        this.addAbility(FirstStrikeAbility.getInstance());
        
        // Noncreature spells cost {1} more to cast.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new ThaliaGuardianOfThrabenCostReductionEffect()));
        
    }

    public ThaliaGuardianOfThraben(final ThaliaGuardianOfThraben card) {
        super(card);
    }

    @Override
    public ThaliaGuardianOfThraben copy() {
        return new ThaliaGuardianOfThraben(this);
    }
}

class ThaliaGuardianOfThrabenCostReductionEffect extends CostModificationEffectImpl<ThaliaGuardianOfThrabenCostReductionEffect> {

	ThaliaGuardianOfThrabenCostReductionEffect ( ) {
		super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
		staticText = "Noncreature spells cost {1} more to cast";
	}

	ThaliaGuardianOfThrabenCostReductionEffect(ThaliaGuardianOfThrabenCostReductionEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source, Ability abilityToModify) {
		SpellAbility spellAbility = (SpellAbility) abilityToModify;
		spellAbility.getManaCostsToPay().add(new GenericManaCost(1));
		return true;
	}
	
	@Override
	public boolean applies(Ability abilityToModify, Ability source, Game game) {
		if (abilityToModify instanceof SpellAbility) {
			Card card = game.getCard(abilityToModify.getSourceId());
			if (card != null && !card.getCardType().contains(CardType.CREATURE)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ThaliaGuardianOfThrabenCostReductionEffect copy() {
		return new ThaliaGuardianOfThrabenCostReductionEffect(this);
	}

}
