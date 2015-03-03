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
package mage.sets.mercadianmasques;

import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class Bribery extends CardImpl {

    public Bribery(UUID ownerId) {
        super(ownerId, 62, "Bribery", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");
        this.expansionSetCode = "MMQ";

        // Search target opponent's library for a creature card and put that card onto the battlefield under your control. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new BriberyEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    public Bribery(final Bribery card) {
        super(card);
    }

    @Override
    public Bribery copy() {
        return new Bribery(this);
    }
}

class BriberyEffect extends OneShotEffect {

    public BriberyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Search target opponent's library for a creature card and put that card onto the battlefield under your control. Then that player shuffles his or her library";
    }

    public BriberyEffect(final BriberyEffect effect) {
        super(effect);
    }

    @Override
    public BriberyEffect copy() {
        return new BriberyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && opponent != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 1, new FilterCreatureCard("creature card"));
            if (controller.searchLibrary(target, game, opponent.getId())) {
                List<UUID> targets = target.getTargets();
                for (UUID targetId : targets) {
                    Card card = opponent.getLibrary().getCard(targetId, game);
                    if (card != null) {
                        controller.putOntoBattlefieldWithInfo(card, game, Zone.LIBRARY, source.getSourceId());
                    }
                }
            }
            opponent.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
