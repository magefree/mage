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
package mage.sets.scourge;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class XantidSwarm extends CardImpl<XantidSwarm> {

    public XantidSwarm(UUID ownerId) {
        super(ownerId, 135, "Xantid Swarm", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{G}");
        this.expansionSetCode = "SCG";
        this.subtype.add("Insect");

        this.color.setGreen(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Xantid Swarm attacks, defending player can't cast spells this turn.
        Ability ability = new XantidSwarmTriggeredAbility(new XantidSwarmReplacementEffect());
        
        this.addAbility(ability);
    }

    public XantidSwarm(final XantidSwarm card) {
        super(card);
    }

    @Override
    public XantidSwarm copy() {
        return new XantidSwarm(this);
    }
}

class XantidSwarmTriggeredAbility extends TriggeredAbilityImpl<XantidSwarmTriggeredAbility> {

    public XantidSwarmTriggeredAbility(Effect effect) {
        super(Zone.BATTLEFIELD, effect);
    }

    public XantidSwarmTriggeredAbility(final XantidSwarmTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public XantidSwarmTriggeredAbility copy() {
        return new XantidSwarmTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ATTACKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
            UUID defender = game.getCombat().getDefendingPlayerId(this.getSourceId(), game);
            this.getEffects().get(0).setTargetPointer(new FixedTarget(defender));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("Whenever {this} attacks, ").append(super.getRule()).toString();
    }
}

class XantidSwarmReplacementEffect extends ReplacementEffectImpl<XantidSwarmReplacementEffect> {

    public XantidSwarmReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "defending player can't cast spells this turn";
    }

    public XantidSwarmReplacementEffect(final XantidSwarmReplacementEffect effect) {
        super(effect);
    }

    @Override
    public XantidSwarmReplacementEffect copy() {
        return new XantidSwarmReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.CAST_SPELL ) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null && player.getId().equals(event.getPlayerId())) {
                return true;
            }
        }
        return false;
    }
}
