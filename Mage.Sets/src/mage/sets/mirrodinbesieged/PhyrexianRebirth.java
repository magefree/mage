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

package mage.sets.mirrodinbesieged;

import java.util.UUID;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;

/**
 *
 * @author ayratn
 */
public class PhyrexianRebirth extends CardImpl<PhyrexianRebirth> {

    public PhyrexianRebirth (UUID ownerId) {
        super(ownerId, 15, "Phyrexian Rebirth", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "MBS";
        this.color.setWhite(true);
		this.getSpellAbility().addEffect(new PhyrexianRebirthEffect());
    }

    public PhyrexianRebirth (final PhyrexianRebirth card) {
        super(card);
    }

    @Override
    public PhyrexianRebirth copy() {
        return new PhyrexianRebirth(this);
    }

	class PhyrexianRebirthEffect extends OneShotEffect<PhyrexianRebirthEffect> {

		public PhyrexianRebirthEffect() {
			super(Constants.Outcome.DestroyPermanent);
            staticText = "Destroy all creatures, then put an X/X colorless Horror artifact creature token onto the battlefield, where X is the number of creatures destroyed this way";
		}

		public PhyrexianRebirthEffect(PhyrexianRebirthEffect ability) {
			super(ability);
		}

		@Override
		public boolean apply(Game game, Ability source) {
			int count = 0;
			for (Permanent permanent: game.getBattlefield().getActivePermanents(FilterCreaturePermanent.getDefault(), source.getControllerId(), game)) {
				count += permanent.destroy(source.getId(), game, false) ? 1 : 0;
			}
			HorrorToken horrorToken = new HorrorToken();
			horrorToken.getPower().setValue(count);
			horrorToken.getToughness().setValue(count);
			horrorToken.putOntoBattlefield(1, game, source.getId(), source.getControllerId());
			return true;
		}

		@Override
		public PhyrexianRebirthEffect copy() {
			return new PhyrexianRebirthEffect(this);
		}

    }

class HorrorToken extends Token {
    public HorrorToken() {
        super("Horror", "X/X colorless Horror artifact creature token");
        cardType.add(CardType.ARTIFACT);
		cardType.add(CardType.CREATURE);
		subtype.add("Horror");
        power = new MageInt(0);
        toughness = new MageInt(0);
    }
}
}
