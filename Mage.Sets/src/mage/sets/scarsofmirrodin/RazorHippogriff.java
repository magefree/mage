/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 * 
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 * 
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
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
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;

/**
 * TODO: Javadoc me
 * 
 * @author maurer.it_at_gmail.com
 */
public class RazorHippogriff extends CardImpl<RazorHippogriff> {

	private static final FilterCard filter;

	static {
		filter = new FilterCard();
		filter.getCardType().add(CardType.ARTIFACT);
	}

	public RazorHippogriff(UUID ownerId) {
		super(ownerId, 17, "Razor Hippogriff", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
		this.expansionSetCode = "SOM";
		this.subtype.add("Hippogriff");
		this.color.setWhite(true);
		this.power = new MageInt(3);
		this.toughness = new MageInt(3);

		this.addAbility(FlyingAbility.getInstance());
		Ability etbAbility = new EntersBattlefieldTriggeredAbility(new RazorHippogriffEffect());
		etbAbility.addTarget(new TargetCardInYourGraveyard(filter));
		this.addAbility(etbAbility);
	}

	public RazorHippogriff(final RazorHippogriff card) {
		super(card);
	}

	@Override
	public RazorHippogriff copy() {
		return new RazorHippogriff(this);
	}
}

class RazorHippogriffEffect extends OneShotEffect<RazorHippogriffEffect> {

	private static final String effectText = "return target artifact card from your graveyard to your hand. You gain life equal to that card's converted mana cost";

	RazorHippogriffEffect ( ) {
		super(Outcome.ReturnToHand);
	}

	RazorHippogriffEffect ( final RazorHippogriffEffect effect ) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Card card = game.getCard(source.getFirstTarget());
		Player player = game.getPlayer(source.getControllerId());
		if (card != null) {
			player.gainLife(card.getManaCost().convertedManaCost(), game);
			return card.moveToZone(Zone.HAND, source.getId(), game, true);
		}
		return false;
	}

	@Override
	public RazorHippogriffEffect copy() {
		return new RazorHippogriffEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return effectText;
	}
}
