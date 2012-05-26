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

package mage.sets.scarsofmirrodin;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.continious.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author Loki
 */
public class GlintHawkIdol extends CardImpl<GlintHawkIdol> {

    public GlintHawkIdol (UUID ownerId) {
        super(ownerId, 156, "Glint Hawk Idol", Rarity.COMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "SOM";
        this.addAbility(new GlintHawkIdolTriggeredAbility());
        this.addAbility(new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GlintHawkIdolToken(), "", Duration.EndOfTurn), new ColoredManaCost(Constants.ColoredManaSymbol.W)));
    }

    public GlintHawkIdol (final GlintHawkIdol card) {
        super(card);
    }

    @Override
    public GlintHawkIdol copy() {
        return new GlintHawkIdol(this);
    }

}

class GlintHawkIdolTriggeredAbility extends TriggeredAbilityImpl<GlintHawkIdolTriggeredAbility> {
    GlintHawkIdolTriggeredAbility() {
        super(Constants.Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new GlintHawkIdolToken(), "", Duration.EndOfTurn), true);
    }

    GlintHawkIdolTriggeredAbility(final GlintHawkIdolTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GlintHawkIdolTriggeredAbility copy() {
        return new GlintHawkIdolTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE && !event.getTargetId().equals(this.getSourceId())) {
			ZoneChangeEvent zEvent = (ZoneChangeEvent)event;
			if (zEvent.getToZone() == Constants.Zone.BATTLEFIELD) {
				Permanent permanent = game.getPermanent(event.getTargetId());
				if (permanent != null && permanent.getCardType().contains(CardType.ARTIFACT)
						&& permanent.getControllerId().equals(this.controllerId)) {
					return true;
				}
			}
		}
		return false;
    }

    @Override
    public String getRule() {
        return "Whenever another artifact enters the battlefield under your control, you may have {this} become a 2/2 Bird artifact creature with flying until end of turn.";
    }
}

class GlintHawkIdolToken extends Token {
    GlintHawkIdolToken() {
        super("", "a 2/2 Bird artifact creature with flying");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add("Bird");
        power = new MageInt(2);
		toughness = new MageInt(2);
        addAbility(FlyingAbility.getInstance());
    }
}