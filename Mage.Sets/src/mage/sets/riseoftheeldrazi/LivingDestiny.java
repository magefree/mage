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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.abilities.costs.common.RevealTargetFromHandCost;

/**
 *
 * @author jeffwadsworth
 */
public class LivingDestiny extends CardImpl<LivingDestiny> {

    public LivingDestiny(UUID ownerId) {
        super(ownerId, 195, "Living Destiny", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{G}");
        this.expansionSetCode = "ROE";

        this.color.setGreen(true);

        // As an additional cost to cast Living Destiny, reveal a creature card from your hand.
        TargetCardInHand targetCard = new TargetCardInHand(new FilterCreatureCard("a creature card"));
        this.getSpellAbility().addCost(new RevealTargetFromHandCost(targetCard));
       
        // You gain life equal to the revealed card's converted mana cost.
        this.getSpellAbility().addEffect(new LivingDestinyEffect());
    }

    public LivingDestiny(final LivingDestiny card) {
        super(card);
    }

    @Override
    public LivingDestiny copy() {
        return new LivingDestiny(this);
    }
}

class LivingDestinyEffect extends OneShotEffect<LivingDestinyEffect> {

    public LivingDestinyEffect() {
        super(Constants.Outcome.GainLife);
        staticText = "You gain life equal to its converted mana cost";
    }

    public LivingDestinyEffect(LivingDestinyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        RevealTargetFromHandCost cost = (RevealTargetFromHandCost) source.getCosts().get(0);
        if (cost != null) {
            Player player = game.getPlayer(source.getControllerId());
            int CMC = cost.convertedManaCosts;
            if (player != null) {
                player.gainLife(CMC, game);
            }
        }
        return true;
    }

    @Override
    public LivingDestinyEffect copy() {
        return new LivingDestinyEffect(this);
    }

}