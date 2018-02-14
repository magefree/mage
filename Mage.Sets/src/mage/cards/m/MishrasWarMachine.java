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
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.BandingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth & L_J
 */
public class MishrasWarMachine extends CardImpl {
    
    public MishrasWarMachine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");        
        this.subtype.add(SubType.JUGGERNAUT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Banding
        this.addAbility(BandingAbility.getInstance());

        // At the beginning of your upkeep, Mishra's War Machine deals 3 damage to you unless you discard a card. If Mishra's War Machine deals damage to you this way, tap it.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new MishrasWarMachineEffect(), TargetController.YOU, false));
        
    }
    
    public MishrasWarMachine(final MishrasWarMachine card) {
        super(card);
    }
    
    @Override
    public MishrasWarMachine copy() {
        return new MishrasWarMachine(this);
    }
}

class MishrasWarMachineEffect extends OneShotEffect {
    
    public MishrasWarMachineEffect() {
        super(Outcome.Sacrifice);
        staticText = "{this} deals 3 damage to you unless you discard a card. If Mishra's War Machine deals damage to you this way, tap it";
    }
    
    public MishrasWarMachineEffect(final MishrasWarMachineEffect effect) {
        super(effect);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null
                && sourcePermanent != null) {
            DiscardCardCost cost = new DiscardCardCost();
            if (controller.chooseUse(Outcome.Benefit, "Do you wish to discard a card to prevent the 3 damage to you?", source, game)
                    && cost.canPay(source, source.getSourceId(), source.getControllerId(), game)
                    && cost.pay(source, game, source.getSourceId(), source.getControllerId(), true)) {
                return true;
            }
            if (controller.damage(3, sourcePermanent.getId(), game, false, true) > 0) {
                sourcePermanent.tap(game);
                return true;
            }
        }
        return false;
    }
    
    @Override
    public MishrasWarMachineEffect copy() {
        return new MishrasWarMachineEffect(this);
    }
}
