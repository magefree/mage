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
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
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
import mage.game.permanent.token.EldraziHorrorToken;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class OtherworldlyOutburst extends CardImpl {

    public OtherworldlyOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target creature gets +1/+0 until end of turn. When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(1, 0, Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new OtherworldlyOutburstEffect());
    }

    public OtherworldlyOutburst(final OtherworldlyOutburst card) {
        super(card);
    }

    @Override
    public OtherworldlyOutburst copy() {
        return new OtherworldlyOutburst(this);
    }
}

class OtherworldlyOutburstEffect extends OneShotEffect {

    public OtherworldlyOutburstEffect() {
        super(Outcome.UnboostCreature);
        this.staticText = "When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token";
    }

    public OtherworldlyOutburstEffect(final OtherworldlyOutburstEffect effect) {
        super(effect);
    }

    @Override
    public OtherworldlyOutburstEffect copy() {
        return new OtherworldlyOutburstEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addDelayedTriggeredAbility(new OtherworldlyOutburstDelayedTriggeredAbility(source.getFirstTarget()), source);
        return true;
    }
}

class OtherworldlyOutburstDelayedTriggeredAbility extends DelayedTriggeredAbility {

    private final UUID target;

    public OtherworldlyOutburstDelayedTriggeredAbility(UUID target) {
        super(new CreateTokenEffect(new EldraziHorrorToken()), Duration.EndOfTurn);
        this.target = target;
    }

    public OtherworldlyOutburstDelayedTriggeredAbility(OtherworldlyOutburstDelayedTriggeredAbility ability) {
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
    public OtherworldlyOutburstDelayedTriggeredAbility copy() {
        return new OtherworldlyOutburstDelayedTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When that creature dies this turn, create a 3/2 colorless Eldrazi Horror creature token.";
    }
}
