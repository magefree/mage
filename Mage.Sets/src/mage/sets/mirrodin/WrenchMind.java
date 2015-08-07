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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetDiscard;

/**
 *
 * @author LevelX2
 */
public class WrenchMind extends CardImpl {

    public WrenchMind(UUID ownerId) {
        super(ownerId, 84, "Wrench Mind", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{B}{B}");
        this.expansionSetCode = "MRD";


        // Target player discards two cards unless he or she discards an artifact card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new WrenchMindEffect());

    }

    public WrenchMind(final WrenchMind card) {
        super(card);
    }

    @java.lang.Override
    public WrenchMind copy() {
        return new WrenchMind(this);
    }
}

class WrenchMindEffect extends OneShotEffect {

    public WrenchMindEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards two cards unless he or she discards an artifact card";
    }

    public WrenchMindEffect(final WrenchMindEffect effect) {
        super(effect);
    }

    @java.lang.Override
    public WrenchMindEffect copy() {
        return new WrenchMindEffect(this);
    }

    @java.lang.Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (targetPlayer != null && !targetPlayer.getHand().isEmpty()) {
            TargetDiscard target = new TargetDiscard(targetPlayer.getId());
            targetPlayer.choose(Outcome.Discard, target, source.getSourceId(), game);
            Card card = targetPlayer.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                targetPlayer.discard(card, source, game);
                if (!card.getCardType().contains(CardType.ARTIFACT) && !targetPlayer.getHand().isEmpty()) {
                    targetPlayer.discard(1, source, game);
                }
                return true;
            }            
        }
        return false;
    }
}
