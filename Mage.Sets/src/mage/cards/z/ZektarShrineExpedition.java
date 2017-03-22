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
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.Token;

/**
 *
 * @author North
 */
public class ZektarShrineExpedition extends CardImpl {

    public ZektarShrineExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");

        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Zektar Shrine Expedition.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        // Remove three quest counters from Zektar Shrine Expedition and sacrifice it: Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ZektarShrineExpeditionEffect(), new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public ZektarShrineExpedition(final ZektarShrineExpedition card) {
        super(card);
    }

    @Override
    public ZektarShrineExpedition copy() {
        return new ZektarShrineExpedition(this);
    }
}

class ZektarShrineExpeditionEffect extends OneShotEffect {

    public ZektarShrineExpeditionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 7/1 red Elemental creature token with trample and haste. Exile it at the beginning of the next end step";
    }

    public ZektarShrineExpeditionEffect(final ZektarShrineExpeditionEffect effect) {
        super(effect);
    }

    @Override
    public ZektarShrineExpeditionEffect copy() {
        return new ZektarShrineExpeditionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        CreateTokenEffect effect = new CreateTokenEffect(new ElementalToken());
        if(effect.apply(game, source))
        {
            effect.exileTokensCreatedAtNextEndStep(game, source);            
            return true;
        }
        return false;
    }

    static class ElementalToken extends Token {

        public ElementalToken() {
            super("Elemental", "7/1 red Elemental creature token with trample and haste");
            cardType.add(CardType.CREATURE);
            color.setRed(true);
            subtype.add("Elemental");
            power = new MageInt(7);
            toughness = new MageInt(1);
            addAbility(TrampleAbility.getInstance());
            addAbility(HasteAbility.getInstance());
        }
    }
}
