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
package mage.sets.dragonsmaze;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class TeysaEnvoyOfGhosts extends CardImpl<TeysaEnvoyOfGhosts> {

    public TeysaEnvoyOfGhosts(UUID ownerId) {
        super(ownerId, 108, "Teysa, Envoy of Ghosts", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{W}{B}");
        this.expansionSetCode = "DGM";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Advisor");

        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
        // protection from creatures
        this.addAbility(new ProtectionAbility(new FilterCreaturePermanent("creatures")));
        // Whenever a creature deals combat damage to you, destroy that creature. Put a 1/1 white and black Spirit creature token with flying onto the battlefield.
       this.addAbility(new TeysaEnvoyOfGhostsTriggeredAbility());

    }

    public TeysaEnvoyOfGhosts(final TeysaEnvoyOfGhosts card) {
        super(card);
    }

    @Override
    public TeysaEnvoyOfGhosts copy() {
        return new TeysaEnvoyOfGhosts(this);
    }
}

class TeysaEnvoyOfGhostsTriggeredAbility extends TriggeredAbilityImpl<TeysaEnvoyOfGhostsTriggeredAbility> {

    public TeysaEnvoyOfGhostsTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new DestroyTargetEffect());
        this.addEffect(new CreateTokenEffect(new TeysaEnvoyOfGhostsToken(), 1));

     }

    public TeysaEnvoyOfGhostsTriggeredAbility(final TeysaEnvoyOfGhostsTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TeysaEnvoyOfGhostsTriggeredAbility copy() {
        return new TeysaEnvoyOfGhostsTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof DamagedPlayerEvent) {
            DamagedPlayerEvent damageEvent = (DamagedPlayerEvent)event;
            Permanent p = game.getPermanent(event.getSourceId());
            if (damageEvent.getPlayerId().equals(controllerId) && damageEvent.isCombatDamage() && p != null && p.getCardType().contains(CardType.CREATURE)) {
                game.getState().setValue(sourceId.toString(), p.getControllerId());
                getEffects().get(0).setTargetPointer(new FixedTarget(event.getSourceId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature deals combat damage to you, destroy that creature. Put a 1/1 white and black Spirit creature token with flying onto the battlefield.";
    }

}

class TeysaEnvoyOfGhostsToken extends Token {
    TeysaEnvoyOfGhostsToken() {
        super("Spirit", "1/1 white and black Spirit creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        color.setBlack(true);
        subtype.add("Spirit");
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }
}
