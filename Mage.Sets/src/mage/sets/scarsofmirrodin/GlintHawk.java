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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class GlintHawk extends CardImpl<GlintHawk> {

	public GlintHawk(UUID ownerId) {
		super(ownerId, 10, "Glint Hawk", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{W}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Bird");

		this.color.setWhite(true);
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);

		this.addAbility(FlyingAbility.getInstance());
		this.addAbility(new EntersBattlefieldTriggeredAbility(new GlintHawkEffect()));
	}

	public GlintHawk(final GlintHawk card) {
		super(card);
	}

	@Override
	public GlintHawk copy() {
		return new GlintHawk(this);
	}
}

class GlintHawkEffect extends OneShotEffect<GlintHawkEffect> {

	private static final FilterControlledPermanent filter;
	private static final String effectText = "When Glint Hawk enters the battlefield, sacrifice it unless you return an artifact you control to its owner's hand";

	static {
		filter = new FilterControlledPermanent();
		filter.getCardType().add(CardType.ARTIFACT);
	}
	
	GlintHawkEffect ( ) {
		super(Outcome.Sacrifice);
		staticText = effectText;
	}

	GlintHawkEffect ( GlintHawkEffect effect ) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		boolean targetChosen = false;
		Player player = game.getPlayer(source.getControllerId());
		TargetPermanent target = new TargetPermanent(1, 1, filter, false);

		if (target.canChoose(player.getId(), game)) {
			player.choose(Outcome.Sacrifice, target, game);
			Permanent permanent = game.getPermanent(target.getFirstTarget());

			if ( permanent != null ) {
				targetChosen = true;
				permanent.moveToZone(Zone.HAND, this.getId(), game, false);
			}
		}

		if ( !targetChosen ) {
			new SacrificeSourceEffect().apply(game, source);
		}

		return false;
	}

	@Override
	public GlintHawkEffect copy() {
		return new GlintHawkEffect(this);
	}

}
