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

package mage.sets.magic2011;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.PreventionEffectImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class VengefulArchon extends CardImpl<VengefulArchon> {

	public VengefulArchon(UUID ownerId) {
		super(ownerId, 37, "Vengeful Archon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");
		this.expansionSetCode = "M11";
		this.subtype.add("Archon");
		this.color.setWhite(true);
		this.power = new MageInt(7);
		this.toughness = new MageInt(7);

		this.addAbility(FlyingAbility.getInstance());
		Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VengefulArchonEffect(), new ManaCostsImpl("{X}"));
		ability.addTarget(new TargetPlayer());
		this.addAbility(ability);
	}

	public VengefulArchon(final VengefulArchon card) {
		super(card);
	}

	@Override
	public VengefulArchon copy() {
		return new VengefulArchon(this);
	}

	@Override
	public String getArt() {
		return "129192_typ_reg_sty_010.jpg";
	}

}

class VengefulArchonEffect extends PreventionEffectImpl<VengefulArchonEffect> {

	protected int amount = 0;

	public VengefulArchonEffect() {
		super(Duration.EndOfTurn);
	}

	public VengefulArchonEffect(final VengefulArchonEffect effect) {
		super(effect);
		this.amount = effect.amount;
	}

	@Override
	public VengefulArchonEffect copy() {
		return new VengefulArchonEffect(this);
	}

	@Override
	public void init(Ability source, Game game) {
		if (source.getManaCosts().getVariableCosts().size() > 0)
			amount = source.getManaCosts().getVariableCosts().get(0).getAmount();
	}

	@Override
	public boolean apply(Game game, Ability source) {
		return true;
	}

	@Override
	public boolean replaceEvent(GameEvent event, Ability source, Game game) {
		GameEvent preventEvent = new GameEvent(GameEvent.EventType.PREVENT_DAMAGE, source.getControllerId(), source.getId(), source.getControllerId(), event.getAmount(), false);
		if (!game.replaceEvent(preventEvent)) {
			Player player = game.getPlayer(source.getFirstTarget());
			if (player != null) {
				if (event.getAmount() >= this.amount) {
					int damage = event.getAmount();
					event.setAmount(event.getAmount() - amount);
					player.damage(amount, source.getSourceId(), game, false, true);
					this.used = true;
					game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getControllerId(), source.getId(), source.getControllerId(), damage));
				} else {
					int damage = event.getAmount();
					event.setAmount(0);
					amount -= damage;
					player.damage(damage, source.getSourceId(), game, false, true);
					game.fireEvent(GameEvent.getEvent(GameEvent.EventType.PREVENTED_DAMAGE, source.getControllerId(), source.getId(), source.getControllerId(), damage));
				}
			}
		}
		return false;
	}

	@Override
	public boolean applies(GameEvent event, Ability source, Game game) {
		if (!this.used && super.applies(event, source, game) && event.getTargetId().equals(source.getControllerId())) {
			return true;
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "Prevent the next X damage that would be dealt to you this turn. If damage is prevented this way, Vengeful Archon deals that much damage to target player";
	}

}
