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
package mage.sets.nemesis;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostImpl;
import mage.abilities.costs.CostImpl;
import mage.abilities.effects.common.continious.BoostAllEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public class Massacre extends CardImpl<Massacre> {

    public Massacre(UUID ownerId) {
        super(ownerId, 58, "Massacre", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");
        this.expansionSetCode = "NMS";

        this.color.setBlack(true);

        // If an opponent controls a Plains and you control a Swamp, you may cast Massacre without paying its mana cost.
        this.getSpellAbility().addAlternativeCost(new AlternativeCostImpl("If an opponent controls a Plains and you control a Swamp, you may cast Massacre without paying its mana cost", new MassacreCost()));
        // All creatures get -2/-2 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Constants.Duration.EndOfTurn));
    }

    public Massacre(final Massacre card) {
        super(card);
    }

    @Override
    public Massacre copy() {
        return new Massacre(this);
    }
}

class MassacreCost extends CostImpl<MassacreCost> {

    
    
    public MassacreCost() {
        this.text = "If an opponent controls a Plains and you control a Swamp, you may cast Massacre without paying its mana cost.";
    }

    public MassacreCost(MassacreCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, UUID sourceId, UUID controllerId, boolean noMana) {
        paid = canPay(sourceId, controllerId, game);
        return paid;
    }

    @Override
    public boolean canPay(UUID sourceId, UUID controllerId, Game game) {
        FilterControlledLandPermanent filterSwamp = new FilterControlledLandPermanent("Swamp");
        filterSwamp.add(new SubtypePredicate("Swamp"));
        FilterControlledLandPermanent filterPlains = new FilterControlledLandPermanent("Plains");
        filterPlains.add(new SubtypePredicate("Plains"));
        if (game.getBattlefield().contains(filterSwamp, controllerId, 1, game)) {
            for(UUID uuid : game.getOpponents(controllerId)){
                if(game.getBattlefield().contains(filterPlains, uuid, 1, game)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public MassacreCost copy() {
        return new MassacreCost(this);
    }

}