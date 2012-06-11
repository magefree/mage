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

package mage.sets.championsofkamigawa;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInHand;

/**
 * @author LevelX
 */
public class HisokaMinamoSensei extends CardImpl<HisokaMinamoSensei> {

    public HisokaMinamoSensei(UUID ownerId) {
        super(ownerId, 66, "Hisoka, Minamo Sensei", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Wizard");
        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);
        
        // {2}{U}, Discard a card: Counter target spell if it has the same converted mana cost as the discarded card.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new HisokaMinamoSenseiCounterEffect(), new ManaCostsImpl("{2}{U}"));
        ability.addTarget(new TargetSpell());
        TargetCardInHand targetCard = new TargetCardInHand(new FilterCard("a card"));
        ability.addCost(new HisokaMinamoSenseiDiscardTargetCost(targetCard));
        this.addAbility(ability);
    }

    public HisokaMinamoSensei(final HisokaMinamoSensei card) {
        super(card);
    }

    @Override
    public HisokaMinamoSensei copy() {
        return new HisokaMinamoSensei(this);
    }

}

class HisokaMinamoSenseiDiscardTargetCost extends CostImpl<HisokaMinamoSenseiDiscardTargetCost> {

        protected int convertedManaCosts = 0;
        
	public HisokaMinamoSenseiDiscardTargetCost(TargetCardInHand target) {
		this.addTarget(target);
		this.text = "Discard " + target.getTargetName();
	}

	public HisokaMinamoSenseiDiscardTargetCost(HisokaMinamoSenseiDiscardTargetCost cost) {
		super(cost);
	}

	@Override
	public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
		if (targets.choose(Outcome.Discard, controllerId, sourceId, game)) {
			Player player = game.getPlayer(controllerId);
			for (UUID targetId: targets.get(0).getTargets()) {
				Card card = player.getHand().get(targetId, game);
				if (card == null)
					return false;
				convertedManaCosts = card.getManaCost().convertedManaCost();
                                paid |= player.discard(card, null, game);
                                
			}
		}
		return paid;
	}

	@Override
	public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
		return targets.canChoose(controllerId, game);
	}

	@Override
	public HisokaMinamoSenseiDiscardTargetCost copy() {
		return new HisokaMinamoSenseiDiscardTargetCost(this);
	}
        
        public int getConvertedCosts() {
            return convertedManaCosts;
        }

}

class HisokaMinamoSenseiCounterEffect extends OneShotEffect<HisokaMinamoSenseiCounterEffect> {
    HisokaMinamoSenseiCounterEffect() {
        super(Constants.Outcome.Detriment);
        staticText = "Counter target spell if it has the same converted mana cost as the discarded card";
    }

    HisokaMinamoSenseiCounterEffect(final HisokaMinamoSenseiCounterEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
        if (spell != null) {
            HisokaMinamoSenseiDiscardTargetCost cost = (HisokaMinamoSenseiDiscardTargetCost) source.getCosts().get(0);
            if (cost != null && cost.getConvertedCosts() == spell.getManaCost().convertedManaCost()) {
                return game.getStack().counter(targetPointer.getFirst(game, source), source.getSourceId(), game);
            }
        }
        return false;
    }

    @Override
    public HisokaMinamoSenseiCounterEffect copy() {
        return new HisokaMinamoSenseiCounterEffect(this);
    }
}