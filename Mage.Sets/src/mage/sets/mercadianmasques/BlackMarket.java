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
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author markedagain
 */
public class BlackMarket extends CardImpl {

    public BlackMarket(UUID ownerId) {
        super(ownerId, 116, "Black Market", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}{B}");
        this.expansionSetCode = "MMQ";

        // Whenever a creature dies, put a charge counter on Black Market.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.CHARGE.createInstance()),false ));
        // At the beginning of your precombat main phase, add {B} to your mana pool for each charge counter on Black Market.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(new BlackMarketEffect(), TargetController.YOU, false));
        
    }

    public BlackMarket(final BlackMarket card) {
        super(card);
    }

    @Override
    public BlackMarket copy() {
        return new BlackMarket(this);
    }
}
class BlackMarketEffect extends OneShotEffect {

    public BlackMarketEffect() {
        super(Outcome.PutManaInPool);
        this.staticText = "add {B} to your mana pool for each charge counter on Black Market";
    }

    public BlackMarketEffect(final BlackMarketEffect effect) {
        super(effect);
    }

    @Override
    public BlackMarketEffect copy() {
        return new BlackMarketEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        Player player = game.getPlayer(source.getControllerId());
        if (sourcePermanent != null && player != null) {
            int chargeCounters = sourcePermanent.getCounters().getCount(CounterType.CHARGE);
            if (chargeCounters > 0){
                player.getManaPool().addMana(Mana.BlackMana(chargeCounters), game, source);
                return true;
            }
        }
        return false;
    }
}
