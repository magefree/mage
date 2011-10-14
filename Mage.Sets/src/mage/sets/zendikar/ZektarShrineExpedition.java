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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class ZektarShrineExpedition extends CardImpl<ZektarShrineExpedition> {

    public ZektarShrineExpedition(UUID ownerId) {
        super(ownerId, 155, "Zektar Shrine Expedition", Rarity.COMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}");
        this.expansionSetCode = "ZEN";

        this.color.setRed(true);

        // Landfall - Whenever a land enters the battlefield under your control, you may put a quest counter on Zektar Shrine Expedition.
        this.addAbility(new LandfallAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        // Remove three quest counters from Zektar Shrine Expedition and sacrifice it: Put a 7/1 red Elemental creature token with trample and haste onto the battlefield. Exile it at the beginning of the next end step.
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

class ZektarShrineExpeditionEffect extends OneShotEffect<ZektarShrineExpeditionEffect> {

    public ZektarShrineExpeditionEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put a 7/1 red Elemental creature token with trample and haste onto the battlefield. Exile it at the beginning of the next end step";
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
        ElementalToken token = new ElementalToken();
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());

        ExileTargetEffect exileEffect = new ExileTargetEffect();
        exileEffect.setTargetPointer(new FixedTarget(token.getLastAddedToken()));
        DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(exileEffect);
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);

        return true;
    }
}

class ElementalToken extends Token {

    public ElementalToken() {
        super("Elemental", "7/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color = ObjectColor.RED;
        subtype.add("Elemental");
        power = new MageInt(7);
        toughness = new MageInt(1);
        addAbility(TrampleAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }
}
