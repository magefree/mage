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
package mage.sets.worldwake;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 * @author Loki
 */
public class ArchonOfRedemption extends CardImpl<ArchonOfRedemption> {

    public ArchonOfRedemption(UUID ownerId) {
        super(ownerId, 3, "Archon of Redemption", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "WWK";
        this.subtype.add("Archon");

        this.color.setWhite(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Archon of Redemption or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power.
        this.addAbility(new ArchonOfRedemptionTriggeredAbility());
    }

    public ArchonOfRedemption(final ArchonOfRedemption card) {
        super(card);
    }

    @Override
    public ArchonOfRedemption copy() {
        return new ArchonOfRedemption(this);
    }
}

class ArchonOfRedemptionTriggeredAbility extends TriggeredAbilityImpl<ArchonOfRedemptionTriggeredAbility> {
    ArchonOfRedemptionTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new ArchonOfRedemptionEffect(), true);
    }

    ArchonOfRedemptionTriggeredAbility(final ArchonOfRedemptionTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ArchonOfRedemptionTriggeredAbility copy() {
        return new ArchonOfRedemptionTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            UUID targetId = event.getTargetId();
            Permanent permanent = game.getPermanent(targetId);
            if (permanent.getControllerId().equals(this.controllerId)
                    && permanent.getCardType().contains(CardType.CREATURE)
                    && (targetId.equals(this.getSourceId())
                    || (permanent.getAbilities().contains(FlyingAbility.getInstance()) && !targetId.equals(this.getSourceId())))) {
                for (Effect effect : this.getEffects()) {
                    effect.setTargetPointer(new FixedTarget(event.getTargetId()));
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another creature with flying enters the battlefield under your control, you may gain life equal to that creature's power";
    }
}

class ArchonOfRedemptionEffect extends OneShotEffect<ArchonOfRedemptionEffect> {
    ArchonOfRedemptionEffect() {
        super(Constants.Outcome.GainLife);
    }

    ArchonOfRedemptionEffect(final ArchonOfRedemptionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent p = game.getPermanent(targetPointer.getFirst(game, source));
        Player player = game.getPlayer(source.getControllerId());
        if (p == null) {
            p = (Permanent) game.getLastKnownInformation(targetPointer.getFirst(game, source), Constants.Zone.BATTLEFIELD);
        }
        if (p != null && player != null) {
            player.gainLife(p.getPower().getValue(), game);
            return true;
        }
        return false;
    }

    @Override
    public ArchonOfRedemptionEffect copy() {
        return new ArchonOfRedemptionEffect(this);
    }
}