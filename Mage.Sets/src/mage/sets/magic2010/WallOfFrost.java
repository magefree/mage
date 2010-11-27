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

package mage.sets.magic2010;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.SkipNextUntapTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class WallOfFrost extends CardImpl<WallOfFrost> {

	public WallOfFrost(UUID ownerId) {
		super(ownerId, 80, "Wall of Frost", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
		this.expansionSetCode = "M10";
		this.subtype.add("Wall");
		this.color.setBlue(true);
		this.power = new MageInt(0);
		this.toughness = new MageInt(7);

		this.addAbility(DefenderAbility.getInstance());
		this.addAbility(new WallOfFrostAbility());
	}

	public WallOfFrost(final WallOfFrost card) {
		super(card);
	}

	@Override
	public WallOfFrost copy() {
		return new WallOfFrost(this);
	}

	@Override
	public String getArt() {
		return "121591_typ_reg_sty_010.jpg";
	}

}

class WallOfFrostAbility extends TriggeredAbilityImpl<WallOfFrostAbility> {

	public WallOfFrostAbility() {
		super(Zone.BATTLEFIELD, new SkipNextUntapTargetEffect(), false);
	}

	public WallOfFrostAbility(final WallOfFrostAbility ability) {
		super(ability);
	}

	@Override
	public WallOfFrostAbility copy() {
		return new WallOfFrostAbility(this);
	}

	@Override
	public boolean checkTrigger(GameEvent event, Game game) {
		if (event.getType() == EventType.BLOCKER_DECLARED && event.getSourceId().equals(this.getSourceId())) {
			this.addTarget(new TargetCreaturePermanent());
			this.getTargets().get(0).add(event.getTargetId(), game);
			trigger(game, this.controllerId);
			return true;
		}
		return false;
	}

	@Override
	public String getRule() {
		return "Whenever Wall of Frost blocks a creature, that creature doesn't untap during its controller's next untap step";
	}

}
