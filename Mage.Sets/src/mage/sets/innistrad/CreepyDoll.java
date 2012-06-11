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
package mage.sets.innistrad;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author nantuko
 */
public class CreepyDoll extends CardImpl<CreepyDoll> {

    public CreepyDoll(UUID ownerId) {
        super(ownerId, 220, "Creepy Doll", Rarity.RARE, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Construct");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Creepy Doll is indestructible.
        this.addAbility(IndestructibleAbility.getInstance());

        // Whenever Creepy Doll deals combat damage to a creature, flip a coin. If you win the flip, destroy that creature.
        this.addAbility(new CreepyDollTriggeredAbility());
    }

    public CreepyDoll(final CreepyDoll card) {
        super(card);
    }

    @Override
    public CreepyDoll copy() {
        return new CreepyDoll(this);
    }
}

class CreepyDollTriggeredAbility extends TriggeredAbilityImpl<CreepyDollTriggeredAbility> {

    CreepyDollTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new CreepyDollEffect());
    }

    CreepyDollTriggeredAbility(final CreepyDollTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public CreepyDollTriggeredAbility copy() {
        return new CreepyDollTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedCreatureEvent) {
            if (((DamagedCreatureEvent) event).isCombatDamage() && event.getSourceId().equals(sourceId)) {
                getEffects().get(0).setTargetPointer(new FixedTarget(event.getTargetId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} deals combat damage to a creature, flip a coin. If you win the flip, destroy that creature.";
    }
}

class CreepyDollEffect extends OneShotEffect<CreepyDollEffect> {

    CreepyDollEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "";
    }

    CreepyDollEffect(final CreepyDollEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            if (player.flipCoin(game)) {
                UUID targetId = getTargetPointer().getFirst(game, source);
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.destroy(source.getId(), game, false);
                }
            }
        }
        return false;
    }

    @Override
    public CreepyDollEffect copy() {
        return new CreepyDollEffect(this);
    }
}
