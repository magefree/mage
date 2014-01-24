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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SearingBlood extends CardImpl<SearingBlood> {

    public SearingBlood(UUID ownerId) {
        super(ownerId, 111, "Searing Blood", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{R}{R}");
        this.expansionSetCode = "BNG";

        this.color.setRed(true);

        // Searing Blood deals 2 damage to target creature. When that creature dies this turn, Searing Blood deals 3 damage to that creature's controller.
        this.getSpellAbility().addEffect(new SearingBloodEffect());
    }

    public SearingBlood(final SearingBlood card) {
        super(card);
    }

    @Override
    public SearingBlood copy() {
        return new SearingBlood(this);
    }
}

class SearingBloodEffect extends OneShotEffect<SearingBloodEffect> {

    public SearingBloodEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 2 damage to target creature. When that creature dies this turn, {this} deals 3 damage to that creature's controller";
    }

    public SearingBloodEffect(final SearingBloodEffect effect) {
        super(effect);
    }

    @Override
    public SearingBloodEffect copy() {
        return new SearingBloodEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new SearingBloodDelayedTriggeredAbility(source.getFirstTarget());
        delayedAbility.setSourceId(source.getSourceId());
        delayedAbility.setControllerId(source.getControllerId());
        game.addDelayedTriggeredAbility(delayedAbility);

        return new DamageTargetEffect(2).apply(game, source);
    }
}

class SearingBloodDelayedTriggeredAbility extends DelayedTriggeredAbility<SearingBloodDelayedTriggeredAbility> {

    private UUID target;

    public SearingBloodDelayedTriggeredAbility(UUID target) {
        super(new SearingBloodDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public SearingBloodDelayedTriggeredAbility(SearingBloodDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.ZONE_CHANGE && event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public SearingBloodDelayedTriggeredAbility copy() {
        return new SearingBloodDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, {this} deals 3 damage to that creature's controller.";
    }
}

class SearingBloodDelayedEffect extends OneShotEffect<SearingBloodDelayedEffect> {

    protected UUID target;

    public SearingBloodDelayedEffect(UUID target) {
        super(Outcome.Damage);
        this.target = target;
    }

    public SearingBloodDelayedEffect(final SearingBloodDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public SearingBloodDelayedEffect copy() {
        return new SearingBloodDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (permanent != null) {
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.damage(3, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
