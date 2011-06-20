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

package mage.sets.conflux;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author Loki
 */
public class WallofReverence extends CardImpl<WallofReverence> {

    public WallofReverence (UUID ownerId) {
        super(ownerId, 20, "Wall of Reverence", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}");
        this.expansionSetCode = "CON";
        this.subtype.add("Spirit");
        this.subtype.add("Wall");
		this.color.setWhite(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(6);
        this.addAbility(DefenderAbility.getInstance());
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new WallofReverenceTriggeredAbility());
    }

    public WallofReverence (final WallofReverence card) {
        super(card);
    }

    @Override
    public WallofReverence copy() {
        return new WallofReverence(this);
    }
}

class WallofReverenceTriggeredAbility extends TriggeredAbilityImpl<WallofReverenceTriggeredAbility> {
    WallofReverenceTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new WallofReverenceTriggeredEffect(), true);
        this.addTarget(new TargetControlledCreaturePermanent());
    }

    WallofReverenceTriggeredAbility(final WallofReverenceTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public WallofReverenceTriggeredAbility copy() {
        return new WallofReverenceTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
     if (event.getType() == GameEvent.EventType.END_PHASE_PRE && event.getPlayerId().equals(this.controllerId)) {
			return true;
		}
		return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your end step, " + effects.getText(this);
    }
}

class WallofReverenceTriggeredEffect extends OneShotEffect<WallofReverenceTriggeredEffect> {
    WallofReverenceTriggeredEffect() {
        super(Constants.Outcome.GainLife);
    }

    WallofReverenceTriggeredEffect(WallofReverenceTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent target = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (target != null && player != null) {
            player.gainLife(target.getPower().getValue(), game);
            return true;
        }
        return false;
    }

    @Override
    public WallofReverenceTriggeredEffect copy() {
        return new WallofReverenceTriggeredEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "you may gain life equal to the power of target creature you control";
    }
}