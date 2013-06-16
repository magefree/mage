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
package mage.sets.morningtide;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EvokeAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class Slithermuse extends CardImpl<Slithermuse> {

    public Slithermuse(UUID ownerId) {
        super(ownerId, 50, "Slithermuse", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.expansionSetCode = "MOR";
        this.subtype.add("Elemental");

        this.color.setBlue(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Slithermuse leaves the battlefield, choose an opponent. If that player has more cards in hand than you, draw cards equal to the difference.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new SlithermuseEffect(), false));
        // Evoke {3}{U}
        this.addAbility(new EvokeAbility(this, "{3}{U}"));
    }

    public Slithermuse(final Slithermuse card) {
        super(card);
    }

    @Override
    public Slithermuse copy() {
        return new Slithermuse(this);
    }
}

/**
 * FAQ
 * You choose an opponent when the ability resolves. Once you determine how many
 * more cards than you that player has, that number is locked in as the amount
 * you'll draw.
 *
 */
class SlithermuseEffect extends OneShotEffect<SlithermuseEffect> {

    public SlithermuseEffect() {
        super(Outcome.Benefit);
        this.staticText = "choose an opponent. If that player has more cards in hand than you, draw cards equal to the difference";
    }

    public SlithermuseEffect(final SlithermuseEffect effect) {
        super(effect);
    }

    @Override
    public SlithermuseEffect copy() {
        return new SlithermuseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player != null && permanent != null) {
            TargetOpponent target = new TargetOpponent();
            target.setRequired(true);
            target.setNotTarget(true);
            if (player.choose(this.outcome, target, source.getSourceId(), game)) {
                Player chosenPlayer = game.getPlayer(target.getFirstTarget());
                if (chosenPlayer != null) {
                    game.informPlayers(permanent.getName() + ": " + player.getName() + " has chosen " + chosenPlayer.getName());
                    int diff = chosenPlayer.getHand().size() - player.getHand().size();
                    if (diff > 0) {
                        player.drawCards(diff, game);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
