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
package mage.sets.darkascension;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.UndyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

import java.util.UUID;
import mage.game.events.EntersTheBattlefieldEvent;

/**
 *
 * @author BetaSteward
 */
public class FlayerOfTheHatebound extends CardImpl<FlayerOfTheHatebound> {

    public FlayerOfTheHatebound(UUID ownerId) {
        super(ownerId, 89, "Flayer of the Hatebound", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}");
        this.expansionSetCode = "DKA";
        this.subtype.add("Devil");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(new UndyingAbility());

        // Whenever Flayer of the Hatebound or another creature enters the battlefield from your graveyard, that creature deals damage equal to its power to target creature or player.
        Ability ability = new FlayerTriggeredAbility();
        ability.addTarget(new TargetCreatureOrPlayer(true));
        this.addAbility(ability);
    }

    public FlayerOfTheHatebound(final FlayerOfTheHatebound card) {
        super(card);
    }

    @Override
    public FlayerOfTheHatebound copy() {
        return new FlayerOfTheHatebound(this);
    }
}

class FlayerTriggeredAbility extends TriggeredAbilityImpl<FlayerTriggeredAbility> {

    public FlayerTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new FlayerEffect(), false);
    }

    public FlayerTriggeredAbility(FlayerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (((EntersTheBattlefieldEvent) event).getFromZone() == Constants.Zone.GRAVEYARD
                    && permanent.getOwnerId().equals(controllerId)
                    && permanent.getCardType().contains(CardType.CREATURE)) {
                Effect effect = this.getEffects().get(0);
                effect.setValue("damageSource", event.getTargetId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever Flayer of the Hatebound or another creature enters the battlefield from your graveyard, that creature deals damage equal to its power to target creature or player.";
    }

    @Override
    public FlayerTriggeredAbility copy() {
        return new FlayerTriggeredAbility(this);
    }
}

class FlayerEffect extends OneShotEffect<FlayerEffect> {

    public FlayerEffect() {
        super(Constants.Outcome.Damage);
        staticText = "that creature deals damage equal to its power to target creature or player";
    }

    public FlayerEffect(final FlayerEffect effect) {
        super(effect);
    }

    @Override
    public FlayerEffect copy() {
        return new FlayerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanent(creatureId);
        if (creature == null) {
            creature = (Permanent) game.getLastKnownInformation(creatureId, Constants.Zone.BATTLEFIELD);
        }
        if (creature != null) {
            int amount = creature.getPower().getValue();
            UUID target = source.getTargets().getFirstTarget();
            Permanent targetCreature = game.getPermanent(target);
            if (targetCreature != null) {
                targetCreature.damage(amount, creature.getId(), game, true, false);
                return true;
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(amount, creature.getId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}
