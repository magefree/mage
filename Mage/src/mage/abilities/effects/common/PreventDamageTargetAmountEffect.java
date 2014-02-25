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

package mage.abilities.effects.common;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreatureOrPlayerAmount;

/**
 *
 * @author LevelX2
 */

public class PreventDamageTargetAmountEffect extends PreventionEffectImpl<PreventDamageTargetAmountEffect> {

    private final int amount;
    private final Map<UUID, Integer> targetAmountMap = new HashMap<>();

    public PreventDamageTargetAmountEffect(Duration duration, int amount) {
        super(duration);
        this.amount = amount;
    }

    public PreventDamageTargetAmountEffect(final PreventDamageTargetAmountEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public PreventDamageTargetAmountEffect copy() {
        return new PreventDamageTargetAmountEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Target target = source.getTargets().get(0);
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (target instanceof TargetCreatureOrPlayerAmount && sourcePermanent != null) {
            TargetCreatureOrPlayerAmount multiTarget = (TargetCreatureOrPlayerAmount) target;
            for (UUID targetId: multiTarget.getTargets()) {
                Player player = null;
                Permanent permanent = game.getPermanent(targetId);
                if (permanent == null) {
                    player = game.getPlayer(targetId);
                }
                targetAmountMap.put(targetId, multiTarget.getTargetAmount(targetId));
                StringBuilder sb = new StringBuilder(sourcePermanent.getName()).append(": Prevent the next ");
                sb.append(multiTarget.getTargetAmount(targetId)).append(" damage to ");
                if (player != null) {
                    sb.append(player.getName());
                } else if (permanent != null) {
                    sb.append(permanent.getName());
                }
                game.informPlayers(sb.toString());

            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        int targetAmount = targetAmountMap.get(event.getTargetId());
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), event.getAmount(), false);
        if (!game.replaceEvent(preventEvent)) {
            if (event.getAmount() >= targetAmount) {
                int damage = targetAmount;
                event.setAmount(event.getAmount() - targetAmount);
                targetAmountMap.remove(event.getTargetId());
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
            } else {
                int damage = event.getAmount();
                event.setAmount(0);
                targetAmountMap.put(event.getTargetId(), targetAmount -= damage);
                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getFirstTarget(), source.getSourceId(), source.getControllerId(), damage));
            }
            if (targetAmountMap.isEmpty()) {
                this.used = true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!used && super.applies(event, source, game) && targetAmountMap.containsKey(event.getTargetId())) {
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        // prevent the next 5 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose
        StringBuilder sb = new StringBuilder();
        sb.append("Prevent the next ").append(amount).append(" damage that would be dealt ");
        if (duration.equals(Duration.EndOfTurn)) {
            sb.append("this turn ");
        }
        sb.append("to any number of target creatures and/or players, divided as you choose");
        return sb.toString();
    }

}
