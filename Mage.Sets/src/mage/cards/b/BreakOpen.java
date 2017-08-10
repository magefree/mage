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
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.FaceDownPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public class BreakOpen extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Face-down creature an opponent controls");

    static {
        filter.add(new FaceDownPredicate());
        filter.add(new ControllerPredicate(TargetController.OPPONENT));
    }

    public BreakOpen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Turn target face-down creature an opponent controls face up.
        this.getSpellAbility().addEffect(new TurnFaceUpTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    public BreakOpen(final BreakOpen card) {
        super(card);
    }

    @Override
    public BreakOpen copy() {
        return new BreakOpen(this);
    }
}

class TurnFaceUpTargetEffect extends OneShotEffect {

    public TurnFaceUpTargetEffect() {
        super(Outcome.Benefit);
        this.staticText = "Turn target face-down creature an opponen controls face up.";
    }

    public TurnFaceUpTargetEffect(final TurnFaceUpTargetEffect effect) {
        super(effect);
    }

    @Override
    public TurnFaceUpTargetEffect copy() {
        return new TurnFaceUpTargetEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID target = targetPointer.getFirst(game, source);
        if (target != null) {
            Permanent permanent = game.getPermanent(target);
            if (permanent != null) {
                return permanent.turnFaceUp(game, source.getControllerId());
            }
        }
        return false;
    }
}
