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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author TheElk801
 */
public class TwoHeadedGiant extends CardImpl {

    public TwoHeadedGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Two-Headed Giant attacks, flip two coins.  If both coins come up heads, Two-Headed Giant gains double strike until end of turn.  If both coins come up tails, Two-Headed Giant gains menace until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new TwoHeadedGiantEffect(), false));
    }

    public TwoHeadedGiant(final TwoHeadedGiant card) {
        super(card);
    }

    @Override
    public TwoHeadedGiant copy() {
        return new TwoHeadedGiant(this);
    }
}

class TwoHeadedGiantEffect extends OneShotEffect {

    public TwoHeadedGiantEffect() {
        super(Outcome.Benefit);
        this.staticText = "flip two coins.  If both coins come up heads, {this} gains double strike until end of turn."
                + " If both coins come up tails, {this} gains menace until end of turn";
    }

    public TwoHeadedGiantEffect(final TwoHeadedGiantEffect effect) {
        super(effect);
    }

    @Override
    public TwoHeadedGiantEffect copy() {
        return new TwoHeadedGiantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean head1 = player.flipCoin(game);
        boolean head2 = player.flipCoin(game);
        if (head1 == head2) {
            if (head1) {
                game.addEffect(new GainAbilitySourceEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn), source);
            } else {
                game.addEffect(new GainAbilitySourceEffect(new MenaceAbility(), Duration.EndOfTurn), source);
            }
        }
        return true;
    }
}
