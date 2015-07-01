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
package mage.sets.magic2015;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author emerald000
 */
public class IndulgentTormentor extends CardImpl {

    public IndulgentTormentor(UUID ownerId) {
        super(ownerId, 101, "Indulgent Tormentor", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "M15";
        this.subtype.add("Demon");

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // At the beginning of your upkeep, draw a card unless target opponent sacrifices a creature or pays 3 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new IndulgentTormentorEffect(), TargetController.YOU, false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public IndulgentTormentor(final IndulgentTormentor card) {
        super(card);
    }

    @Override
    public IndulgentTormentor copy() {
        return new IndulgentTormentor(this);
    }
}

class IndulgentTormentorEffect extends OneShotEffect {
    
    IndulgentTormentorEffect() {
        super(Outcome.DrawCard);
        this.staticText = "draw a card unless target opponent sacrifices a creature or pays 3 life";
    }
    
    IndulgentTormentorEffect(final IndulgentTormentorEffect effect) {
        super(effect);
    }
    
    @Override
    public IndulgentTormentorEffect copy() {
        return new IndulgentTormentorEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(source.getFirstTarget());
        if (opponent != null) {
            Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent());
            if (cost.canPay(source, source.getSourceId(), opponent.getId(), game)
                    && opponent.chooseUse(outcome, "Sacrifice a creature to prevent the card draw?", source, game)) {
                if (cost.pay(source, game, source.getSourceId(), opponent.getId(), false)) {
                    return true;
                }
            }
            cost = new PayLifeCost(3);
            if (cost.canPay(source, source.getSourceId(), opponent.getId(), game)
                    && opponent.chooseUse(outcome, "Pay 3 life to prevent the card draw?", source, game)) {
                if (cost.pay(source, game, source.getSourceId(), opponent.getId(), false)) {
                    return true;
                }
            }
            game.getPlayer(source.getControllerId()).drawCards(1, game);
            return true;
        }
        return false;
    }
}
