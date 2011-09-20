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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagePlayerEvent;
import mage.game.events.DamagedPlayerEvent;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward
 */
public class PhyrexianUnlife extends CardImpl<PhyrexianUnlife> {

    public PhyrexianUnlife(UUID ownerId) {
        super(ownerId, 18, "Phyrexian Unlife", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        this.expansionSetCode = "NPH";

        this.color.setWhite(true);

        // You don't lose the game for having 0 or less life.
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianUnlifeEffect()));
        
        // As long as you have 0 or less life, all damage is dealt to you as though its source had infect.
		this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PhyrexianUnlifeEffect2()));
        
    }

    public PhyrexianUnlife(final PhyrexianUnlife card) {
        super(card);
    }

    @Override
    public PhyrexianUnlife copy() {
        return new PhyrexianUnlife(this);
    }
}

class PhyrexianUnlifeEffect extends ReplacementEffectImpl<PhyrexianUnlifeEffect> {

    public PhyrexianUnlifeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You don't lose the game for having 0 or less life";
    }

    public PhyrexianUnlifeEffect(final PhyrexianUnlifeEffect effect) {
        super(effect);
    }

    @Override
    public PhyrexianUnlifeEffect copy() {
        return new PhyrexianUnlifeEffect(this);
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
        if (event.getType() == EventType.LOSES && event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(event.getPlayerId());
  			if (!player.hasLost() && (player.getLife() <= 0 && !player.isEmptyDraw() && player.getCounters().getCount(CounterType.POISON) < 10)) {
				return true;
			}
        }
        return false;
    }

}

class PhyrexianUnlifeEffect2 extends ReplacementEffectImpl<PhyrexianUnlifeEffect2> {

    public PhyrexianUnlifeEffect2() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "As long as you have 0 or less life, all damage is dealt to you as though its source had infect";
    }

    public PhyrexianUnlifeEffect2(final PhyrexianUnlifeEffect2 effect) {
        super(effect);
    }

    @Override
    public PhyrexianUnlifeEffect2 copy() {
        return new PhyrexianUnlifeEffect2(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        DamagePlayerEvent damageEvent = (DamagePlayerEvent) event;
        int actualDamage = damageEvent.getAmount();
        if (actualDamage > 0) {
            Player player = game.getPlayer(damageEvent.getPlayerId());
            Permanent damageSource = game.getPermanent(damageEvent.getSourceId());
            player.addCounters(CounterType.POISON.createInstance(actualDamage), game);
            if (damageSource != null && damageSource.getAbilities().containsKey(LifelinkAbility.getInstance().getId())) {
                Player controlPlayer = game.getPlayer(damageSource.getControllerId());
                controlPlayer.gainLife(actualDamage, game);
            }
            game.fireEvent(new DamagedPlayerEvent(damageEvent.getPlayerId(), damageEvent.getSourceId(), damageEvent.getPlayerId(), actualDamage, damageEvent.isCombatDamage()));
        }
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DAMAGE_PLAYER && event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(event.getPlayerId());
  			if (player.getLife() <= 0) {
				return true;
			}
        }
        return false;
    }

}