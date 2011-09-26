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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public class EntomberExarch extends CardImpl<EntomberExarch> {

    public EntomberExarch(UUID ownerId) {
        super(ownerId, 59, "Entomber Exarch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.expansionSetCode = "NPH";
        this.subtype.add("Cleric");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Entomber Exarch enters the battlefield, choose one - Return target creature card from your graveyard to your hand; or target opponent reveals his or her hand, you choose a noncreature card from it, then that player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCardInYourGraveyard(new FilterCreatureCard("creature card from your graveyard")));
        Mode mode = new Mode();
        mode.getEffects().add(new EntomberExarchEffect());
        mode.getTargets().add(new TargetOpponent());
        ability.addMode(mode);
        this.addAbility(ability);
    }

    public EntomberExarch(final EntomberExarch card) {
        super(card);
    }

    @Override
    public EntomberExarch copy() {
        return new EntomberExarch(this);
    }
}

class EntomberExarchEffect extends OneShotEffect<EntomberExarchEffect> {
    private static final FilterCard filter = new FilterCard("noncreature card");

    static {
        filter.getNotCardType().add(CardType.CREATURE);
    }

    EntomberExarchEffect() {
        super(Constants.Outcome.Discard);
        staticText = "target opponent reveals his or her hand, you choose a noncreature card from it, then that player discards that card";
    }

    EntomberExarchEffect(final EntomberExarchEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
		if (player != null) {
			player.revealCards("Entomber Exarch", player.getHand(), game);
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
    public EntomberExarchEffect copy() {
        return new EntomberExarchEffect(this);
    }
}
