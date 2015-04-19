/*
 *  Copyright 2011 BetaSteward_at_googlemail.com. All rights reserved.
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
package mage.sets.scarsofmirrodin;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PainfulQuandary extends CardImpl {

    public PainfulQuandary(UUID ownerId) {
        super(ownerId, 73, "Painful Quandary", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "SOM";

        // Whenever an opponent casts a spell, that player loses 5 life unless he or she discards a card.
        this.addAbility(new SpellCastOpponentTriggeredAbility(Zone.BATTLEFIELD, new PainfulQuandryEffect(), new FilterSpell(), false, SetTargetPointer.PLAYER));
    }

    public PainfulQuandary(final PainfulQuandary card) {
        super(card);
    }

    @Override
    public PainfulQuandary copy() {
        return new PainfulQuandary(this);
    }

}

class PainfulQuandryEffect extends OneShotEffect {

    public PainfulQuandryEffect() {
        super(Outcome.LoseLife);
        staticText = "that player loses 5 life unless he or she discards a card";
    }

    public PainfulQuandryEffect(final PainfulQuandryEffect effect) {
        super(effect);
    }

    @Override
    public PainfulQuandryEffect copy() {
        return new PainfulQuandryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null) {
            boolean paid = false;
            Cost cost = new DiscardTargetCost(new TargetCardInHand());
            if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                    && player.chooseUse(Outcome.Detriment, "Discard a card (otherwise you lose 5 life)?", game)) {
                paid = cost.pay(source, game, source.getSourceId(), player.getId(), false);
            }
            if (!paid) {
                player.loseLife(5, game);
            }
            return true;
        }
        return false;
    }

}
