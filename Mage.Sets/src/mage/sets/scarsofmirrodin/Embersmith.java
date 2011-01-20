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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 * @author Loki
 */
public class Embersmith extends CardImpl<Embersmith> {

    public Embersmith(UUID ownerId) {
        super(ownerId, 87, "Embersmith", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Human");
        this.subtype.add("Artificer");
        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.addAbility(new EmbersmithTriggeredAbility());
    }

    public Embersmith(final Embersmith card) {
        super(card);
    }

    @Override
    public Embersmith copy() {
        return new Embersmith(this);
    }

}

class EmbersmithTriggeredAbility extends TriggeredAbilityImpl<EmbersmithTriggeredAbility> {
    EmbersmithTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new EmbersmithEffect());
        this.addTarget(new TargetCreatureOrPlayer());
    }

    EmbersmithTriggeredAbility(final EmbersmithTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EmbersmithTriggeredAbility copy() {
        return new EmbersmithTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getPlayerId().equals(this.getControllerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getCardType().contains(CardType.ARTIFACT)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast an artifact spell, you may pay {1}. If you do, Embersmith deals 1 damage to target creature or player.";
    }
}

class EmbersmithEffect extends OneShotEffect<EmbersmithEffect> {
    EmbersmithEffect() {
        super(Constants.Outcome.Damage);
    }

    EmbersmithEffect(final EmbersmithEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Cost cost = new GenericManaCost(1);
        cost.clearPaid();
        if (cost.pay(game, source.getId(), source.getControllerId(), false)) {
            Permanent permanent = game.getPermanent(source.getFirstTarget());
            if (permanent != null) {
                permanent.damage(1, source.getId(), game, true, false);
                return true;
            }
            Player player = game.getPlayer(source.getFirstTarget());
            if (player != null) {
                player.damage(1, source.getId(), game, false, true);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public EmbersmithEffect copy() {
        return new EmbersmithEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "you may pay {1}. If you do, Embersmith deals 1 damage to target creature or player";
    }
}