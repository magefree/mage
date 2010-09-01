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
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.combat.CombatGroup;
import mage.players.Player;
import mage.sets.Zendikar;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GoblinGuide extends CardImpl<GoblinGuide> {

	public GoblinGuide(UUID ownerId) {
		super(ownerId, "Goblin Guide", new CardType[]{CardType.CREATURE}, "{R}");
		this.expansionSetId = Zendikar.getInstance().getId();
		this.color.setRed(true);
		this.subtype.add("Goblin");
		this.subtype.add("Scout");
		this.power = new MageInt(2);
		this.toughness = new MageInt(2);
		this.addAbility(HasteAbility.getInstance());
		this.addAbility(new AttacksTriggeredAbility(new GoblinGuideEffect(), false));
	}

	public GoblinGuide(final GoblinGuide card) {
		super(card);
	}

	@Override
	public GoblinGuide copy() {
		return new GoblinGuide(this);
	}

	@Override
	public String getArt() {
		return "123540_typ_reg_sty_010.jpg";
	}

}

class GoblinGuideEffect extends OneShotEffect<GoblinGuideEffect> {

	public GoblinGuideEffect() {
		super(Outcome.DrawCard);
	}

	public GoblinGuideEffect(final GoblinGuideEffect effect) {
		super(effect);
	}

	@Override
	public GoblinGuideEffect copy() {
		return new GoblinGuideEffect(this);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player defender = null;
		for (CombatGroup group: game.getCombat().getGroups()) {
			if (group.getAttackers().contains(source.getSourceId())) {
				defender = game.getPlayer(group.getDefenderId());
				break;
			}
		}
		if (defender != null) {
			Cards cards = new CardsImpl();
			Card card = defender.getLibrary().getFromTop(game);
			if (card != null) {
				cards.add(card);
				defender.revealCards(cards, game);
				if (card.getCardType().contains(CardType.LAND)) {
					defender.getLibrary().removeFromTop(game);
					defender.putInHand(card, game);
				}
			}
		}
		return false;
	}

	@Override
	public String getText(Ability source) {
		return "defending player reveals the top card of his or her library. If it's a land card, that player puts it into his or her hand";
	}
}