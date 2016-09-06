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
package mage.sets.oathofthegatewatch;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class RemorselessPunishment extends CardImpl {

    public RemorselessPunishment(UUID ownerId) {
        super(ownerId, 89, "Remorseless Punishment", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");
        this.expansionSetCode = "OGW";

        // Target opponent loses 5 life unless that player discards two cards or sacrifices a creature or planeswalker. Repeat this process once.
        getSpellAbility().addEffect(new RemorselessPunishmentEffect());
        getSpellAbility().addTarget(new TargetOpponent());
    }

    public RemorselessPunishment(final RemorselessPunishment card) {
        super(card);
    }

    @Override
    public RemorselessPunishment copy() {
        return new RemorselessPunishment(this);
    }
}

class RemorselessPunishmentEffect extends OneShotEffect {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("creature or planeswalker");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.PLANESWALKER)));
    }

    public RemorselessPunishmentEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Target opponent loses 5 life unless that player discards two cards or sacrifices a creature or planeswalker. Repeat this process once";
    }

    public RemorselessPunishmentEffect(final RemorselessPunishmentEffect effect) {
        super(effect);
    }

    @Override
    public RemorselessPunishmentEffect copy() {
        return new RemorselessPunishmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (opponent != null) {
            handleBaseEffect(game, source, opponent, "1st");
            handleBaseEffect(game, source, opponent, "2nd");
            return true;
        }
        return false;
    }

    private void handleBaseEffect(Game game, Ability source, Player opponent, String iteration) {
        if (opponent.getHand().size() > 1) {
            if (opponent.chooseUse(outcome, "Choose your " + iteration + " punishment.", null, "Discard two cards", "Choose another option", source, game)) {
                opponent.discard(2, false, source, game);
                return;
            }
        }
        if (game.getBattlefield().contains(filter, opponent.getId(), 1, game)) {
            if (opponent.chooseUse(outcome, "Choose your " + iteration + " punishment.", null, "Sacrifice a creature or planeswalker", "Lose 5 life", source, game)) {
                TargetPermanent target = new TargetPermanent(1, 1, filter, true);
                if (target.choose(Outcome.Sacrifice, opponent.getId(), source.getId(), game)) {
                    for (UUID targetId : target.getTargets()) {
                        Permanent permanent = game.getPermanent(targetId);
                        if (permanent != null) {
                            permanent.sacrifice(source.getSourceId(), game);
                        }
                    }
                    return;
                }

            }
        }
        opponent.loseLife(5, game);
    }
}
