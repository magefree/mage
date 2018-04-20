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
package mage.cards.a;

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
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public class AetherCharge extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Beast you control");

    static {
        filter.add(new SubtypePredicate(SubType.BEAST));
    }

    public AetherCharge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{R}");

        // Whenever a Beast enters the battlefield under your control, you may have it deal 4 damage to target opponent.
        Ability ability = new AetherChargeTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public AetherCharge(final AetherCharge card) {
        super(card);
    }

    @Override
    public AetherCharge copy() {
        return new AetherCharge(this);
    }
}

class AetherChargeTriggeredAbility extends TriggeredAbilityImpl {

    public AetherChargeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AetherChargeEffect(), true); // is optional
    }

    public AetherChargeTriggeredAbility(AetherChargeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent.isCreature() && permanent.hasSubtype(SubType.BEAST, game)
                && permanent.getControllerId().equals(this.controllerId)) {
            Effect effect = this.getEffects().get(0);
            effect.setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a Beast enters the battlefield under your control, you may have it deal 4 damage to target opponent.";
    }

    @Override
    public AetherChargeTriggeredAbility copy() {
        return new AetherChargeTriggeredAbility(this);
    }
}

class AetherChargeEffect extends OneShotEffect {

    public AetherChargeEffect() {
        super(Outcome.Damage);
        staticText = "you may have it deal 4 damage to target opponent";
    }

    public AetherChargeEffect(final AetherChargeEffect effect) {
        super(effect);
    }

    @Override
    public AetherChargeEffect copy() {
        return new AetherChargeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Zone.BATTLEFIELD);
        }
        if (creature != null) {
            UUID target = source.getTargets().getFirstTarget();
            Player opponent = game.getPlayer(target);
            if (opponent != null) {
                opponent.damage(4, creature.getId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
