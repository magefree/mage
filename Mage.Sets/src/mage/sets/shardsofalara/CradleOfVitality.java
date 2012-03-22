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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Alvin
 */
public class CradleOfVitality extends CardImpl<CradleOfVitality> {

    public CradleOfVitality(UUID ownerId) {
        super(ownerId, 7, "Cradle of Vitality", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        this.expansionSetCode = "ALA";

        this.color.setWhite(true);

        // Whenever you gain life, you may pay {1}{W}. If you do, put a +1/+1 counter on target creature for each 1 life you gained.
        Ability ability = new CradleOfVitalityGainLifeTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    public CradleOfVitality(final CradleOfVitality card) {
        super(card);
    }

    @Override
    public CradleOfVitality copy() {
        return new CradleOfVitality(this);
    }
}


class CradleOfVitalityGainLifeTriggeredAbility extends TriggeredAbilityImpl<CradleOfVitalityGainLifeTriggeredAbility> {
	
	public CradleOfVitalityGainLifeTriggeredAbility() {
		super(Zone.BATTLEFIELD, new CradleOfVitalityEffect(), false);
		addManaCost(new ManaCostsImpl("{1}{W}"));
	}

	public CradleOfVitalityGainLifeTriggeredAbility(final CradleOfVitalityGainLifeTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public CradleOfVitalityGainLifeTriggeredAbility copy() {
		return new CradleOfVitalityGainLifeTriggeredAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.GAINED_LIFE && event.getPlayerId().equals(this.controllerId)) {
			this.getEffects().get(0).setValue("amount", event.getAmount());
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever you gain life, you may pay {1}{W}. If you do, put a +1/+1 counter on target creature for each 1 life you gained";
	}
}

class CradleOfVitalityEffect extends OneShotEffect<CradleOfVitalityEffect> {

    public CradleOfVitalityEffect() {
    	super(Outcome.Benefit);
    	staticText = "Put a +1/+1 counter on target creature for each 1 life you gained";
    }

    public CradleOfVitalityEffect(final CradleOfVitalityEffect effect) {
        super(effect);
    }

    @Override
    public CradleOfVitalityEffect copy() {
        return new CradleOfVitalityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
    	int affectedTargets = 0;
    	Integer amount = (Integer) getValue("amount");
		for (UUID uuid : targetPointer.getTargets(source)) {
			Permanent permanent = game.getPermanent(uuid);
			permanent.addCounters(CounterType.P1P1.createInstance(amount), game);
			affectedTargets ++;
		}
		return affectedTargets > 0;
    }
}
