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
package mage.sets.betrayersofkamigawa;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public class FinalJudgment extends CardImpl<FinalJudgment> {

    public FinalJudgment(UUID ownerId) {
        super(ownerId, 4, "Final Judgment", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");
        this.expansionSetCode = "BOK";
        this.color.setWhite(true);
        // Exile all creatures.
        this.getSpellAbility().addEffect(new FinalJudgmentEffect());

    }

    public FinalJudgment(final FinalJudgment card) {
        super(card);
    }

    @Override
    public FinalJudgment copy() {
        return new FinalJudgment(this);
    }
}

class FinalJudgmentEffect extends OneShotEffect<FinalJudgmentEffect> {

    private final static FilterPermanent filter = new FilterPermanent("");

    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public FinalJudgmentEffect() {
        super(Constants.Outcome.Exile);
        staticText = "Exile all creatures";
    }

    public FinalJudgmentEffect(final FinalJudgmentEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(filter, game)) {
            permanent.moveToExile(null, null,source.getId(), game);
        }
        return true;
    }

    @Override
    public FinalJudgmentEffect copy() {
        return new FinalJudgmentEffect(this);
    }

}