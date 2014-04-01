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
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class Borrowing100000Arrows extends CardImpl<Borrowing100000Arrows> {

    public Borrowing100000Arrows(UUID ownerId) {
        super(ownerId, 33, "Borrowing 100,000 Arrows", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{2}{U}");
        this.expansionSetCode = "C13";

        this.color.setBlue(true);

        // Draw a card for each tapped creature target opponent controls.
        this.getSpellAbility().addEffect(new Borrowing100000ArrowsEffect());
        this.getSpellAbility().addTarget(new TargetOpponent(true));
    }

    public Borrowing100000Arrows(final Borrowing100000Arrows card) {
        super(card);
    }

    @Override
    public Borrowing100000Arrows copy() {
        return new Borrowing100000Arrows(this);
    }
}

class Borrowing100000ArrowsEffect extends OneShotEffect<Borrowing100000ArrowsEffect> {

    public Borrowing100000ArrowsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Draw a card for each tapped creature target opponent controls";
    }

    public Borrowing100000ArrowsEffect(final Borrowing100000ArrowsEffect effect) {
        super(effect);
    }

    @Override
    public Borrowing100000ArrowsEffect copy() {
        return new Borrowing100000ArrowsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player opponent = game.getPlayer(this.getTargetPointer().getFirst(game, source));
            if (opponent != null) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(new TappedPredicate());
            filter.add(new ControllerIdPredicate(opponent.getId()));
            return new DrawCardSourceControllerEffect(game.getBattlefield().count(filter, source.getSourceId(), source.getSourceId(), game)).apply(game, source);
        }
        return false;
    }
}
