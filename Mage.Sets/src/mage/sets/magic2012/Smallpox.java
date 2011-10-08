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
package mage.sets.magic2012;

import java.security.PrivilegedActionException;
import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.Filter;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author nantuko
 */
public class Smallpox extends CardImpl<Smallpox> {

    public Smallpox(UUID ownerId) {
        super(ownerId, 108, "Smallpox", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{B}{B}");
        this.expansionSetCode = "M12";

        this.color.setBlack(true);

        // Each player loses 1 life, discards a card, sacrifices a creature, then sacrifices a land.
		this.getSpellAbility().addEffect(new SmallpoxEffect());
    }

    public Smallpox(final Smallpox card) {
        super(card);
    }

    @Override
    public Smallpox copy() {
        return new Smallpox(this);
    }
}

class SmallpoxEffect extends OneShotEffect<SmallpoxEffect> {

	private static FilterPermanent filterCreature = new FilterPermanent("a creature you control");
	private static FilterPermanent filterLand = new FilterPermanent("a land you control");

	static {
		filterCreature.getCardType().add(CardType.CREATURE);
		filterCreature.setScopeCardType(Filter.ComparisonScope.Any);
		filterCreature.setTargetController(Constants.TargetController.YOU);
		filterLand.getCardType().add(CardType.LAND);
		filterLand.setScopeCardType(Filter.ComparisonScope.Any);
		filterLand.setTargetController(Constants.TargetController.YOU);
	}

    SmallpoxEffect() {
        super(Constants.Outcome.DestroyPermanent);
        staticText = "Each player loses 1 life, discards a card, sacrifices a creature, then sacrifices a land";
    }

    SmallpoxEffect(final SmallpoxEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
				player.loseLife(1, game);
            }
        }

		for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.discard(1, source, game);
            }
        }


		for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
				sacrifice(game, source, player, filterCreature);
            }
        }

		for (UUID playerId : game.getPlayerList()) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
				sacrifice(game, source, player, filterLand);
            }
        }

        return true;
    }

    @Override
    public SmallpoxEffect copy() {
        return new SmallpoxEffect(this);
    }

	private void sacrifice(Game game, Ability source, Player player, FilterPermanent filter) {
		Target target = new TargetControlledPermanent(1, 1, filter, false);
		if (target.canChoose(player.getId(), game)) {
			while (!target.isChosen() && target.canChoose(player.getId(), game)) {
				player.choose(Constants.Outcome.Sacrifice, target, source.getSourceId(), game);
			}

			for ( int idx = 0; idx < target.getTargets().size(); idx++) {
				Permanent permanent = game.getPermanent((UUID)target.getTargets().get(idx));

				if ( permanent != null ) {
					permanent.sacrifice(source.getSourceId(), game);
				}
			}
		}
	}

}
