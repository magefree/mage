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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.SpellCastTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Loki
 */
public class HikariTwilightGuardian extends CardImpl<HikariTwilightGuardian> {

    private final static FilterSpiritOrArcaneCard filter = new FilterSpiritOrArcaneCard();
    
    public HikariTwilightGuardian (UUID ownerId) {
        super(ownerId, 12, "Hikari, Twilight Guardian", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.expansionSetCode = "CHK";
        this.supertype.add("Legendary");
        this.subtype.add("Spirit");
		this.color.setWhite(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SpellCastTriggeredAbility(new HikariTwilightGuardianEffect(), filter, true));
    }

    public HikariTwilightGuardian (final HikariTwilightGuardian card) {
        super(card);
    }

    @Override
    public HikariTwilightGuardian copy() {
        return new HikariTwilightGuardian(this);
    }

}

class HikariTwilightGuardianEffect extends OneShotEffect<HikariTwilightGuardianEffect> {

	private static final String effectText = "Exile {this}. Return it to the battlefield under your control at the beginning of the next end step";

	HikariTwilightGuardianEffect ( ) {
		super(Constants.Outcome.Benefit);
		staticText = effectText;
	}

	HikariTwilightGuardianEffect(HikariTwilightGuardianEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Permanent permanent = game.getPermanent(source.getSourceId());
		if (permanent != null) {
			if (permanent.moveToExile(source.getSourceId(), "Hikari, Twilight Guardian", source.getId(), game)) {
				//create delayed triggered ability
				HikariTwilightGuardianDelayedTriggeredAbility delayedAbility = new HikariTwilightGuardianDelayedTriggeredAbility(source.getSourceId());
				delayedAbility.setSourceId(source.getSourceId());
				delayedAbility.setControllerId(source.getControllerId());
				game.addDelayedTriggeredAbility(delayedAbility);
				return true;
			}
		}
		return false;
	}

	@Override
	public HikariTwilightGuardianEffect copy() {
		return new HikariTwilightGuardianEffect(this);
	}

}

class HikariTwilightGuardianDelayedTriggeredAbility extends DelayedTriggeredAbility<HikariTwilightGuardianDelayedTriggeredAbility> {

	HikariTwilightGuardianDelayedTriggeredAbility ( UUID exileId ) {
		super(new ReturnFromExileEffect(exileId, Constants.Zone.BATTLEFIELD));
	}

	HikariTwilightGuardianDelayedTriggeredAbility(HikariTwilightGuardianDelayedTriggeredAbility ability) {
		super(ability);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == GameEvent.EventType.END_TURN_STEP_PRE) {
			return true;
		}
		return false;
	}
	@Override
	public HikariTwilightGuardianDelayedTriggeredAbility copy() {
		return new HikariTwilightGuardianDelayedTriggeredAbility(this);
	}
}