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

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.counters.CounterType;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX
 */
public class OtherworldlyJourney extends CardImpl<OtherworldlyJourney> {

    public OtherworldlyJourney(UUID ownerId) {
        super(ownerId, 37, "Otherworldly Journey", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{W}");
        this.expansionSetCode = "CHK";
        this.color.setWhite(true);
        // Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it.
        this.getSpellAbility().addEffect(new OtherworldlyJourneyEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public OtherworldlyJourney(final OtherworldlyJourney card) {
        super(card);
    }

    @Override
    public OtherworldlyJourney copy() {
        return new OtherworldlyJourney(this);
    }

}

class OtherworldlyJourneyEffect extends OneShotEffect<OtherworldlyJourneyEffect> {

	private static final String effectText = "Exile target creature. At the beginning of the next end step, return that card to the battlefield under its owner's control with a +1/+1 counter on it";

	OtherworldlyJourneyEffect ( ) {
		super(Constants.Outcome.Benefit);
		staticText = effectText;
	}

	OtherworldlyJourneyEffect(OtherworldlyJourneyEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getFirstTarget());
		if (permanent != null) {
			if (permanent.moveToExile(source.getSourceId(), "Otherworldly Journey", source.getId(), game)) {
				ExileZone exile = game.getExile().getExileZone(source.getSourceId());
				// only if permanent is in exile (tokens would be stop to exist)
				if (exile != null && !exile.isEmpty()) {
					//create delayed triggered ability
					AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(
							new ReturnFromExileEffect(source.getSourceId(), Constants.Zone.BATTLEFIELD, "return that card to the battlefield under its owner's control with a +1/+1 counter on it"));
					delayedAbility.setSourceId(source.getSourceId());
					delayedAbility.setControllerId(source.getControllerId());
					AddCountersTargetEffect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
					effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
					delayedAbility.addEffect(effect);
					game.addDelayedTriggeredAbility(delayedAbility);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public OtherworldlyJourneyEffect copy() {
		return new OtherworldlyJourneyEffect(this);
	}

}
