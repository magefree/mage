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
package mage.cards.g;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author BursegSardaukar
 */
public class GoblinAssassin extends CardImpl {

    public GoblinAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add("Goblin");
        this.subtype.add("Assassin");
        
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Goblin Assassin or another Goblin enters the battlefield, each player flips a coin. Each player whose coin comes up tails sacrifices a creature.
        this.addAbility(new GoblinAssassinTriggeredAbiliy());
    }

    public GoblinAssassin(final GoblinAssassin card) {
        super(card);
    }

    @Override
    public GoblinAssassin copy() {
        return new GoblinAssassin(this);
    }
}

class GoblinAssassinTriggeredAbiliy extends TriggeredAbilityImpl {
    GoblinAssassinTriggeredAbiliy() {
        super(Zone.BATTLEFIELD, new GoblinAssassinTriggeredEffect(), false);
    }

    GoblinAssassinTriggeredAbiliy(final GoblinAssassinTriggeredAbiliy ability) {
        super(ability);
    }

    @Override
    public GoblinAssassinTriggeredAbiliy copy() {
        return new GoblinAssassinTriggeredAbiliy(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if ((targetId.equals(this.getSourceId())) || (permanent.hasSubtype("Goblin", game) && !targetId.equals(this.getSourceId()))) {
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever {this} or another Goblin enters battlefield, each player flips a coin. Each player whose coin comes up tails sacrifices a creature.";
    }
}

class GoblinAssassinTriggeredEffect extends OneShotEffect {
    GoblinAssassinTriggeredEffect() {
        super(Outcome.Sacrifice);
    }

    GoblinAssassinTriggeredEffect(final GoblinAssassinTriggeredEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
       List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {            
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !player.flipCoin(game)) {
                    TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                    target.setNotTarget(true);
                    if (target.canChoose(player.getId(), game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source.getSourceId(), game);
                }
            }
            return true;        
        }
        return false;
    }

    @Override
    public GoblinAssassinTriggeredEffect copy() {
        return new GoblinAssassinTriggeredEffect(this);
    }
}