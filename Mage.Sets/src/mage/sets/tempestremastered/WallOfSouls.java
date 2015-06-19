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
package mage.sets.tempestremastered;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DamagedCreatureEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author fireshoes
 */
public class WallOfSouls extends CardImpl {

    public WallOfSouls(UUID ownerId) {
        super(ownerId, 123, "Wall of Souls", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "TPR";
        this.subtype.add("Wall");
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        
        // Whenever Wall of Souls is dealt combat damage, it deals that much damage to target opponent.
        Ability ability = new WallOfSoulsTriggeredAbility();
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public WallOfSouls(final WallOfSouls card) {
        super(card);
    }

    @Override
    public WallOfSouls copy() {
        return new WallOfSouls(this);
    }
}

class WallOfSoulsTriggeredAbility extends TriggeredAbilityImpl {

    public WallOfSoulsTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WallOfSoulsDealDamageEffect());
    }

    public WallOfSoulsTriggeredAbility(final WallOfSoulsTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public WallOfSoulsTriggeredAbility copy() {
        return new WallOfSoulsTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.DAMAGED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.sourceId) && ((DamagedCreatureEvent)event).isCombatDamage()) {
   			this.getEffects().get(0).setValue("damage", event.getAmount());
       		return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt combat damage, " + super.getRule();
    }
}

class WallOfSoulsDealDamageEffect extends OneShotEffect {

    public WallOfSoulsDealDamageEffect() {
        super(Outcome.Damage);
        this.staticText = "it deals that much damage to target opponent";
    }

    public WallOfSoulsDealDamageEffect(final WallOfSoulsDealDamageEffect effect) {
        super(effect);
    }

    @Override
    public WallOfSoulsDealDamageEffect copy() {
        return new WallOfSoulsDealDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = (Integer) getValue("damage");
        if (amount > 0) {
            Player targetOpponent = game.getPlayer(source.getTargets().getFirstTarget());
            if (targetOpponent != null) {
                targetOpponent.damage(amount, source.getSourceId(), game, false, true);
                return true;
            }
        }
        return false;
    }
}