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
package mage.sets.bornofthegods;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class AcolytesReward extends CardImpl<AcolytesReward> {

    public AcolytesReward(UUID ownerId) {
        super(ownerId, 1, "Acolyte's Reward", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "BNG";

        this.color.setWhite(true);

        // Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, Acolyte's Reward deals that much damage to target creature or player.
        this.getSpellAbility().addEffect(new AcolytesRewardEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer(true));
    }

    public AcolytesReward(final AcolytesReward card) {
        super(card);
    }

    @Override
    public AcolytesReward copy() {
        return new AcolytesReward(this);
    }
}

class AcolytesRewardEffect extends PreventionEffectImpl<AcolytesRewardEffect> {

    protected int amount = 0;

    public AcolytesRewardEffect() {
        super(Duration.EndOfTurn);
        staticText = "Prevent the next X damage that would be dealt to target creature this turn, where X is your devotion to white. If damage is prevented this way, {this} deals that much damage to target creature or player.";
    }

    public AcolytesRewardEffect(final AcolytesRewardEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public AcolytesRewardEffect copy() {
        return new AcolytesRewardEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        amount = new DevotionCount(ColoredManaSymbol.W).calculate(game, source);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        boolean result = false;
        int toPrevent = amount;
        if (event.getAmount() < this.amount) {
            toPrevent = event.getAmount();
            amount -= event.getAmount();
        } else {
            amount = 0;
        }
        GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getControllerId(), source.getId(), source.getControllerId(), toPrevent, false);
        if (!game.replaceEvent(preventEvent)) {
            Permanent targetCreature = game.getPermanent(source.getFirstTarget());
            if (targetCreature != null) {
                if (amount == 0) {
                    this.used = true;
                    this.discard();
                }
                if (event.getAmount() >= toPrevent) {
                    event.setAmount(event.getAmount() - toPrevent);
                } else {
                    event.setAmount(0);
                    result = true;
                }
                if (toPrevent > 0) {
                    game.informPlayers(new StringBuilder("Acolyte's Reward ").append("prevented ").append(toPrevent).append(" to ").append(targetCreature.getName()).toString());
                    game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE,
                            source.getControllerId(), source.getSourceId(), source.getControllerId(), toPrevent));

                    Player targetPlayer = game.getPlayer(source.getTargets().get(1).getFirstTarget());
                    if (targetPlayer != null) {
                        targetPlayer.damage(toPrevent, source.getSourceId(), game, false, true);
                        game.informPlayers(new StringBuilder("Acolyte's Reward ").append("deals ").append(toPrevent).append(" damage to ").append(targetPlayer.getName()).toString());
                    } else {
                        Permanent targetDamageCreature = game.getPermanent(source.getTargets().get(1).getFirstTarget());
                        if (targetDamageCreature != null) {
                            targetDamageCreature.damage(toPrevent, source.getSourceId(), game, true, false);
                            game.informPlayers(new StringBuilder("Acolyte's Reward ").append("deals ").append(toPrevent).append(" damage to ").append(targetDamageCreature.getName()).toString());
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return !this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getFirstTarget());
    }

}
