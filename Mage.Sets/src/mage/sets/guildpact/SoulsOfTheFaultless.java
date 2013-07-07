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
package mage.sets.guildpact;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
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
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author North
 */
public class SoulsOfTheFaultless extends CardImpl<SoulsOfTheFaultless> {

    public SoulsOfTheFaultless(UUID ownerId) {
        super(ownerId, 131, "Souls of the Faultless", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{W}{B}{B}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Spirit");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Whenever Souls of the Faultless is dealt combat damage, you gain that much life and attacking player loses that much life.
        this.addAbility(new SoulsOfTheFaultlessTriggeredAbility());
    }

    public SoulsOfTheFaultless(final SoulsOfTheFaultless card) {
        super(card);
    }

    @Override
    public SoulsOfTheFaultless copy() {
        return new SoulsOfTheFaultless(this);
    }
}

class SoulsOfTheFaultlessTriggeredAbility extends TriggeredAbilityImpl<SoulsOfTheFaultlessTriggeredAbility> {

    public SoulsOfTheFaultlessTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SoulsOfTheFaultlessEffect());
    }

    public SoulsOfTheFaultlessTriggeredAbility(final SoulsOfTheFaultlessTriggeredAbility effect) {
        super(effect);
    }

    @Override
    public SoulsOfTheFaultlessTriggeredAbility copy() {
        return new SoulsOfTheFaultlessTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DAMAGED_CREATURE
                && event.getTargetId().equals(this.sourceId)
                && ((DamagedCreatureEvent) event).isCombatDamage()) {
            Permanent source = game.getPermanent(event.getSourceId());
            if (source == null) {
                source = (Permanent) game.getLastKnownInformation(event.getSourceId(), Zone.BATTLEFIELD);
            }
            UUID attackerId = source != null ? source.getControllerId() : null;
            for (Effect effect : this.getEffects()) {
                effect.setValue("damageAmount", event.getAmount());
                effect.setValue("attackerId", attackerId);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} is dealt combat damage, " + super.getRule();
    }
}

class SoulsOfTheFaultlessEffect extends OneShotEffect<SoulsOfTheFaultlessEffect> {

    public SoulsOfTheFaultlessEffect() {
        super(Outcome.GainLife);
        staticText = "you gain that much life and attacking player loses that much life";
    }

    public SoulsOfTheFaultlessEffect(final SoulsOfTheFaultlessEffect effect) {
        super(effect);
    }

    @Override
    public SoulsOfTheFaultlessEffect copy() {
        return new SoulsOfTheFaultlessEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Integer amount = (Integer) this.getValue("damageAmount");

        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game);
        }

        UUID attackerId = (UUID) this.getValue("attackerId");
        Player attacker = game.getPlayer(attackerId);
        if (attacker != null) {
            attacker.loseLife(amount, game);
        }
        return true;
    }
}
