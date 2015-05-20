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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceIsSpellCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.DynamicCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * @author LevelX2
 */
public class KentaroTheSmilingCat extends CardImpl {

    public KentaroTheSmilingCat(UUID ownerId) {
        super(ownerId, 13, "Kentaro, the Smiling Cat", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{W}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Samurai");

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bushido 1 (When this blocks or becomes blocked, it gets +1/+1 until end of turn.)
        this.addAbility(new BushidoAbility(1));
        
        // You may pay {X} rather than pay the mana cost for Samurai spells you cast, where X is that spell's converted mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KentaroTheSmilingCatCastingEffect()));
        

    }

    public KentaroTheSmilingCat(final KentaroTheSmilingCat card) {
        super(card);
    }

    @Override
    public KentaroTheSmilingCat copy() {
        return new KentaroTheSmilingCat(this);
    }
}  

class KentaroTheSmilingCatCastingEffect extends ContinuousEffectImpl {
	
	private static final FilterCard filterSamurai = new FilterCard();
	
	static {
		filterSamurai.add(new SubtypePredicate("Samurai"));
	}
	
    static final AlternativeCostSourceAbility alternativeCastingCostAbility = new AlternativeCostSourceAbility(
    		SourceIsSpellCondition.getInstance(), null, filterSamurai, true, new ColorlessConvertedManaCost());
	
    public KentaroTheSmilingCatCastingEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may pay {X} rather than pay the mana cost for Samurai spells you cast, where X is that spell's converted mana cost";
    }
    
    public KentaroTheSmilingCatCastingEffect(final KentaroTheSmilingCatCastingEffect effect) {
        super(effect);
    }

    @Override
    public KentaroTheSmilingCatCastingEffect copy() {
        return new KentaroTheSmilingCatCastingEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.getAlternativeSourceCosts().add(alternativeCastingCostAbility);
            return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}

class ColorlessConvertedManaCost implements DynamicCost {

	@Override
	public Cost getCost(Ability ability, Game game) {
		return new GenericManaCost(ability.getManaCosts().convertedManaCost());
	}

	@Override
	public String getText(Ability ability, Game game) {
		return "Pay " + getCost(ability, game).getText() + " rather than " + ability.getManaCosts().getText() + " for Samurai card?";
	}
}