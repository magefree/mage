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
package mage.sets.darksteel;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class ChitteringRats extends CardImpl {

    public ChitteringRats(UUID ownerId) {
        super(ownerId, 39, "Chittering Rats", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "DST";
        this.subtype.add("Rat");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Chittering Rats enters the battlefield, target opponent puts a card from his or her hand on top of his or her library.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ChitteringRatsEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

    }

    public ChitteringRats(final ChitteringRats card) {
        super(card);
    }

    @Override
    public ChitteringRats copy() {
        return new ChitteringRats(this);
    }
}

class ChitteringRatsEffect extends OneShotEffect {

    public ChitteringRatsEffect() {
        super(Outcome.Detriment);
        this.staticText = "target opponent puts a card from his or her hand on top of his or her library.";
    }

    public ChitteringRatsEffect(final ChitteringRatsEffect effect) {
        super(effect);
    }

    @Override
    public ChitteringRatsEffect copy() {
        return new ChitteringRatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetOpponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetOpponent != null) {
            if (targetOpponent.getHand().size() > 0) {
                TargetCardInHand target = new TargetCardInHand();
                target.setNotTarget(true);
                target.setTargetName("a card from your hand to put on top of your library");
                targetOpponent.choose(Outcome.Detriment, target, source.getSourceId(), game);
                Card card = targetOpponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    targetOpponent.moveCardToLibraryWithInfo(card, source.getSourceId(), game, Zone.HAND, true, false);
                }
            }
            return true;
        }
        return false;
    }
}
