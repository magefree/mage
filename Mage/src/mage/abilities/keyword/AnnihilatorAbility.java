/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */
package mage.abilities.keyword;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 * 702.84. Annihilator 702.84a Annihilator is a triggered ability. "Annihilator
 * N" means "Whenever this creature attacks, defending player sacrifices N
 * permanents."
 *
 * 702.84b If a creature has multiple instances of annihilator, each triggers
 * separately.
 *
 * @author maurer.it_at_gmail.com
 */
public class AnnihilatorAbility extends TriggeredAbilityImpl {

    int count;

    public AnnihilatorAbility(int count) {
        super(Zone.BATTLEFIELD, new AnnihilatorEffect(count), false);
        this.count = count;
    }

    public AnnihilatorAbility(final AnnihilatorAbility ability) {
        super(ability);
        this.count = ability.count;
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ATTACKER_DECLARED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(this.getSourceId())) {
            UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(sourceId, game);
            if (defendingPlayerId != null) {
                // the id has to be set here because the source can be leave battlefield
                for(Effect effect : getEffects()) {
                    effect.setValue("defendingPlayerId", defendingPlayerId);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Annihilator " + count + "<i>(Whenever this creature attacks, defending player sacrifices " +
                (count == 1 ? "a permanent": CardUtil.numberToText(count) + " permanents") + ".)</i>";
    }

    @Override
    public AnnihilatorAbility copy() {
        return new AnnihilatorAbility(this);
    }

}

class AnnihilatorEffect extends OneShotEffect {

    private final int count;
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    AnnihilatorEffect(int count) {
        super(Outcome.Sacrifice);
        this.count = count;
    }

    AnnihilatorEffect(AnnihilatorEffect effect) {
        super(effect);
        this.count = effect.count;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = (UUID) getValue("defendingPlayerId");
        Player player = null;
        if (defendingPlayerId != null) {
            player= game.getPlayer(defendingPlayerId);
        }
        if (player != null) {
            int amount = Math.min(count, game.getBattlefield().countAll(filter, player.getId(), game));
            Target target = new TargetControlledPermanent(amount, amount, filter, false);
            if (target.canChoose(player.getId(), game)) {
                while (!target.isChosen() && target.canChoose(player.getId(), game) && player.canRespond()) {
                    player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                }
                for (int idx = 0; idx < target.getTargets().size(); idx++) {
                    Permanent permanent = game.getPermanent((UUID) target.getTargets().get(idx));
                    if (permanent != null) {
                        permanent.sacrifice(source.getSourceId(), game);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public AnnihilatorEffect copy() {
        return new AnnihilatorEffect(this);
    }

}
