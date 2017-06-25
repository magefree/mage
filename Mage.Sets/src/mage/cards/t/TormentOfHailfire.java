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
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public class TormentOfHailfire extends CardImpl {

    public TormentOfHailfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Repeat the following process X times. Each opponent loses 3 life unless he or she sacrifices a nonland permanent or discards a card.
        this.getSpellAbility().addEffect(new TormentOfHailfireEffect());

    }

    public TormentOfHailfire(final TormentOfHailfire card) {
        super(card);
    }

    @Override
    public TormentOfHailfire copy() {
        return new TormentOfHailfire(this);
    }
}

class TormentOfHailfireEffect extends OneShotEffect {

    public TormentOfHailfireEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Repeat the following process X times. Each opponent loses 3 life unless he or she sacrifices a nonland permanent or discards a card";
    }

    public TormentOfHailfireEffect(final TormentOfHailfireEffect effect) {
        super(effect);
    }

    @Override
    public TormentOfHailfireEffect copy() {
        return new TormentOfHailfireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int repeat = source.getManaCosts().getX();
            for (int i = 0; i < repeat; i++) {
                for (UUID opponentId : game.getOpponents(source.getControllerId())) {
                    Player opponent = game.getPlayer(opponentId);
                    if (opponent != null) {
                        int permanents = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_NON_LAND, opponentId, game);
                        if (permanents > 0 && opponent.chooseUse(outcome, "Sacrifices a nonland permanent? (Iteration " + i + " of " + repeat + ")",
                                "Otherwise you have to discard a card or lose 3 life.", "Sacrifice", "Discard or life loss", source, game)) {
                            Target target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_NON_LAND);
                            if (opponent.choose(outcome, target, source.getSourceId(), game)) {
                                Permanent permanent = game.getPermanent(target.getFirstTarget());
                                if (permanent != null) {
                                    permanent.sacrifice(source.getSourceId(), game);
                                    continue;
                                }
                            }
                        }
                        if (!opponent.getHand().isEmpty() && opponent.chooseUse(outcome, "Discard a card? (Iteration " + i + " of " + repeat + ")",
                                "Otherwise you lose 3 life.", "Discard", "Lose 3 life", source, game)) {
                            opponent.discardOne(false, source, game);
                            continue;
                        }
                        opponent.loseLife(3, game, false);
                    }
                }

            }
            return true;
        }
        return false;
    }
}
