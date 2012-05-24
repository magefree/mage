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
package mage.sets.darkascension;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public class AlphaBrawl extends CardImpl<AlphaBrawl> {

	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature an opponent controls");

	static {
		filter.setTargetController(Constants.TargetController.OPPONENT);
	}

    public AlphaBrawl(UUID ownerId) {
        super(ownerId, 82, "Alpha Brawl", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{6}{R}{R}");
        this.expansionSetCode = "DKA";

        this.color.setRed(true);

        // Target creature an opponent controls deals damage equal to its power to each other creature that player controls, then each of those creatures deals damage equal to its power to that creature.
        this.getSpellAbility().addEffect(new AlphaBrawlEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
        
    }

    public AlphaBrawl(final AlphaBrawl card) {
        super(card);
    }

    @Override
    public AlphaBrawl copy() {
        return new AlphaBrawl(this);
    }
}

class AlphaBrawlEffect extends OneShotEffect<AlphaBrawlEffect> {

	private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    public AlphaBrawlEffect() {
		super(Constants.Outcome.Damage);
		staticText = "Target creature an opponent controls deals damage equal to its power to each other creature that player controls, then each of those creatures deals damage equal to its power to that creature";
	}

	public AlphaBrawlEffect(final AlphaBrawlEffect effect) {
		super(effect);
	}

	@Override
	public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            Player player = game.getPlayer(creature.getControllerId());
            if (player != null) {
                int power = creature.getPower().getValue();
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                    perm.damage(power, creature.getId(), game, true, false);
                }
                for (Permanent perm: game.getBattlefield().getAllActivePermanents(filter, player.getId(), game)) {
                    creature.damage(perm.getPower().getValue(), perm.getId(), game, true, false);
                }
                return true;
            }
        }
		return false;
	}

	@Override
	public AlphaBrawlEffect copy() {
		return new AlphaBrawlEffect(this);
	}

}