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

package mage.sets.riseoftheeldrazi;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.SubLayer;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class TrainingGrounds extends CardImpl<TrainingGrounds> {

    public TrainingGrounds (UUID ownerId) {
        super(ownerId, 91, "Training Grounds", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{U}");
        this.expansionSetCode = "ROE";
        
		this.color.setBlue(true);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TrainingGroundsCostReductionEffect()));
    }

    public TrainingGrounds (final TrainingGrounds card) {
        super(card);
    }

    @Override
    public TrainingGrounds copy() {
        return new TrainingGrounds(this);
    }
}

class TrainingGroundsCostReductionEffect extends ContinuousEffectImpl<TrainingGroundsCostReductionEffect> {

	private static final String effectText = "Activated abilities of creatures you control cost up to {2} less to activate. This effect can't reduce the amount of mana an ability costs to activate to less than one mana";
	private static final FilterControlledCreaturePermanent filter;

	static {
		filter = new FilterControlledCreaturePermanent();
	}

	TrainingGroundsCostReductionEffect ( ) {
		super(Duration.WhileOnBattlefield, Layer.TextChangingEffects_3, SubLayer.NA, Outcome.Benefit);
	}

	TrainingGroundsCostReductionEffect ( TrainingGroundsCostReductionEffect effect ) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		boolean applied = false;
		List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(filter);

		if ( permanents != null && !permanents.isEmpty() ) {
			for ( Permanent permanent : permanents ) {
				for ( Ability ability : permanent.getAbilities() ) {
					if ( !(ability instanceof SpellAbility) && ability.getManaCosts() != null ) {
						int costCount = ability.getManaCosts().size();
						for ( Cost cost : ability.getManaCosts() ) {
							if ( cost instanceof GenericManaCost ) {
								GenericManaCost costCasted = (GenericManaCost)cost;
								int amount = costCasted.convertedManaCost();
								int adjustedAmount = 0;
								if ( costCount == 1 && (amount - 2) <= 0 ) {
									adjustedAmount = 1;
								}
								else {
									//In case the adjusted amount goes below 0.
									adjustedAmount = Math.max(0, amount - 2);
								}
								costCasted.setMana(adjustedAmount);
								applied = true;
							}
						}
					}
				}
			}
		}
		
		return applied;
	}

	@Override
	public TrainingGroundsCostReductionEffect copy() {
		return new TrainingGroundsCostReductionEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return effectText;
	}
}
