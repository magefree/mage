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
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class HoardingDragon extends CardImpl<HoardingDragon> {

	private static FilterCard filter = new FilterCard();

	static {
		filter.getCardType().add(CardType.ARTIFACT);
	}

	public HoardingDragon(UUID ownerId) {
		super(ownerId, "Hoarding Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
		this.expansionSetCode = "M11";
		this.subtype.add("Dragon");
		this.color.setRed(true);
		this.power = new MageInt(4);
		this.toughness = new MageInt(4);

		this.addAbility(FlyingAbility.getInstance());
		Ability ability1 = new EntersBattlefieldTriggeredAbility(new HoardingDragonEffect(this.getId()), true);
		this.addAbility(ability1);
		Ability ability2 = new PutIntoGraveFromBattlefieldTriggeredAbility(new ReturnFromExileEffect(this.getId(), Zone.BATTLEFIELD), true);
		this.addAbility(ability2);
	}

	public HoardingDragon(final HoardingDragon card) {
		super(card);
	}

	@Override
	public HoardingDragon copy() {
		return new HoardingDragon(this);
	}

	@Override
	public String getArt() {
		return "129166_typ_reg_sty_010.jpg";
	}

}

class HoardingDragonEffect extends OneShotEffect<HoardingDragonEffect> {

	protected UUID exileId;
	protected TargetCardInLibrary target;
	
	public HoardingDragonEffect(UUID exileId) {
		super(Outcome.Exile);
		this.exileId = exileId;
		target = new TargetCardInLibrary();
	}
	
	public HoardingDragonEffect(final HoardingDragonEffect effect) {
		super(effect);
		this.exileId = effect.exileId;
		this.target = effect.target.copy();
	}
	
	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getControllerId());
		player.searchLibrary(target, game);
		if (target.getTargets().size() > 0) {
			Card card = player.getLibrary().remove(target.getFirstTarget(), game);
			if (card != null){
				game.getExile().add(exileId, "Hoarding Dragon exile", card);
			}
			player.shuffleLibrary(game);
		}
		return true;
	}

	@Override
	public HoardingDragonEffect copy() {
		return new HoardingDragonEffect(this);
	}

	@Override
	public String getText(Ability source) {
		return "When Hoarding Dragon enters the battlefield, you may search your library for an artifact card, exile it, then shuffle your library";
	}
}
