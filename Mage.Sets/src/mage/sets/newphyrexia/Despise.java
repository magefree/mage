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
package mage.sets.newphyrexia;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class Despise extends CardImpl<Despise> {

    public Despise(UUID ownerId) {
        super(ownerId, 56, "Despise", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "NPH";

        this.color.setBlack(true);

        // Target opponent reveals his or her hand. You choose a creature or planeswalker card from it. That player discards that card.
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addEffect(new DespiseEffect());
    }

    public Despise(final Despise card) {
        super(card);
    }

    @Override
    public Despise copy() {
        return new Despise(this);
    }
}

class DespiseEffect extends OneShotEffect<DespiseEffect> {

	private static final FilterCard filter = new FilterCard("creature or planeswalker card");

	static {
		filter.getCardType().add(CardType.CREATURE);
		filter.getCardType().add(CardType.PLANESWALKER);
        filter.setScopeCardType(Filter.ComparisonScope.Any);
	}

	public DespiseEffect() {
		super(Constants.Outcome.Discard);
		staticText = "Target opponent reveals his or her hand. You choose a creature or planeswalker card from it. That player discards that card";
	}

	public DespiseEffect(final DespiseEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
		Player player = game.getPlayer(source.getFirstTarget());
		if (player != null) {
			player.revealCards("Despise", player.getHand(), game);
			Player you = game.getPlayer(source.getControllerId());
			if (you != null) {
				TargetCard target = new TargetCard(Constants.Zone.PICK, filter);
                target.setRequired(true);
				if (you.choose(Constants.Outcome.Benefit, player.getHand(), target, game)) {
					Card card = player.getHand().get(target.getFirstTarget(), game);
					if (card != null) {
						return player.discard(card, source, game);
					}
				}
			}
		}
		return false;
	}

	@Override
	public DespiseEffect copy() {
		return new DespiseEffect(this);
	}

}
