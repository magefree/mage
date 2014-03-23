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
package mage.sets.legends;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.PutTopCardOfLibraryIntoGraveTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CardsDrawnDuringDrawStepWatcher;

/**
 *
 * @author LevelX2
 */
public class ChainsOfMephistopheles extends CardImpl<ChainsOfMephistopheles> {

    public ChainsOfMephistopheles(UUID ownerId) {
        super(ownerId, 5, "Chains of Mephistopheles", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "LEG";

        this.color.setBlack(true);

        // If a player would draw a card except the first one he or she draws in his or her draw step each turn, that player discards a card instead. If the player discards a card this way, he or she draws a card. If the player doesn't discard a card this way, he or she puts the top card of his or her library into his or her graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChainsOfMephistophelesReplacementEffect()));
        this.addWatcher(new CardsDrawnDuringDrawStepWatcher());
    }

    public ChainsOfMephistopheles(final ChainsOfMephistopheles card) {
        super(card);
    }

    @Override
    public ChainsOfMephistopheles copy() {
        return new ChainsOfMephistopheles(this);
    }
}

class ChainsOfMephistophelesReplacementEffect extends ReplacementEffectImpl<ChainsOfMephistophelesReplacementEffect> {

    public ChainsOfMephistophelesReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a player would draw a card except the first one he or she draws in his or her draw step each turn, that player discards a card instead. If the player discards a card this way, he or she draws a card. If the player doesn't discard a card this way, he or she puts the top card of his or her library into his or her graveyard";
    }

    public ChainsOfMephistophelesReplacementEffect(final ChainsOfMephistophelesReplacementEffect effect) {
        super(effect);
    }

    @Override
    public ChainsOfMephistophelesReplacementEffect copy() {
        return new ChainsOfMephistophelesReplacementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            if (player.getHand().isEmpty()) {
                // he or she puts the top card of his or her library into his or her graveyard
                Effect effect = new PutTopCardOfLibraryIntoGraveTargetEffect(1);
                effect.setTargetPointer(new FixedTarget(event.getPlayerId()));
                effect.apply(game, source);
                return true;
            } else  {
                // discards a card instead. If the player discards a card this way, he or she draws a card.
                player.discard(1, source, game);
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DRAW_CARD) {
            if (game.getActivePlayerId().equals(event.getPlayerId())) {
                CardsDrawnDuringDrawStepWatcher watcher = (CardsDrawnDuringDrawStepWatcher) game.getState().getWatchers().get("CardsDrawnDuringDrawStep");
                if (watcher != null && watcher.getAmountCardsDrawn(event.getPlayerId()) > 0) {
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }
}
