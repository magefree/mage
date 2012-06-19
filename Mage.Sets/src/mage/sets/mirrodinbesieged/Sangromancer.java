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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class Sangromancer extends CardImpl<Sangromancer> {

    public Sangromancer (UUID ownerId) {
        super(ownerId, 53, "Sangromancer", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "MBS";
        this.subtype.add("Vampire");
        this.subtype.add("Shaman");
        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SangromancerFirstTriggeredAbility());
        this.addAbility(new SangromancerSecondTriggeredAbility());
    }

    public Sangromancer (final Sangromancer card) {
        super(card);
    }

    @Override
    public Sangromancer copy() {
        return new Sangromancer(this);
    }
}

class SangromancerFirstTriggeredAbility extends TriggeredAbilityImpl<SangromancerFirstTriggeredAbility> {
    SangromancerFirstTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(3), true);
    }

    SangromancerFirstTriggeredAbility(final SangromancerFirstTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SangromancerFirstTriggeredAbility copy() {
        return new SangromancerFirstTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Constants.Zone.BATTLEFIELD);
            if (p != null && p.getCardType().contains(CardType.CREATURE) && game.getOpponents(this.getControllerId()).contains(p.getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature an opponent controls is put into a graveyard from the battlefield, " + super.getRule();
    }
}

class SangromancerSecondTriggeredAbility extends TriggeredAbilityImpl<SangromancerSecondTriggeredAbility> {
    SangromancerSecondTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new GainLifeEffect(3), true);
    }

    SangromancerSecondTriggeredAbility(final SangromancerSecondTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public SangromancerSecondTriggeredAbility copy() {
        return new SangromancerSecondTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.DISCARDED_CARD && game.getOpponents(this.getControllerId()).contains(event.getPlayerId())) {
             return true;
         }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent discards a card, " + super.getRule();
    }
}