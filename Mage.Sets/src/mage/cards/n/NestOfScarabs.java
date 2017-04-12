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
package mage.cards.n;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.dynamicvalue.common.EffectKeyValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author stravant
 */
public class NestOfScarabs extends CardImpl {

    public NestOfScarabs(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        // Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect tokens.
        this.addAbility(new NestOfScarabsTriggeredAbility());

    }

    public NestOfScarabs(final NestOfScarabs card) {
        super(card);
    }

    @Override
    public NestOfScarabs copy() {
        return new NestOfScarabs(this);
    }
}

class NestOfScarabsTriggeredAbility extends TriggeredAbilityImpl {

    NestOfScarabsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new BlackInsectToken(), new EffectKeyValue("countersAdded")));
    }

    NestOfScarabsTriggeredAbility(final NestOfScarabsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTERS_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        boolean weAreDoingIt = getControllerId().equals(game.getControllerId(event.getSourceId()));
        boolean isM1M1Counters = event.getData().equals(CounterType.M1M1.getName());
        if (weAreDoingIt && isM1M1Counters) {
            Permanent permanent = game.getPermanentOrLKIBattlefield(event.getTargetId());
            if (permanent == null) {
                permanent = game.getPermanentEntering(event.getTargetId());
            }
            if (permanent.isCreature()) {
                getEffects().forEach(effect -> effect.setValue("countersAdded", event.getAmount()));
                return true;
            }
        }
        return false;
    }

    @Override
    public NestOfScarabsTriggeredAbility copy() {
        return new NestOfScarabsTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "Whenever you put one or more -1/-1 counters on a creature, create that many 1/1 black Insect tokens.";
    }
}

class BlackInsectToken extends Token {
    BlackInsectToken() {
        super("Insect", "1/1 black Insect token");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add("Insect");
        power = new MageInt(1);
        toughness = new MageInt(1);
    }
}
