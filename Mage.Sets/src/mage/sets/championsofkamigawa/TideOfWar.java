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
package mage.sets.championsofkamigawa;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class TideOfWar extends CardImpl<TideOfWar> {

    public TideOfWar(UUID ownerId) {
        super(ownerId, 194, "Tide of War", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");
        this.expansionSetCode = "CHK";

        this.color.setRed(true);

        // Whenever one or more creatures block, flip a coin. If you win the flip, each blocking creature is sacrificed by its controller. If you lose the flip, each blocked creature is sacrificed by its controller.
        this.addAbility(new BlocksTriggeredAbility(new TideOfWarEffect(), false));
    }

    public TideOfWar(final TideOfWar card) {
        super(card);
    }

    @Override
    public TideOfWar copy() {
        return new TideOfWar(this);
    }
}

class BlocksTriggeredAbility extends TriggeredAbilityImpl<BlocksTriggeredAbility> {

    public BlocksTriggeredAbility(Effect effect, boolean optional) {
        super(Zone.BATTLEFIELD, effect, optional);
    }

    public BlocksTriggeredAbility(final BlocksTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.DECLARED_BLOCKERS) {
            for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                if (!combatGroup.getBlockers().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creatures block, " + super.getRule();
    }

    @Override
    public BlocksTriggeredAbility copy() {
        return new BlocksTriggeredAbility(this);
    }
}

class TideOfWarEffect extends OneShotEffect<TideOfWarEffect> {

    public TideOfWarEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip a coin. If you win the flip, each blocking creature is sacrificed by its controller. If you lose the flip, each blocked creature is sacrificed by its controller";
    }

    public TideOfWarEffect(final TideOfWarEffect effect) {
        super(effect);
    }

    @Override
    public TideOfWarEffect copy() {
        return new TideOfWarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<UUID> toSacrifice = new HashSet<UUID>();
            if (controller.flipCoin(game)) {
                // each blocking creature is sacrificed by its controller
                for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                    for (UUID blockerId: combatGroup.getBlockers()) {
                        toSacrifice.add(blockerId);
                    }
                }
            } else {
                // each blocked creature is sacrificed by its controller
                for (CombatGroup combatGroup: game.getCombat().getGroups()) {
                    if (!combatGroup.getBlockers().isEmpty()) {
                        for (UUID attackerId: combatGroup.getAttackers()) {
                            toSacrifice.add(attackerId);
                        }
                    }
                }
            }
            for(UUID creatureId: toSacrifice) {
                Permanent creature = game.getPermanent(creatureId);
                if (creature != null) {
                    creature.sacrifice(source.getSourceId(), game);
                    Player player = game.getPlayer(creature.getControllerId());
                    if (player != null) {
                        game.informPlayers(new StringBuilder(player.getName()).append(" sacrifices ").append(creature.getName()).toString());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
