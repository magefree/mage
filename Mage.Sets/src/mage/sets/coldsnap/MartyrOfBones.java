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
package mage.sets.coldsnap;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public class MartyrOfBones extends CardImpl {

    public MartyrOfBones(UUID ownerId) {
        super(ownerId, 65, "Martyr of Bones", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{B}");
        this.expansionSetCode = "CSP";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {1}, Reveal X black cards from your hand, Sacrifice Martyr of Bones: Exile up to X target cards from a single graveyard.
        Effect effect = new ExileTargetEffect(null, null, Zone.GRAVEYARD);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new GenericManaCost(1));
        ability.addCost(new RevealVariableBlackCardsFromHandCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 1, new FilterCard("cards in a single graveyard")));
        this.addAbility(ability);
    }
    
    public MartyrOfBones(final MartyrOfBones card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SimpleActivatedAbility) {
            int amount = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof RevealVariableBlackCardsFromHandCost) {
                    amount = ((VariableCost) cost).getAmount();
                }
            }
            ability.getTargets().clear();
            ability.addTarget(new TargetCardInASingleGraveyard(0, amount, new FilterCard()));
        }
    }

    @Override
    public MartyrOfBones copy() {
        return new MartyrOfBones(this);
    }
}

class RevealVariableBlackCardsFromHandCost extends VariableCostImpl {
    
    private static final FilterCard filter = new FilterCard("X black cards from your hand");
    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }
    
    RevealVariableBlackCardsFromHandCost() {
        super("black cards to reveal");
        this.text = new StringBuilder("Reveal ").append(xText).append(" black cards from {this}").toString();
    }
    
    RevealVariableBlackCardsFromHandCost(final RevealVariableBlackCardsFromHandCost cost) {
        super(cost);
    }
   
    @Override
    public RevealVariableBlackCardsFromHandCost copy() {
        return new RevealVariableBlackCardsFromHandCost(this);
    }
    
    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        return new RevealTargetFromHandCost(new TargetCardInHand(0, xValue, filter));
    }
    
    @Override
    public int getMinValue(Ability source, Game game) {
        return 0;
    }
    
    @Override
    public int getMaxValue(Ability source, Game game) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            return player.getHand().getCards(filter, game).size();
        }
        return 0;
    }
}