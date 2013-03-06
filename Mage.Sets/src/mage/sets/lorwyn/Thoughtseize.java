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
package mage.sets.lorwyn;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.LoseLifeSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author jonubuu
 */
public class Thoughtseize extends CardImpl<Thoughtseize> {

    public Thoughtseize(UUID ownerId) {
        super(ownerId, 145, "Thoughtseize", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "LRW";

        this.color.setBlack(true);

        // Target player reveals his or her hand. You choose a nonland card from it. That player discards that card. You lose 2 life.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new ThoughtseizeEffect());
        this.getSpellAbility().addEffect(new LoseLifeSourceEffect(2));
    }

    public Thoughtseize(final Thoughtseize card) {
        super(card);
    }

    @Override
    public Thoughtseize copy() {
        return new Thoughtseize(this);
    }
}

class ThoughtseizeEffect extends OneShotEffect<ThoughtseizeEffect> {

    private static final FilterCard filter = new FilterCard("nonland card");

    static {
        filter.add(Predicates.not(new CardTypePredicate(CardType.LAND)));
    }

    public ThoughtseizeEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals his or her hand. You choose a nonland card from it. That player discards that card";
    }

    public ThoughtseizeEffect(final ThoughtseizeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.revealCards("Thoughtseize", player.getHand(), game);
            Player you = game.getPlayer(source.getControllerId());
            if (you != null) {
                TargetCard target = new TargetCard(Zone.HAND, filter);
                if (you.chooseTarget(outcome, player.getHand(), target, source, game)) {
                    Card card = player.getHand().get(target.getFirstTarget(), game);
                    if (card != null) {
                        return player.discard(card, source, game);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public ThoughtseizeEffect copy() {
        return new ThoughtseizeEffect(this);
    }
}
