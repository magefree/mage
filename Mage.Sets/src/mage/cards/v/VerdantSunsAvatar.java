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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author spjspj
 */
public class VerdantSunsAvatar extends CardImpl {

    public VerdantSunsAvatar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // When Verdant Sun's Avatar or another creature enters the battlefield under your control, you gain life equal to that creature's toughness.
        this.addAbility(new VerdantSunsAvatarTriggeredAbility());
    }

    public VerdantSunsAvatar(final VerdantSunsAvatar card) {
        super(card);
    }

    @Override
    public VerdantSunsAvatar copy() {
        return new VerdantSunsAvatar(this);
    }
}

class VerdantSunsAvatarTriggeredAbility extends TriggeredAbilityImpl {

    public VerdantSunsAvatarTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VerdantSunsAvatarEffect(), false);
    }

    public VerdantSunsAvatarTriggeredAbility(VerdantSunsAvatarTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.isCreature()
                && permanent.getControllerId().equals(this.controllerId)) {
            Effect effect = this.getEffects().get(0);
            // Life is determined during resolution so it has to be retrieved there (e.g. Giant Growth before resolution)
            effect.setValue("lifeSource", event.getTargetId());
            effect.setValue("zoneChangeCounter", permanent.getZoneChangeCounter(game));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} or another creature enters the battlefield under your control, " + super.getRule();
    }

    @Override
    public VerdantSunsAvatarTriggeredAbility copy() {
        return new VerdantSunsAvatarTriggeredAbility(this);
    }
}

class VerdantSunsAvatarEffect extends OneShotEffect {

    public VerdantSunsAvatarEffect() {
        super(Outcome.GainLife);
        staticText = "you gain life equal to that creature's toughness";
    }

    public VerdantSunsAvatarEffect(final VerdantSunsAvatarEffect effect) {
        super(effect);
    }

    @Override
    public VerdantSunsAvatarEffect copy() {
        return new VerdantSunsAvatarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("lifeSource");
        Integer zoneChangeCounter = (Integer) getValue("zoneChangeCounter");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null || creature.getZoneChangeCounter(game) != zoneChangeCounter) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD, zoneChangeCounter);
        }
        if (creature != null) {
            int amount = creature.getToughness().getValue();
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(amount, game);
            }
            return true;
        }
        return false;
    }
}
