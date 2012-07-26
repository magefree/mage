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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.abilities.Ability;
import mage.abilities.costs.common.RevealTargetFromHandCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class DisasterRadius extends CardImpl<DisasterRadius> {

    public DisasterRadius(UUID ownerId) {
        super(ownerId, 141, "Disaster Radius", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{5}{R}{R}");
        this.expansionSetCode = "ROE";

        this.color.setRed(true);

        // As an additional cost to cast Disaster Radius, reveal a creature card from your hand.
        TargetCardInHand targetCard = new TargetCardInHand(new FilterCreatureCard("a creature card."));
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(targetCard));
        
        // Disaster Radius deals X damage to each creature your opponents control, where X is the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new DisasterRadiusEffect());
    }

    public DisasterRadius(final DisasterRadius card) {
        super(card);
    }

    @Override
    public DisasterRadius copy() {
        return new DisasterRadius(this);
    }
}

class DisasterRadiusEffect extends OneShotEffect<DisasterRadiusEffect> {
    
    final private static FilterCreaturePermanent filter = new FilterCreaturePermanent("creature your opponents control");
    
    static {
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public DisasterRadiusEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "{this} deals X damage to each creature your opponents control, where X is the revealed card's converted mana cost";
    }

    public DisasterRadiusEffect(DisasterRadiusEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        if (cost != null) {
            int damage = cost.convertedManaCosts;
            for (Permanent creature : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game)) {
                if (creature != null) {
                    creature.damage(damage, source.getId(), game, false, false);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public DisasterRadiusEffect copy() {
        return new DisasterRadiusEffect(this);
    }

}
