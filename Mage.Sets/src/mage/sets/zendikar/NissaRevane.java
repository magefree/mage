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
package mage.sets.zendikar;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayEffect;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class NissaRevane extends CardImpl<NissaRevane> {

	private static final FilterCard nissasChosenFilter = new FilterCard();
	private static final FilterCard elfFilter = new FilterCard();

	static {
		nissasChosenFilter.getName().add("Nissa's Chosen");
		nissasChosenFilter.setMessage("card named Nissa's Chosen");
		elfFilter.getSubtype().add("Elf");
		elfFilter.setMessage("Elf creature cards");
	}

	public NissaRevane(UUID ownerId) {
		super(ownerId, 170, "Nissa Revane", Rarity.MYTHIC, new CardType[]{ CardType.PLANESWALKER }, "{2}{G}{G}");
		this.expansionSetCode = "ZEN";
		this.subtype.add("Nissa");
		this.loyalty = new MageInt(2);

		this.color.setGreen(true);

		LoyaltyAbility ability1 = new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(1, nissasChosenFilter)), 1);
		this.addAbility(ability1);

		LoyaltyAbility ability2 = new LoyaltyAbility(new NissaRevaneGainLifeEffect(), 1);
		this.addAbility(ability2);

		LoyaltyAbility ability3 = new LoyaltyAbility(new SearchLibraryPutInPlayEffect(new TargetCardInLibrary(0, Integer.MAX_VALUE, elfFilter)), -7);
		this.addAbility(ability3);
	}

	public NissaRevane(final NissaRevane card) {
		super(card);
	}

	@Override
	public NissaRevane copy() {
		return new NissaRevane(this);
	}
}

class NissaRevaneGainLifeEffect extends OneShotEffect<NissaRevaneGainLifeEffect> {

	private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

	static {
		filter.getSubtype().add("Elf");
	}

	public NissaRevaneGainLifeEffect() {
		super(Outcome.GainLife);
	}

	public NissaRevaneGainLifeEffect(final NissaRevaneGainLifeEffect effect) {
		super(effect);
	}

	@Override
	public NissaRevaneGainLifeEffect copy() {
		return new NissaRevaneGainLifeEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		int life = 2 * game.getBattlefield().countAll(filter);
		if (player != null) {
			player.gainLife(life, game);
		}
		return true;
	}

	@Override
	public String getText(Ability source) {
		return "You gain 2 life for each Elf you control";
	}

}
