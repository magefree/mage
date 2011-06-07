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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public class WarReport extends CardImpl<WarReport> {

    public WarReport(UUID ownerId) {
        super(ownerId, 26, "War Report", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{3}{W}");
        this.expansionSetCode = "NPH";

        this.color.setWhite(true);

        this.getSpellAbility().addEffect(new WarReportEffect());
    }

    public WarReport(final WarReport card) {
        super(card);
    }

    @Override
    public WarReport copy() {
        return new WarReport(this);
    }
}

class WarReportEffect extends OneShotEffect<WarReportEffect> {

    public WarReportEffect() {
        super(Outcome.GainLife);
    }

    public WarReportEffect(final WarReportEffect effect) {
        super(effect);
    }

    @Override
    public WarReportEffect copy() {
        return new WarReportEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int lifeToGain = game.getBattlefield().countAll(new FilterCreaturePermanent());
            lifeToGain += game.getBattlefield().countAll(new FilterArtifactPermanent());
            player.gainLife(lifeToGain, game);
        }
        return true;
    }

    @Override
    public String getDynamicText(Ability source) {
        return "You gain life equal to the number of creatures on the battlefield plus the number of artifacts on the battlefield";
    }
}
