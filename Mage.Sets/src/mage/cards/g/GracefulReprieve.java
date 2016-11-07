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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public class GracefulReprieve extends CardImpl {

    public GracefulReprieve(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // When target creature dies this turn, return that card to the battlefield under its owner's control.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new GracefulReprieveEffect());
        
    }

    public GracefulReprieve(final GracefulReprieve card) {
        super(card);
    }

    @Override
    public GracefulReprieve copy() {
        return new GracefulReprieve(this);
    }
}

class GracefulReprieveEffect extends OneShotEffect {

    public GracefulReprieveEffect() {
        super(Outcome.Benefit);
        this.staticText = "When target creature dies this turn, return that card to the battlefield under its owner's control";
    }

    public GracefulReprieveEffect(final GracefulReprieveEffect effect) {
        super(effect);
    }

    @Override
    public GracefulReprieveEffect copy() {
        return new GracefulReprieveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DelayedTriggeredAbility delayedAbility = new GracefulReprieveDelayedTriggeredAbility(targetPointer.getFirst(game, source));
        game.addDelayedTriggeredAbility(delayedAbility, source);
        return true;
    }
}

class GracefulReprieveDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private UUID target;

    public GracefulReprieveDelayedTriggeredAbility(UUID target) {
        super(new GracefulReprieveDelayedEffect(target), Duration.EndOfTurn);
        this.target = target;
    }

    public GracefulReprieveDelayedTriggeredAbility(GracefulReprieveDelayedTriggeredAbility ability) {
        super(ability);
        this.target = ability.target;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(target)) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD && zEvent.getToZone() == Zone.GRAVEYARD) {
                return true;
            }
        }
        return false;
    }

    @Override
    public GracefulReprieveDelayedTriggeredAbility copy() {
        return new GracefulReprieveDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When target creature dies this turn, return that card to the battlefield under its owner's control.";
    }
}

class GracefulReprieveDelayedEffect extends OneShotEffect {

    private final UUID target;

    public GracefulReprieveDelayedEffect(UUID target) {
        super(Outcome.PutCreatureInPlay);
        this.target = target;
        this.staticText = "return that card to the battlefield under its owner's control";
    }

    public GracefulReprieveDelayedEffect(final GracefulReprieveDelayedEffect effect) {
        super(effect);
        this.target = effect.target;
    }

    @Override
    public GracefulReprieveDelayedEffect copy() {
        return new GracefulReprieveDelayedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = (Permanent) game.getLastKnownInformation(target, Zone.BATTLEFIELD);
        if (controller != null
                && permanent != null) {
            Player player = game.getPlayer(permanent.getOwnerId());
            if (player != null) {
                Card card = game.getCard(target);
                if (card != null && game.getState().getZone(card.getId()).equals(Zone.GRAVEYARD)) {
                    return card.putOntoBattlefield(game, Zone.GRAVEYARD, source.getSourceId(), player.getId());
                }
                return true;
            }
        }
        return false;
    }
}
