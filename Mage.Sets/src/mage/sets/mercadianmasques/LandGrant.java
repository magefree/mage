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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Plopman
 */
public class LandGrant extends CardImpl {

    private static final FilterLandCard filter = new FilterLandCard("Forest card");
    
    static {
        filter.add(new SubtypePredicate("Forest"));
    }
    
    public LandGrant(UUID ownerId) {
        super(ownerId, 255, "Land Grant", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "MMQ";


        // If you have no land cards in hand, you may reveal your hand rather than pay Land Grant's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new LandGrantReavealCost(), new LandGrantCondition(),
            "If you have no land cards in hand, you may reveal your hand rather than pay {this}'s mana cost."));

        // Search your library for a Forest card, reveal that card, and put it into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true, true));
    }

    public LandGrant(final LandGrant card) {
        super(card);
    }

    @Override
    public LandGrant copy() {
        return new LandGrant(this);
    }
}

class LandGrantCondition implements Condition {
 
    @Override
    public boolean apply(Game game, Ability source) {   
        Player player = game.getPlayer(source.getControllerId());
        if (player != null && player.getHand().count(new FilterLandCard(), game) == 0) {
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "If you have no land cards in hand";
    }
}

class LandGrantReavealCost extends CostImpl {

    public LandGrantReavealCost() {
        this.text = "reveal your hand";
    }

    public LandGrantReavealCost(LandGrantReavealCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        Player player = game.getPlayer(controllerId);
        if (player != null) {
            player.revealCards("Land Grant", player.getHand(), game);
            paid = true;
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, UUID sourceId, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public LandGrantReavealCost copy() {
        return new LandGrantReavealCost(this);
    }

}
