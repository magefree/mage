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
package mage.sets.innistrad;

import java.util.UUID;

import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.watchers.common.DamagedByWatcher;

/**
 *
 * @author Alvin
 */
public class AbattoirGhoul extends CardImpl<AbattoirGhoul> {

    public AbattoirGhoul(UUID ownerId) {
        super(ownerId, 85, "Abattoir Ghoul", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Zombie");

        this.color.setBlack(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.addAbility(FirstStrikeAbility.getInstance());
        // Whenever a creature dealt damage by Abattoir Ghoul this turn dies, you gain life equal to that creature's toughness.
        AbattoirGhoulEffect effect = new AbattoirGhoulEffect();
        
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        this.addWatcher(new DamagedByWatcher());
    }

    public AbattoirGhoul(final AbattoirGhoul card) {
        super(card);
    }

    @Override
    public AbattoirGhoul copy() {
        return new AbattoirGhoul(this);
    }
}

class AbattoirGhoulEffect extends ReplacementEffectImpl<AbattoirGhoulEffect> {

	public AbattoirGhoulEffect() {
		super(Duration.EndOfTurn, Outcome.GainLife);
		staticText = "Whenever a creature dealt damage by Abattoir Ghoul this turn dies, you gain life equal to that creature's toughness";
	}

	public AbattoirGhoulEffect(final AbattoirGhoulEffect effect) {
		super(effect);
	}

	@Override
	public AbattoirGhoulEffect copy() {
		return new AbattoirGhoulEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent permanent = ((ZoneChangeEvent)event).getTarget();
        if (permanent != null) {
        	MageInt toughness = permanent.getToughness();
        	Player player = (Player)this.getValue("player");
        	player.gainLife(toughness.getValue(), game);
            return true;
        }
		return false;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (event.getType() == EventType.ZONE_CHANGE && ((ZoneChangeEvent)event).isDiesEvent()) {
            Permanent p = game.getPermanent(source.getSourceId());
            if (p != null) {
                DamagedByWatcher watcher = (DamagedByWatcher) game.getState().getWatchers().get("DamagedByWatcher", source.getSourceId());
                if (watcher != null)
                    return watcher.damagedCreatures.contains(event.getTargetId());
            }
		}
		return false;
	}

}
