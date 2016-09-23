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
package mage.sets.mirrodin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.turn.AddExtraTurnTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author emerald000
 */
public class Timesifter extends CardImpl {

    public Timesifter(UUID ownerId) {
        super(ownerId, 262, "Timesifter", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{5}");
        this.expansionSetCode = "MRD";

        // At the beginning of each upkeep, each player exiles the top card of his or her library. The player who exiled the card with the highest converted mana cost takes an extra turn after this one. If two or more players' cards are tied for highest cost, the tied players repeat this process until the tie is broken.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TimesifterEffect(), TargetController.ANY, false));
    }

    public Timesifter(final Timesifter card) {
        super(card);
    }

    @Override
    public Timesifter copy() {
        return new Timesifter(this);
    }
}

class TimesifterEffect extends OneShotEffect {

    TimesifterEffect() {
        super(Outcome.ExtraTurn);
        this.staticText = "each player exiles the top card of his or her library. The player who exiled the card with the highest converted mana cost takes an extra turn after this one. If two or more players' cards are tied for highest cost, the tied players repeat this process until the tie is broken";
    }

    TimesifterEffect(final TimesifterEffect effect) {
        super(effect);
    }

    @Override
    public TimesifterEffect copy() {
        return new TimesifterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> playersExiling = game.getState().getPlayersInRange(source.getControllerId(), game);
        do {
            int highestCMC = Integer.MIN_VALUE;
            List<UUID> playersWithHighestCMC = new ArrayList<>(1);
            for (UUID playerId : playersExiling) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Card card = player.getLibrary().getFromTop(game);
                    if (card != null) {
                        int cardCMC = card.getConvertedManaCost();
                        player.moveCardsToExile(card, source, game, true, null, "");
                        if (cardCMC > highestCMC) {
                            highestCMC = cardCMC;
                            playersWithHighestCMC.clear();
                            playersWithHighestCMC.add(playerId);
                        }
                        else if (cardCMC == highestCMC) {
                            playersWithHighestCMC.add(playerId);
                        }
                    }
                }
            }
            playersExiling = new ArrayList<>(playersWithHighestCMC);
        } while (playersExiling.size() > 1);
        for (UUID playerId : playersExiling) {
            Effect effect = new AddExtraTurnTargetEffect();
            effect.setTargetPointer(new FixedTarget(playerId));
            effect.apply(game, source);
            Player player = game.getPlayer(playerId);
            if (player != null) {
                game.informPlayers(player.getLogName() + " will take an extra turn after this one.");
            }
        }
        return true;
    }
}
