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

package mage.sets.shardsofalara;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public class BattlegraceAngel extends CardImpl<BattlegraceAngel> {

    public BattlegraceAngel (UUID ownerId) {
        super(ownerId, 6, "Battlegrace Angel", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "ALA";
        this.subtype.add("Angel");
        this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(new ExaltedAbility());
        this.addAbility(new BattlegraceAngelAbility());
    }

    public BattlegraceAngel (final BattlegraceAngel card) {
        super(card);
    }

    @Override
    public BattlegraceAngel copy() {
        return new BattlegraceAngel(this);
    }

}

class BattlegraceAngelAbility extends TriggeredAbilityImpl<BattlegraceAngelAbility> {

	public BattlegraceAngelAbility() {
		super(Zone.BATTLEFIELD, new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn));
	}

	public BattlegraceAngelAbility(final BattlegraceAngelAbility ability) {
		super(ability);
	}

	@Override
	public BattlegraceAngelAbility copy() {
		return new BattlegraceAngelAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.DECLARED_ATTACKERS && game.getActivePlayerId().equals(this.controllerId) ) {
			if (game.getCombat().attacksAlone()) {
				TargetCreaturePermanent target = new TargetCreaturePermanent();
				this.addTarget(target);
				this.getTargets().get(0).add(game.getCombat().getAttackers().get(0),game);
				return true;
			}
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever a creature you control attacks alone, it gains lifelink until end of turn.";
	}

}
